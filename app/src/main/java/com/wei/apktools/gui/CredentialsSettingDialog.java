package com.wei.apktools.gui;

import com.wei.apktools.bean.CredentialsBean;
import com.wei.apktools.config.EnvProperty;
import com.wei.apktools.interfaces.MenuControllerInterfaces;
import com.wei.apktools.interfaces.MenuModelInterfaces;
import com.wei.apktools.utils.Constant;
import com.wei.apktools.utils.ImageUtils;

import javax.swing.*;
import javax.swing.border.EtchedBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

/**
 * Created by starrysky on 15-2-6.
 */
public class CredentialsSettingDialog extends JDialog implements ActionListener {

    private static final int WIDTH = 400;
    private static final int HEIGHT = 320;

    private MenuControllerInterfaces menuControllerInterfaces;
    private MenuModelInterfaces menuModelInterfaces;

    private JTextField keyPasswordText;
    private JTextField aliasText;
    private JTextField passwordText;
    private JTextField pathText;

    public CredentialsSettingDialog(JFrame frame, MenuControllerInterfaces menuControllerInterfaces, MenuModelInterfaces menuModelInterfaces) {
        super(frame, true);

        this.menuControllerInterfaces = menuControllerInterfaces;
        this.menuModelInterfaces = menuModelInterfaces;

        // 初始化内容
        initPanel();

        // 设置内容
        setContent();

        GuiUtils.pack(frame, this, false);
    }

    private void initPanel() {

        setTitle("默认证书设置");
        setIconImage(ImageUtils.loadInsideImage(Constant.APP_ICON));

        setLayout(new BorderLayout());
        setContentPane(newContentPanel());
    }

    private void setContent() {

        CredentialsBean credentialsBean = menuModelInterfaces.getCredentialsConfig();

        keyPasswordText.setText(credentialsBean.getKeyPassword());
        aliasText.setText(credentialsBean.getAlias());
        passwordText.setText(credentialsBean.getPassword());
        pathText.setText(credentialsBean.getPath());
    }

    private JPanel newContentPanel() {

        JPanel contentPanel = new JPanel(null);
        GuiUtils.revisionSize(contentPanel, WIDTH, HEIGHT);

        contentPanel.add(newCredentialsPanel());
        contentPanel.add(newAppendPanel());

        return contentPanel;
    }

    private JPanel newCredentialsPanel() {

        JPanel credentialsPanel = new JPanel(null);
        credentialsPanel.setBounds(12, 25, WIDTH - 24, 235);
        credentialsPanel.setBorder(BorderFactory.createEtchedBorder());

        JLabel keystoreLable = new JLabel("证书密码:");
        keystoreLable.setBounds(30, 25, 60, 25);
        credentialsPanel.add(keystoreLable);

        keyPasswordText = new JTextField();
        keyPasswordText.setBounds(100, 25, 240, 25);
        credentialsPanel.add(keyPasswordText);

        JLabel nameLable = new JLabel("名称:");
        nameLable.setBounds(30, 65, 60, 25);
        credentialsPanel.add(nameLable);

        aliasText = new JTextField();
        aliasText.setBounds(100, 65, 240, 25);
        credentialsPanel.add(aliasText);

        JLabel passwordLable = new JLabel("密码:");
        passwordLable.setBounds(30, 105, 60, 25);
        credentialsPanel.add(passwordLable);

        passwordText = new JTextField();
        passwordText.setBounds(100, 105, 240, 25);
        credentialsPanel.add(passwordText);

        JLabel pathLable = new JLabel("路径:");
        pathLable.setBounds(30, 145, 60, 25);
        credentialsPanel.add(pathLable);

        pathText = new JTextField();
        pathText.setBounds(100, 145, 240, 25);
        credentialsPanel.add(pathText);

        JButton select = new JButton("选择");
        select.setActionCommand("select_credentials");
        select.setBounds(275, 190, 65, 25);
        select.addActionListener(this);
        credentialsPanel.add(select);

        JButton defaults = new JButton("恢复默认");
        defaults.setActionCommand("restore_default");
        defaults.setBounds(145, 190, 115, 25);
        defaults.addActionListener(this);
        credentialsPanel.add(defaults);

        return credentialsPanel;
    }

    private JPanel newAppendPanel() {

        JPanel appendPanel = new JPanel(null);

        appendPanel.setBounds(12, 270, WIDTH - 24, 40);

        int w = appendPanel.getWidth();
        int h = appendPanel.getHeight();

        JButton confirmButton = new JButton("确定");
        confirmButton.setActionCommand("confirm");
        confirmButton.addActionListener(this);
        confirmButton.setBounds(w - 146, (h - 28) >> 1, 60, 28);

        JButton cancelButton = new JButton("取消");
        cancelButton.setActionCommand("cancel");
        cancelButton.addActionListener(this);
        cancelButton.setBounds(w - 66, (h - 28) >> 1, 60, 28);

        appendPanel.add(confirmButton);
        appendPanel.add(cancelButton);

        return appendPanel;
    }

    @Override
    public void actionPerformed(ActionEvent e) {

        String command = e.getActionCommand();

        if ("restore_default".equals(command)) {

            // 重新设置原有的信息
            setContent();
        } else if ("select_credentials".equals(command)) {

            // 选择证书文件
            File path = GuiUtils.selectOpenFile(this, pathText.getText());
            if (path != null) pathText.setText(path.getPath());
        } else if ("confirm".equals(command)) {

            // 保存证书信息
            CredentialsBean credentialsBean = menuModelInterfaces.getCredentialsConfig();

            credentialsBean.setKeyPassword(keyPasswordText.getText());
            credentialsBean.setAlias(aliasText.getText());
            credentialsBean.setPassword(passwordText.getText());
            credentialsBean.setPath(pathText.getText());

            menuControllerInterfaces.setCredentialsConfig(credentialsBean);
            dispose();
        } else if ("cancel".equals(command)) {

            // 取消t
            dispose();
        }
    }
}
