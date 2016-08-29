package com.wei.apktools.interfaces;

import com.wei.apktools.config.EnvProperty;
import com.wei.apktools.config.Theme;
import com.wei.apktools.task.QueueTask;
import com.wei.apktools.task.TaskManager;

/**
 * Created by starrysky on 15-3-5.
 */
public interface MainModelInterfaces {

    /**
     * 菜单的模型对象
     * @return
     */
    MenuModelInterfaces getMenuModelInterfaces();

    /**
     * 输出信息的模型对象
     * @return
     */
    OutInfoModelInterfaces getOutInfoModelInterfaces();

    /**
     * 签名证书的模型对象
     * @return
     */
    CredentialModelInterfaces getCredentialModelInterfaces();

    /**
     * 释放
     */
    void release();
}
