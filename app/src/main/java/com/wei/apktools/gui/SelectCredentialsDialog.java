package com.wei.apktools.gui;

import com.wei.apktools.core.SignedProperty;
import com.wei.apktools.utils.Constant;
import com.wei.apktools.utils.ImageUtils;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

/**
 * Created by starrysky on 15-3-23.
 */
public class SelectCredentialsDialog extends JDialog implements ActionListener {

    private static final int WIDTH = 400;
    private static final int HEIGHT = 280;

    private List<SignedProperty> signedProperties;

    private JComboBox<String> comboBox;
    private JTextArea textArea;

    private boolean cancel;
    private SignedProperty selectSignedProperty;

    public SelectCredentialsDialog(JFrame frame, List<SignedProperty>signedProperties) {
        super(frame, true);

        this.signedProperties = signedProperties;
        cancel = true;

        initPanel();

        setContent();

        GuiUtils.pack(frame, this, false);
    }

    public boolean isCancel() {
        return cancel;
    }

    public SignedProperty getSelectSignedProperty() {
        return selectSignedProperty;
    }

    private void initPanel() {

        setTitle("导出签名证书");
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
        credentialsPanel.setBounds(12, 25, WIDTH - 24, 195);
        credentialsPanel.setBorder(BorderFactory.createEtchedBorder());

        JLabel label = new JLabel("选择签名证书:");
        label.setBounds(30, 25, 80, 25);
        credentialsPanel.add(label);

        comboBox = new JComboBox<>();
        comboBox.setBounds(140, 25, 205, 25);
        comboBox.setActionCommand("select_credential");
        comboBox.addActionListener(this);
        credentialsPanel.add(comboBox);

        textArea = new JTextArea();
        textArea.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(textArea);
        scrollPane.setBounds(30, 65, 316, 110);
        credentialsPanel.add(scrollPane);

        return credentialsPanel;
    }

    private JPanel newAppendPanel() {

        JPanel appendPanel = new JPanel(null);

        appendPanel.setBounds(12, 230, WIDTH - 24, 40);

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

    private void setContent() {

        for (int i = 0; i < signedProperties.size(); i++) {
            SignedProperty signedProperty = signedProperties.get(i);
            comboBox.addItem(signedProperty.getId() + "  --  " + signedProperty.getSignedName());
        }
    }

    private String toInfo(SignedProperty signedProperty) {

        if (signedProperty == null) return null;

        StringBuffer expandInfo = new StringBuffer();

        expandInfo.append("证书密码: " + signedProperty.getKeystorePassword() + "\n");
        expandInfo.append("期限(年): " + (signedProperty.getTerm() / 12) + "\n");
        expandInfo.append("别名: " + signedProperty.getSignedName() + "\n");
        expandInfo.append("密码: " + signedProperty.getSignedPassword() + "\n");
        expandInfo.append("创建时间: " + signedProperty.getCreateTime() + "\n");
        expandInfo.append("文件路径: " + signedProperty.getFile().getPath());

        return expandInfo.toString();
    }

    @Override
    public void actionPerformed(ActionEvent e) {

        String command = e.getActionCommand();

        if ("confirm".equals(command)) {

            // 确定事件
            cancel = false;
            int index = comboBox.getSelectedIndex();
            selectSignedProperty = signedProperties.get(index);
            dispose();
        } else if ("cancel".equals(command)) {

            // 取消
            dispose();
        } else if ("select_credential".equals(command)) {

            // 选择签名证书-设置信息
            int index = comboBox.getSelectedIndex();
            textArea.setText(toInfo(signedProperties.get(index)));
        }
    }
}
