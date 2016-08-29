package com.wei.apktools.controller;

import com.wei.apktools.bean.CompileBean;
import com.wei.apktools.bean.CredentialsBean;
import com.wei.apktools.config.ThemeProperty;
import com.wei.apktools.gui.AboutDialog;
import com.wei.apktools.gui.ComSettingsDialog;
import com.wei.apktools.gui.CredentialsSettingDialog;
import com.wei.apktools.gui.ThemeSettingDialog;
import com.wei.apktools.interfaces.MainControllerInterfaces;
import com.wei.apktools.interfaces.MainModelInterfaces;
import com.wei.apktools.interfaces.MenuControllerInterfaces;
import com.wei.apktools.interfaces.MenuModelInterfaces;
import com.wei.apktools.model.MenuModel;
import com.wei.apktools.utils.Constant;

import javax.swing.*;

/**
 * Created by starrysky on 15-3-16.
 */
public class MenuController extends BaseController implements MenuControllerInterfaces {

    private MainControllerInterfaces mainControllerInterfaces;
    private MenuModelInterfaces menuModelInterfaces;

    public MenuController(JFrame frame, MainControllerInterfaces mainControllerInterfaces, MainModelInterfaces mainModelInterfaces) {
        super(frame);
        this.mainControllerInterfaces = mainControllerInterfaces;
        this.menuModelInterfaces = mainModelInterfaces.getMenuModelInterfaces();
    }

    @Override
    public void quit() {
        mainControllerInterfaces.quit();
    }

    @Override
    public void setThemeProperty(ThemeProperty themeProperty) {
        menuModelInterfaces.setThemeProperty(themeProperty);
        menuModelInterfaces.refreshTheme(getFrame());
    }

    @Override
    public void setCompileConfig(CompileBean compileBean) {
        menuModelInterfaces.setCompileConfig(compileBean);
    }

    @Override
    public void setCredentialsConfig(CredentialsBean credentialsBean) {
        menuModelInterfaces.setCredentialsConfig(credentialsBean);
    }
}
