package com.wei.apktools.model;

import com.wei.apktools.bean.CompileBean;
import com.wei.apktools.bean.CredentialsBean;
import com.wei.apktools.config.EnvProperty;
import com.wei.apktools.config.Theme;
import com.wei.apktools.config.ThemeProperty;
import com.wei.apktools.interfaces.CredentialModelInterfaces;
import com.wei.apktools.interfaces.MenuModelInterfaces;
import com.wei.apktools.utils.Constant;

import javax.swing.*;

/**
 * Created by starrysky on 15-3-16.
 */
public class MenuModel implements MenuModelInterfaces {

    private CredentialModelInterfaces credentialModelInterfaces;

    private EnvProperty envProperty;

    private Theme theme;
    private ThemeProperty themeProperty;

    public MenuModel(CredentialModelInterfaces credentialModelInterfaces) {
        this.credentialModelInterfaces = credentialModelInterfaces;
        this.envProperty = EnvProperty.getInstance();
        this.theme = new Theme();

        themeProperty = theme.loadThemeProperty(Constant.THEME);
        if (themeProperty == null) themeProperty = new ThemeProperty();
    }

    @Override
    public ThemeProperty getThemeProperty() {
        return themeProperty;
    }

    @Override
    public void setThemeProperty(ThemeProperty themeProperty) {
        theme.saveThemeProperty(themeProperty, Constant.THEME);
    }

    @Override
    public void refreshTheme(JFrame frame) {
        theme.setTheme(frame, Constant.THEME, true);
    }

    @Override
    public CompileBean geCompileConfig() {

        CompileBean compileBean = new CompileBean();

        compileBean.setJdkPath(envProperty.get(EnvProperty.JDK_HOME));
        compileBean.setOutPath(envProperty.get(EnvProperty.COMPLETE_DIR));
        compileBean.setTempPath(envProperty.get(EnvProperty.TEMP_DIR));
        compileBean.setClearTempDir(envProperty.getBoolean(EnvProperty.CLEAR_TEMP));

        return compileBean;
    }

    @Override
    public void setCompileConfig(CompileBean compileBean) {

        // 保存环境属性信息
        envProperty.put(EnvProperty.JDK_HOME, compileBean.getJdkPath());
        envProperty.put(EnvProperty.COMPLETE_DIR, compileBean.getOutPath());
        envProperty.put(EnvProperty.TEMP_DIR, compileBean.getTempPath());
        envProperty.putBoolean(EnvProperty.CLEAR_TEMP, compileBean.isClearTempDir());
        envProperty.saveEnvProperty();
    }

    @Override
    public CredentialsBean getCredentialsConfig() {
        return credentialModelInterfaces.getCredentialsConfig();
    }

    @Override
    public void setCredentialsConfig(CredentialsBean credentialsBean) {
        credentialModelInterfaces.setCredentialsConfig(credentialsBean);
    }
}
