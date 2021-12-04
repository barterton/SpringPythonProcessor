package com.pyexecutor.pythonprocessor.service;

import com.pyexecutor.pythonprocessor.task.Task;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedReader;
import java.io.File;
import java.io.InputStreamReader;

@Component
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class PythonProcess {

    private static final Logger logger = LoggerFactory.getLogger(PythonProcess.class);

    @Value(value = "${spring.pythonPath}")
    private String pythonPath;
    @Value(value = "${spring.pyLibraryPath}")
    private String pyLibraryPath;
    @Value(value = "${spring.pyLibName}")
    private String pyLibName;

public int executePython(Task taskToRun) {

    String sysPath = System.getenv("PATH");
    String[] pyPathArgs = {"PYTHONPATH="+pyLibraryPath, "PATH="+sysPath};

    String command = pythonPath + " " + pyLibraryPath + File.separator + pyLibName + " " + taskToRun.getArgs();
    int exitCode = 1;
    try {
        Process p = Runtime.getRuntime().exec(command, pyPathArgs);

        BufferedReader br = new BufferedReader(new InputStreamReader(p.getInputStream()));
        BufferedReader err = new BufferedReader(new InputStreamReader(p.getErrorStream()));

        String processOutput;

        while ((processOutput = br.readLine()) != null){
            logger.info("[Python process logs]: {}", processOutput);
        }

        while ((processOutput = err.readLine()) != null){
            logger.error("[Python error logs]: {}", processOutput);
        }
        exitCode = p.waitFor();
        logger.info("Python task exited with code: {}", exitCode);

    } catch (Exception e) {
        logger.info(e.getMessage(), e);

    }


    return exitCode;
    }
}
