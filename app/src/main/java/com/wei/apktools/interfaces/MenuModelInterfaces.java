package com.wei.apktools.interfaces;

import com.wei.apktools.bean.CompileBean;
import com.wei.apktools.bean.CredentialsBean;
import com.wei.apktools.config.ThemeProperty;

import javax.swing.*;

/**
 * Created by starrysky on 15-3-16.
 */
public interface MenuModelInterfaces {

    /**
     * 获取主题属性
     * @return
     */
    ThemeProperty getThemeProperty();

    /**
     * 设置主题的属性
     * @param themeProperty
     */
    void setThemeProperty(ThemeProperty themeProperty);

    /**
     * 刷新当前主题
     */
    void refreshTheme(JFrame frame);

    /**
     * 获取编译配置
     * @return
     */
    CompileBean geCompileConfig();

    /**
     * 设置编译配置
     * @param compileBean
     */
    void setCompileConfig(CompileBean compileBean);

    /**
     * 获取默认证书配置
     * @return
     */
    CredentialsBean getCredentialsConfig();

    /**
     * 设置默认证书配置
     * @param credentialsBean
     */
    void setCredentialsConfig(CredentialsBean credentialsBean);
}
