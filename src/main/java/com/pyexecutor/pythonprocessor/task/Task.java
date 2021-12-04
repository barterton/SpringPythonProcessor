package com.pyexecutor.pythonprocessor.task;

import lombok.*;

import java.io.Serializable;

@Getter
@Setter
public class Task implements Serializable {

    private String taskId;
    private String args;

    public Task(String taskId, String args){
        this.taskId = taskId;
        this.args = args;
    }


}
