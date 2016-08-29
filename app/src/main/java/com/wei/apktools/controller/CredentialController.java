package com.wei.apktools.controller;

import com.wei.apktools.bean.CredentialsBean;
import com.wei.apktools.core.SignedProperty;
import com.wei.apktools.gui.GuiUtils;
import com.wei.apktools.gui.SelectCredentialsDialog;
import com.wei.apktools.interfaces.CredentialControllerInterfaces;
import com.wei.apktools.interfaces.CredentialModelInterfaces;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.io.File;
import java.io.FilenameFilter;
import java.util.List;

/**
 * Created by starrysky on 15-3-17.
 */
public class CredentialController implements CredentialControllerInterfaces {

    private JFrame frame;
    private CredentialModelInterfaces credentialModelInterfaces;

    public CredentialController(JFrame frame, CredentialModelInterfaces credentialModelInterfaces) {
        this.frame = frame;
        this.credentialModelInterfaces = credentialModelInterfaces;
    }

    @Override
    public void showSigned() {

        String lastShowSigned = credentialModelInterfaces.getLastShowSigned();
        File file = GuiUtils.selectOpenFile(frame, lastShowSigned == null
                ? null : lastShowSigned, new FileNameExtensionFilter("选择查看的文件(*.apk)", "apk"));

        // 查看文件的签名信息
        if (file != null) credentialModelInterfaces.showSigned(file.getPath());
    }

    @Override
    public void batchSigned() {

        String lastSignedDir = credentialModelInterfaces.getLastSignedDir();

        File[] files = GuiUtils.selectOpenFiles(frame,
                lastSignedDir == null ? null : lastSignedDir,
                new FileNameExtensionFilter("选择文件(*.apk)", "apk"));

        // 批量签名
        if (files != null && files.length > 0) {

            // 获取默认的签名证书
            CredentialsBean credentialsBean = credentialModelInterfaces.getCredentialsConfig();

            if (credentialsBean.getPath() == null || !new File(credentialsBean.getPath()).isFile()) {
                JOptionPane.showMessageDialog(frame,
                        "当前没有配置默认的签名证书，请设置后再进行操作。", "错误", JOptionPane.ERROR_MESSAGE);
                return ;
            }

            // 进入批量签名
            credentialModelInterfaces.batchSigned(credentialsBean, files);
        }
    }

    @Override
    public void createCredentials(SignedProperty signedProperty) {
        credentialModelInterfaces.createCredentials(signedProperty);
    }

    @Override
    public void measurementsChanged() {
        credentialModelInterfaces.measurementsChanged();
    }

    @Override
    public void deleteCredentials(SignedProperty... signedPropertys) {

        int result = JOptionPane.showConfirmDialog(frame, "是否删除选中的签名证书？", "提示",
                JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);

        if (result != 0) return ;

        if (!credentialModelInterfaces.deleteCredentials(signedPropertys)) {
            JOptionPane.showMessageDialog(frame,
                    "删除签名证书出错了！", "错误", JOptionPane.ERROR_MESSAGE);
        }
        measurementsChanged();
    }

    @Override
    public void setDefalutCredentials(SignedProperty signedProperty) {

        if (signedProperty == null) return ;

        CredentialsBean credentialsBean = new CredentialsBean();

        credentialsBean.setKeyPassword(signedProperty.getKeystorePassword());
        credentialsBean.setAlias(signedProperty.getSignedName());
        credentialsBean.setPassword(signedProperty.getSignedPassword());
        credentialsBean.setPath(signedProperty.getFile().getPath());

        credentialModelInterfaces.setCredentialsConfig(credentialsBean);

        JOptionPane.showMessageDialog(frame,
                "已设置成默认的签名证书。", "提示", JOptionPane.INFORMATION_MESSAGE);
    }

    @Override
    public void signedFile(SignedProperty signedProperty) {

        String lastSignedDir = credentialModelInterfaces.getLastSignedDir();

        File[] files = GuiUtils.selectOpenFiles(frame,
                lastSignedDir == null ? null : lastSignedDir,
                new FileNameExtensionFilter("选择文件(*.apk)", "apk"));

        if (files != null && files.length > 0) {
            // 签名文件
            credentialModelInterfaces.signedFile(signedProperty, files);
        }
    }

    @Override
    public void importCredentials() {

        File file = GuiUtils.selectOpenFile(frame, null,
                new FileNameExtensionFilter("选择导入的文件(*.keystore)", "keystore"));

        if (file == null) return ;

        if (!credentialModelInterfaces.importCredentials(file)) {
            // 导入签名证书失败
            JOptionPane.showMessageDialog(frame,
                    "导入签名证书失败。", "错误", JOptionPane.ERROR_MESSAGE);
            return ;
        }
        measurementsChanged();
    }

    @Override
    public void exportCredentials(List<SignedProperty> signedProperties) {

        if (signedProperties == null || signedProperties.isEmpty()) {
            JOptionPane.showMessageDialog(frame,
                    "当前没有可以导出的签名证书。", "错误", JOptionPane.ERROR_MESSAGE);
            return ;
        }

        SelectCredentialsDialog selectCredentialsDialog = new SelectCredentialsDialog(frame, signedProperties);
        selectCredentialsDialog.setVisible(true);

        if (selectCredentialsDialog.isCancel()) return ;

        SignedProperty signedProperty = selectCredentialsDialog.getSelectSignedProperty();

        File dir = GuiUtils.selectSaveFileDir(frame, null);

        if (dir == null) return ;

        if (credentialModelInterfaces.exportCredentials(dir, signedProperty)) {
            // 导出成功
            JOptionPane.showMessageDialog(frame,
                    "导出签名证书成功。", "提示", JOptionPane.INFORMATION_MESSAGE);
            return ;
        }

        JOptionPane.showMessageDialog(frame,
                "导出签名证书失败。", "错误", JOptionPane.ERROR_MESSAGE);
    }
}
