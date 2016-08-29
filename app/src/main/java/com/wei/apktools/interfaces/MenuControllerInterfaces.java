package com.wei.apktools.interfaces;

import com.wei.apktools.bean.CompileBean;
import com.wei.apktools.bean.CredentialsBean;
import com.wei.apktools.config.ThemeProperty;

/**
 * Created by starrysky on 15-3-16.
 */
public interface MenuControllerInterfaces {

    /**
     * 退出程序
     */
    void quit();

    /**
     * 设置主题的属性
     * @param themeProperty
     */
    void setThemeProperty(ThemeProperty themeProperty);

    /**
     * 设置编译配置
     * @param compileBean
     */
    void setCompileConfig(CompileBean compileBean);

    /**
     * 设置默认证书配置
     * @param credentialsBean
     */
    void setCredentialsConfig(CredentialsBean credentialsBean);
}
