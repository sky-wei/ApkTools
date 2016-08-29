package com.wei.apktools.interfaces;

import com.wei.apktools.core.SignedProperty;

import java.util.List;

/**
 * Created by starrysky on 15-3-16.
 */
public interface CredentialControllerInterfaces {

    /**
     * 显示APK签名信息
     */
    void showSigned();

    /**
     * 进入批量签名
     */
    void batchSigned();

    /**
     * 创建签名证书
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
    void deleteCredentials(SignedProperty... signedPropertys);

    /**
     * 设置默认的签名证书
     * @param signedProperty
     */
    void setDefalutCredentials(SignedProperty signedProperty);

    /**
     * 签名文件
     * @param signedProperty
     */
    void signedFile(SignedProperty signedProperty);

    /**
     * 导入签名证书
     */
    void importCredentials();

    /**
     * 导出签名证书
     * @param signedProperties
     */
    void exportCredentials(List<SignedProperty> signedProperties);
}
