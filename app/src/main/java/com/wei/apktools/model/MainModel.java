package com.wei.apktools.model;

import com.wei.apktools.config.EnvProperty;
import com.wei.apktools.config.Theme;
import com.wei.apktools.interfaces.CredentialModelInterfaces;
import com.wei.apktools.interfaces.MainModelInterfaces;
import com.wei.apktools.interfaces.MenuModelInterfaces;
import com.wei.apktools.interfaces.OutInfoModelInterfaces;
import com.wei.apktools.task.QueueTask;
import com.wei.apktools.task.TaskManager;

/**
 * Created by starrysky on 15-3-5.
 */
public class MainModel implements MainModelInterfaces {

    private MenuModelInterfaces menuModelInterfaces;
    private OutInfoModelInterfaces outInfoModelInterfaces;
    private CredentialModelInterfaces credentialModelInterfaces;

    public MainModel() {
        outInfoModelInterfaces = new OutInfoModel();
        credentialModelInterfaces = new CredentialModel();
        menuModelInterfaces = new MenuModel(credentialModelInterfaces);
    }

    @Override
    public MenuModelInterfaces getMenuModelInterfaces() {
        return menuModelInterfaces;
    }

    @Override
    public OutInfoModelInterfaces getOutInfoModelInterfaces() {
        return outInfoModelInterfaces;
    }

    @Override
    public CredentialModelInterfaces getCredentialModelInterfaces() {
        return credentialModelInterfaces;
    }

    @Override
    public void release() {
        // 释放所有任务
        TaskManager.getInstance().release();
    }
}
