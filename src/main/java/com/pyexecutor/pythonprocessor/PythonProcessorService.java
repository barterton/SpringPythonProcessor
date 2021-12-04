package com.pyexecutor.pythonprocessor;

import com.hazelcast.collection.IQueue;
import com.pyexecutor.pythonprocessor.service.PythonProcess;
import com.pyexecutor.pythonprocessor.task.Task;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.beans.factory.annotation.Value;
import com.hazelcast.client.HazelcastClient;
import com.hazelcast.client.config.ClientConfig;
import com.hazelcast.client.util.ClientStateListener;
import com.hazelcast.core.Hazelcast;
import com.hazelcast.core.HazelcastInstance;
import com.hazelcast.map.IMap;

import java.util.concurrent.BlockingQueue;

@SpringBootApplication(
		scanBasePackages = "com.pyexecutor.pythonprocessor"
)
@ComponentScan(basePackages = "com.pyexecutor.pythonprocessor")
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class PythonProcessorService implements ApplicationRunner {

	private final PythonProcess process;

	@Value(value = "${spring.pythonPath}")
	String pypath;

	public static void main(String[] args) {
		SpringApplication.run(PythonProcessorService.class, args);

	}
	@Override
	public void run(ApplicationArguments args) throws InterruptedException {
		HazelcastInstance hz = HazelcastClient.newHazelcastClient();

		BlockingQueue<Task> queue = hz.getQueue("tasks");

		while(true){
			process.executePython(queue.take());

		}
	}
}
