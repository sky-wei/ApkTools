package com.wei.apktools.task;

import com.wei.apktools.interfaces.OutputInfo;
import com.wei.apktools.utils.Log;

import java.io.File;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

/**
 * Created by starrysky on 15-2-10.
 */
public class TaskManager extends Thread implements QueueTask {

    private boolean run;
    private Execute execute;

    private Queue<ExecuteTask> taskQueue = new LinkedList<>();;

    private static TaskManager taskManager;

    public static TaskManager getInstance() {

        if (taskManager == null) {
            taskManager = new TaskManager();
            taskManager.start();
        }
        return taskManager;
    }

    private TaskManager() {
        execute = new Execute();
    }

    public Execute getExecute() {
        return execute;
    }

    public void setOutputInfo(OutputInfo outputInfo) {
        execute.setOutputInfo(outputInfo);
    }

    public void setEnvp(String[] envp) {
        execute.setEnvp(envp);
    }

    public void setWorkDir(File workDir) {
        execute.setWorkDir(workDir);
    }

    public synchronized void release() {
        setRun(false);
        notify();
    }

    @Override
    public synchronized void addTask(ExecuteTask executeTask) {

        if (executeTask == null) return ;

        taskQueue.add(executeTask);
        notify();
    }

    @Override
    public synchronized void removeTask(ExecuteTask executeTask) {

    }

    @Override
    public synchronized List<ExecuteTask> getTasks() {
        return null;
    }

    @Override
    public synchronized void clearTask() {
        taskQueue.clear();
    }

    private void output(String info) {
        if (execute.getOutputInfo() != null) execute.getOutputInfo().onOutput(info);
    }

    private void output(String info, Throwable tr) {
        if (execute.getOutputInfo() != null) execute.getOutputInfo().onOutput(info, tr);
    }

    @Override
    public synchronized void run() {
        super.run();

        Log.d(">>>>>>>>>>>  开始执行");

        while (run) {

            while (!taskQueue.isEmpty()) {

                Log.d(">>> 有任务需要执行");

                try {
                    // 处理任务
                    ExecuteTask executeTask = taskQueue.poll();
                    executeTask.onHandler(execute);
                } catch (Exception e) {
                    Log.e("执行任务出错", e);
                    output("执行任务出错", e);
                }

                try {
                    Log.d(">>>  开始休休眠");
                    Thread.sleep(5000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }

            try {
                Log.d(">>>  开始等待");
                wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        Log.d(">>>>>>>>>>>  结束执行");
    }

    @Override
    public synchronized void start() {
        run = true;
        super.start();
    }

    public boolean isRun() {
        return run;
    }

    public void setRun(boolean run) {
        this.run = run;
    }
}
