package com.wei.apktools.controller;

import com.wei.apktools.config.EnvProperty;
import com.wei.apktools.controller.MenuController;
import com.wei.apktools.controller.OutInfoController;
import com.wei.apktools.gui.ComSettingsDialog;
import com.wei.apktools.interfaces.*;
import com.wei.apktools.main.MainFrame;
import com.wei.apktools.utils.Constant;

import java.io.File;

/**
 * Created by starrysky on 15-3-4.
 */
public class MainController implements MainControllerInterfaces {

    private MainFrame mainFrame;
    private MainModelInterfaces mainModelInterfaces;

    private MenuControllerInterfaces menuControllerInterfaces;
    private OutInfoControllerInterfaces outInfoControllerInterfaces;
    private CredentialControllerInterfaces credentialControllerInterfaces;

    public MainController(MainModelInterfaces mainModelInterfaces) {

        this.mainModelInterfaces = mainModelInterfaces;

        init();
        mainFrame = new MainFrame(this, mainModelInterfaces);

        menuControllerInterfaces = new MenuController(mainFrame, this, mainModelInterfaces);
        outInfoControllerInterfaces = new OutInfoController(mainFrame, mainModelInterfaces.getOutInfoModelInterfaces());
        credentialControllerInterfaces = new CredentialController(mainFrame, mainModelInterfaces.getCredentialModelInterfaces());

        mainFrame.createView();
        detectionEnv();
    }

    @Override
    public MenuControllerInterfaces getMenuControllerInterfaces() {
        return menuControllerInterfaces;
    }

    @Override
    public OutInfoControllerInterfaces getOutInfoControllerInterfaces() {
        return outInfoControllerInterfaces;
    }

    @Override
    public CredentialControllerInterfaces getCredentialControllerInterfaces() {
        return credentialControllerInterfaces;
    }

    @Override
    public void quit() {

        // 退出程序
        mainFrame.dispose();
        mainModelInterfaces.release();
    }

    private void init() {

        // 初始化资源目录
        File dir = new File(Constant.CREDENTIAL_DIR);
        if (!dir.isDirectory()) dir.mkdirs();
    }

    private void detectionEnv() {

        // 获取环境属性文件
        File envProperFile = EnvProperty.getInstance().getEnvPropertyFile();

        if (!envProperFile.exists()) {

            // 进入设置环境属性配置界面
            ComSettingsDialog comSettingsDialog = new ComSettingsDialog(
                    mainFrame, menuControllerInterfaces, mainModelInterfaces.getMenuModelInterfaces());
            comSettingsDialog.setVisible(true);
        }
    }
}
