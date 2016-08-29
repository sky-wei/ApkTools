package com.wei.apktools.core;

import com.wei.apktools.config.EnvProperty;
import com.wei.apktools.config.Theme;
import com.wei.apktools.task.QueueTask;
import com.wei.apktools.task.TaskManager;

/**
 * Created by starrysky on 15-1-19.
 *
 * 程序功能控件器接口
 */
public interface Controller {

    /**
     * 获取当前主题对象
     * @return
     */
    Theme getTheme();

    /**
     * 获取环境属性对象
     * @return
     */
    EnvProperty getEnvProperty();

    /**
     * 队列任务
     * @return
     */
    QueueTask getQueueTask();

    /**
     * 退出程序
     */
    void quit();
}
