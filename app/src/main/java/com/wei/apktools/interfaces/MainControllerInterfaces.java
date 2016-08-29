package com.wei.apktools.interfaces;

/**
 * Created by starrysky on 15-3-4.
 */
public interface MainControllerInterfaces {

    /**
     * 获取菜单的控制对象
     * @return
     */
    MenuControllerInterfaces getMenuControllerInterfaces();

    /**
     * 获取输出信息控制对象
     * @return
     */
    OutInfoControllerInterfaces getOutInfoControllerInterfaces();

    /**
     * 签名证书控制对象
     * @return
     */
    CredentialControllerInterfaces getCredentialControllerInterfaces();

    /**
     * 退出程序
     */
    void quit();
}
