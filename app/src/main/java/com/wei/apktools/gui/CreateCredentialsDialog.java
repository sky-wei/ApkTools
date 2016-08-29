package com.wei.apktools.gui;

import com.wei.apktools.core.SignedProperty;
import com.wei.apktools.interfaces.CredentialControllerInterfaces;
import com.wei.apktools.interfaces.CredentialModelInterfaces;
import com.wei.apktools.utils.Constant;
import com.wei.apktools.utils.ImageUtils;
import com.wei.apktools.utils.StringUtils;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.File;
import java.util.Date;

/**
 * Created by starrysky on 15-3-17.
 */
public class CreateCredentialsDialog extends JDialog implements ActionListener {

    private static final int WIDTH = 390;
    private static final int HEIGHT = 475;

    private CredentialControllerInterfaces credentialControllerInterfaces;
    private CredentialModelInterfaces credentialModelInterfaces;

    private JTextField keystorePasswordText;
    private JTextField termText;
    private JTextField aliasText;
    private JTextField passwordText;
    private JTextField nameText;
    private JTextField organizationText;
    private JTextField cityText;
    private JTextField provinceText;
    private JTextField codeText;

    private JLabel hintLabel;

    public CreateCredentialsDialog(JFrame frame, CredentialControllerInterfaces credentialControllerInterfaces, CredentialModelInterfaces credentialModelInterfaces) {
        super(frame, true);

        this.credentialControllerInterfaces = credentialControllerInterfaces;
        this.credentialModelInterfaces = credentialModelInterfaces;

        initPanel();

        GuiUtils.pack(frame, this, false);
    }

    protected void initPanel() {

        setTitle("创建签名证书");
        setIconImage(ImageUtils.loadInsideImage(Constant.APP_ICON));

        setLayout(new BorderLayout());
        setContentPane(newContentPanel());
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
        credentialsPanel.setBounds(12, 25, WIDTH - 24, 390);
        credentialsPanel.setBorder(BorderFactory.createEtchedBorder());

        JLabel keystoreLable = new JLabel("证书密码:");
        keystoreLable.setBounds(30, 25, 60, 25);
        credentialsPanel.add(keystoreLable);

        keystorePasswordText = new JTextField();
        keystorePasswordText.setBounds(100, 25, 230, 25);
        keystorePasswordText.setToolTipText("证书的密码与下面的密码有区别");
        keystorePasswordText.addFocusListener(textFocusAdapter);
        credentialsPanel.add(keystorePasswordText);

        JLabel termLable = new JLabel("期限(年):");
        termLable.setBounds(30, 65, 60, 25);
        credentialsPanel.add(termLable);

        termText = new JTextField();
        termText.setBounds(100, 65, 230, 25);
        termText.setToolTipText("签名证书的有效期限(单位:年)");
        termText.addFocusListener(textFocusAdapter);
        credentialsPanel.add(termText);

        JLabel aliasLable = new JLabel("别名:");
        aliasLable.setBounds(30, 105, 60, 25);
        credentialsPanel.add(aliasLable);

        aliasText = new JTextField();
        aliasText.setBounds(100, 105, 230, 25);
        aliasText.setToolTipText("签名用户的名称");
        aliasText.addFocusListener(textFocusAdapter);
        credentialsPanel.add(aliasText);

        JLabel passwordLable = new JLabel("密码:");
        passwordLable.setBounds(30, 145, 60, 25);
        credentialsPanel.add(passwordLable);

        passwordText = new JTextField();
        passwordText.setBounds(100, 145, 230, 25);
        passwordText.setToolTipText("签名用户的密码");
        passwordText.addFocusListener(textFocusAdapter);
        credentialsPanel.add(passwordText);

        JLabel nameLable = new JLabel("名称:");
        nameLable.setBounds(30, 185, 60, 25);
        credentialsPanel.add(nameLable);

        nameText = new JTextField();
        nameText.setBounds(100, 185, 230, 25);
        nameText.setToolTipText("签名用户的名称(非真实制)");
        nameText.addFocusListener(textFocusAdapter);
        credentialsPanel.add(nameText);

        JLabel organizationLable = new JLabel("组织单位:");
        organizationLable.setBounds(30, 225, 60, 25);
        credentialsPanel.add(organizationLable);

        organizationText = new JTextField();
        organizationText.setBounds(100, 225, 230, 25);
        organizationText.setToolTipText("签名用户的组织单位");
        organizationText.addFocusListener(textFocusAdapter);
        credentialsPanel.add(organizationText);

        JLabel cityLable = new JLabel("所在城市:");
        cityLable.setBounds(30, 265, 60, 25);
        credentialsPanel.add(cityLable);

        cityText = new JTextField();
        cityText.setBounds(100, 265, 230, 25);
        cityText.setToolTipText("签名用户所在城市");
        cityText.addFocusListener(textFocusAdapter);
        credentialsPanel.add(cityText);

        JLabel provinceLable = new JLabel("所在省份:");
        provinceLable.setBounds(30, 305, 60, 25);
        credentialsPanel.add(provinceLable);

        provinceText = new JTextField();
        provinceText.setBounds(100, 305, 230, 25);
        provinceText.setToolTipText("签名用户所在省份");
        provinceText.addFocusListener(textFocusAdapter);
        credentialsPanel.add(provinceText);

        JLabel codeLable = new JLabel("国家代码:");
        codeLable.setBounds(30, 345, 60, 25);
        credentialsPanel.add(codeLable);

        codeText = new JTextField();
        codeText.setBounds(100, 345, 230, 25);
        codeText.setToolTipText("签名用户的国家代码");
        codeText.addFocusListener(textFocusAdapter);
        credentialsPanel.add(codeText);

        return credentialsPanel;
    }

    private JPanel newAppendPanel() {

        JPanel appendPanel = new JPanel(null);

        appendPanel.setBounds(12, 425, WIDTH - 24, 40);

        int w = appendPanel.getWidth();
        int h = appendPanel.getHeight();

        hintLabel = new JLabel();
        hintLabel.setBounds(10, (h - 25) >> 1, 200, 25);
        hintLabel.setForeground(Color.RED);
        appendPanel.add(hintLabel);

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

        String action = e.getActionCommand();

        if ("confirm".equals(action)) {

            if (verifyInfo()) {

                SignedProperty signedProperty = new SignedProperty();
                signedProperty.setKeystorePassword(keystorePasswordText.getText());
                signedProperty.setSignedName(aliasText.getText());
                signedProperty.setSignedPassword(passwordText.getText());
                signedProperty.setTerm(Integer.parseInt(termText.getText()) * 12);
                signedProperty.setName(nameText.getText());
                signedProperty.setOrganization(organizationText.getText());
                signedProperty.setCity(cityText.getText());
                signedProperty.setProvince(provinceText.getText());
                signedProperty.setCode(codeText.getText());
                signedProperty.setFile(new File(Constant.CREDENTIAL_DIR, StringUtils.dateToString() + ".keystore"));

                // 生成签名证书
                credentialControllerInterfaces.createCredentials(signedProperty);
                dispose();
            }
        } else if ("cancel".equals(action)) {
            dispose();
        }
    }

    private boolean verifyInfo() {

        if (StringUtils.isEmpty(keystorePasswordText.getText())
                || StringUtils.isEmpty(termText.getText())
                || StringUtils.isEmpty(aliasText.getText())
                || StringUtils.isEmpty(passwordText.getText())
                || StringUtils.isEmpty(nameText.getText())
                || StringUtils.isEmpty(organizationText.getText())
                || StringUtils.isEmpty(cityText.getText())
                || StringUtils.isEmpty(provinceText.getText())
                || StringUtils.isEmpty(codeText.getText())) {
            hint("填写的内容不能为空。");
            return false;
        }

        try {
            Integer.parseInt(termText.getText());
        } catch (NumberFormatException e) {
            hint("签名的期限必须是一个有效的整数。");
            return false;
        }

        hint(null);

        return true;
    }

    private void hint(String hint) {

        if (hint == null) {
            hintLabel.setText("");
            return ;
        }
        hintLabel.setText(hint);
    }

    private FocusAdapter textFocusAdapter = new FocusAdapter() {

        @Override
        public void focusLost(FocusEvent e) {
            super.focusLost(e);

            Object source = e.getSource();

            if (source instanceof JTextField) {

                JTextField textField = (JTextField)source;
                hint(StringUtils.isEmpty(textField.getText()) ? "填写的内容不能为空。" : null);

                if (source == termText && !StringUtils.isEmpty(textField.getText())) {
                    try {
                        Integer.parseInt(termText.getText());
                    } catch (NumberFormatException e1) {
                        hint("签名的期限必须是一个有效的整数。");
                    }
                }
            }
        }
    };
}
