package com.wei.apktools.interfaces;

import com.wei.apktools.bean.CredentialsBean;
import com.wei.apktools.core.SignedProperty;

import java.io.File;
import java.util.Observable;
import java.util.Observer;

/**
 * Created by starrysky on 15-3-16.
 */
public interface CredentialModelInterfaces {

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

    /**
     * 获取上一次查看的签名文件
     * @return
     */
    String getLastShowSigned();

    /**
     * 查看指定文件的签名信息
     * @param filePath
     */
    void showSigned(String filePath);

    /**
     * 获取上一次的签名的目录
     * @return
     */
    String getLastSignedDir();

    /**
     * 对APK进入批量签名
     * @param files
     */
    void batchSigned(CredentialsBean credentialsBean, File... files);

    /**
     * 创建签名证书
     * @param signedProperty
     */
    void createCredentials(SignedProperty signedProperty);

    /**
     * 通知更新数据
     */
    void measurementsChanged();

    /**
     * 删除签名证书
     * @param signedPropertys
     */
    boolean deleteCredentials(SignedProperty... signedPropertys);

    /**
     * 签名文件
     * @param signedProperty
     * @param files
     */
    void signedFile(SignedProperty signedProperty, File... files);

    /**
     * 导入签名证书
     */
    boolean importCredentials(File file);

    /**
     * 保存签名证书到指定目录下
     * @param dir
     * @param signedProperty
     */
    boolean exportCredentials(File dir, SignedProperty signedProperty);
}
