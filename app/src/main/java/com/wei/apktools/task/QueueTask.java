package com.wei.apktools.task;

import java.util.List;

/**
 * Created by starrysky on 15-2-10.
 */
public interface QueueTask {

    /**
     * 添加任务
     * @param executeTask
     */
    void addTask(ExecuteTask executeTask);

    /**
     * 移除任务
     * @param executeTask
     */
    void removeTask(ExecuteTask executeTask);

    /**
     * 获取所有的任务
     * @return
     */
    List<ExecuteTask> getTasks();

    /**
     * 清除所有任务
     */
    void clearTask();
}
