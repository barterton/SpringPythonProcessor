package com.pyexecutor.pythonprocessor;

import com.hazelcast.client.HazelcastClient;
import com.hazelcast.core.HazelcastInstance;
import com.pyexecutor.pythonprocessor.task.Task;
import org.springframework.boot.SpringApplication;

import java.util.concurrent.BlockingQueue;

public class PopulateTaskQueue {

    public static void main(String[] args) throws InterruptedException {
        HazelcastInstance hz = HazelcastClient.newHazelcastClient();
        BlockingQueue<Task> queue = hz.getQueue("tasks");

        queue.put(new Task("DefaultTask", "--task DefaultTask"));
        queue.put(new Task("Task1", "--task Task1"));
        queue.put(new Task("Task1", "--task Task2"));
        queue.put(new Task("Task1", "--task Task3"));

    }
}
