package com.wei.apktools.task;

import org.junit.Test;

public class TaskManagerTest {

    @Test
    public void testGetInstance() throws Exception {

        TaskManager taskManager = TaskManager.getInstance();
        Thread.sleep(2000);
        taskManager.addTask(executeTask);
        Thread.sleep(2000);
        taskManager.addTask(executeTask);
        Thread.sleep(2000);
        taskManager.release();
        Thread.sleep(2000);
    }

    private ExecuteTask executeTask = new ExecuteTask() {
        @Override
        public void onHandler(Execute execute) {
            System.out.println(">>>>> 我在运行了...");
        }
    };
}