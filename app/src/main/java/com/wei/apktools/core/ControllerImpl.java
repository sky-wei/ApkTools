package com.wei.apktools.core;

import com.wei.apktools.main.AppMain;
import com.wei.apktools.config.EnvProperty;
import com.wei.apktools.config.Theme;
import com.wei.apktools.task.QueueTask;
import com.wei.apktools.task.TaskManager;

/**
 * Created by starrysky on 15-1-19.
 *
 * 控制器的实现类
 */
public class ControllerImpl implements Controller {

    private AppMain mainFrame;
    private Theme theme;
    private TaskManager taskManager;

    public ControllerImpl(AppMain mainFrame) {

        this.mainFrame = mainFrame;
        theme = new Theme();
        taskManager= TaskManager.getInstance();
    }

    @Override
    public Theme getTheme() {
        return theme;
    }

    @Override
    public EnvProperty getEnvProperty() {
        return EnvProperty.getInstance();
    }

    @Override
    public QueueTask getQueueTask() {
        return taskManager;
    }

    @Override
    public void quit() {

        // 退出程序
        taskManager.release();
    }
}
