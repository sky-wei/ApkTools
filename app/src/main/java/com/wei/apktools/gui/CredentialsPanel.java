package com.wei.apktools.gui;

import com.wei.apktools.core.SignedProperty;
import com.wei.apktools.interfaces.CredentialControllerInterfaces;
import com.wei.apktools.interfaces.CredentialModelInterfaces;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.*;
import java.util.*;
import java.util.List;

/**
 * Created by starrysky on 15-2-5.
 */
public class CredentialsPanel extends JPanel implements ActionListener, Observer {

    private JFrame frame;
    private CredentialControllerInterfaces credentialControllerInterfaces;
    private CredentialModelInterfaces credentialModelInterfaces;

    private CredentialsTableModel credentialsTableModel;
    private JTable credentialsTable;

    private JTextArea expandTextArea;

    private List<SignedProperty> signedProperties;

    public CredentialsPanel(JFrame frame, CredentialControllerInterfaces credentialControllerInterfaces, CredentialModelInterfaces credentialModelInterfaces) {
        this.frame = frame;
        this.credentialControllerInterfaces = credentialControllerInterfaces;
        this.credentialModelInterfaces = credentialModelInterfaces;

        initPanel();

        if (credentialModelInterfaces instanceof Observable) {
            // 添加监听
            Observable observable = (Observable)credentialModelInterfaces;
            observable.addObserver(this);
        }
        credentialControllerInterfaces.measurementsChanged();
    }

    private void initPanel() {

        setLayout(new BorderLayout());

        add(newToolPanel(), BorderLayout.NORTH);
        add(newContentPanel(), BorderLayout.CENTER);
    }

    private JPanel newToolPanel() {

        JPanel toolPanel = new JPanel(new BorderLayout());
        toolPanel.setBorder(BorderFactory.createEmptyBorder(4, 2, 2, 2));

        JToolBar toolBar = new JToolBar();
        toolBar.setFloatable(false);

        toolBar.add(GuiUtils.newButton("查看签名", "show_signed", this), "查看指定APK文件的签名信息");
        toolBar.add(GuiUtils.newButton("批量签名", "batch_signed", this, "使用默认签名证书对指定APK文件进行签名"));
        toolBar.addSeparator(new Dimension(30, 20));
        toolBar.add(GuiUtils.newButton("创建", "create_credentials", this), "创建签名证书");
        toolBar.addSeparator();
        toolBar.add(GuiUtils.newButton("导入", "import_credentials", this), "导入外部的签名证书");
        toolBar.add(GuiUtils.newButton("导出", "export_credentials", this), "导出签名证书到指定目录下");

        toolPanel.add(toolBar, BorderLayout.EAST);

        return toolPanel;
    }

    private JPanel newContentPanel() {

        JPanel contentPanel = new JPanel(new BorderLayout());
        contentPanel.setBorder(BorderFactory.createEmptyBorder(4, 2, 2, 2));

        credentialsTableModel = new CredentialsTableModel();
        credentialsTableModel.addColumn("ID");
        credentialsTableModel.addColumn("证书密码");
        credentialsTableModel.addColumn("别名");
        credentialsTableModel.addColumn("密码");
        credentialsTableModel.addColumn("路径");
        credentialsTableModel.addColumn("创建时间");

        credentialsTable = new JTable(credentialsTableModel);
        credentialsTable.setAutoResizeMode(JTable.AUTO_RESIZE_LAST_COLUMN);
        credentialsTable.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
        credentialsTable.addMouseListener(tableMouseAdapter);
        credentialsTable.addKeyListener(tableKeyAdapter);
        JScrollPane jScrollPane = new JScrollPane(credentialsTable);

        // 显示扩展的信息
        expandTextArea = new JTextArea();
        expandTextArea.setEditable(false);
        expandTextArea.setBackground(contentPanel.getBackground());
        JScrollPane expandScrollPane = new JScrollPane(expandTextArea);

        contentPanel.add(jScrollPane, BorderLayout.CENTER);
        contentPanel.add(expandScrollPane, BorderLayout.SOUTH);

        return contentPanel;
    }

    private void updateExpandInfo() {

        int rowCount = credentialsTable.getSelectedRowCount();

        if (rowCount <= 0) {
            expandTextArea.setText("\n\n\n\n\n\n\n\n\n\n");
            return ;
        }

        // 获取列表索引id
        int[] indexs = credentialsTable.getSelectedRows();

        StringBuffer expandInfo = new StringBuffer();

        for (int i = 0; i < indexs.length; i++) {

            SignedProperty signerInfo = signedProperties.get(indexs[i]);

            expandInfo.append("证书密码: " + signerInfo.getKeystorePassword() + "\n");
            expandInfo.append("期限(年): " + (signerInfo.getTerm() / 12) + "\n");
            expandInfo.append("别名: " + signerInfo.getSignedName() + "\n");
            expandInfo.append("密码: " + signerInfo.getSignedPassword() + "\n");
            expandInfo.append("名称: " + signerInfo.getName() + "\n");
            expandInfo.append("组织单位: " + signerInfo.getOrganization() + "\n");
            expandInfo.append("所在城市: " + signerInfo.getCity() + "\n");
            expandInfo.append("所在省份: " + signerInfo.getProvince() + "\n");
            expandInfo.append("国家代码: " + signerInfo.getCode() + "\n");
            expandInfo.append("创建时间: " + signerInfo.getCreateTime() + "\n");
            expandInfo.append("文件路径: " + signerInfo.getFile().getPath());

            if (i != indexs.length - 1) expandInfo.append("\n\n");
        }

        expandTextArea.setText(expandInfo.toString());
    }

    private void showPopupMenu(MouseEvent e) {

        int rowCount = credentialsTable.getSelectedRowCount();

        JPopupMenu popupMenu = new JPopupMenu();

        popupMenu.add(GuiUtils.newMenuItem("刷新列表", "refresh", this));

        if (rowCount > 0) {

            popupMenu.addSeparator();
            popupMenu.add(GuiUtils.newMenuItem("删除证书", "delete", this));

            if (rowCount == 1) {

                // 等于1的菜单
                popupMenu.addSeparator();
                popupMenu.add(GuiUtils.newMenuItem("签名文件", "signed_file", this));
                popupMenu.add(GuiUtils.newMenuItem("设为默认证书", "default_credentials", this));
            } else if (rowCount > 1) {

                // 大于1的菜单(暂时没有什么特殊处理)
            }
        }

        popupMenu.show(credentialsTable, e.getX(), e.getY());
    }

    private MouseAdapter tableMouseAdapter = new MouseAdapter() {

        @Override
        public void mousePressed(MouseEvent e) {
            super.mousePressed(e);

            if (MouseEvent.BUTTON3 == e.getButton()) {
                // 显示右键菜单
                showPopupMenu(e);
            }
        }

        @Override
        public void mouseReleased(MouseEvent e) {
            super.mouseReleased(e);

            if (MouseEvent.BUTTON1 == e.getButton()) {
                // 更新扩展信息
                updateExpandInfo();
            }
        }

        @Override
        public void mouseMoved(MouseEvent e) {
            super.mouseMoved(e);

            if (MouseEvent.BUTTON1 == e.getButton()) {
                // 更新扩展信息
                updateExpandInfo();
            }
        }
    };

    private KeyAdapter tableKeyAdapter = new KeyAdapter() {
        @Override
        public void keyReleased(KeyEvent e) {
            super.keyReleased(e);

            if (KeyEvent.VK_UP == e.getKeyCode()
                    || KeyEvent.VK_DOWN == e.getKeyCode()
                    || KeyEvent.VK_ENTER == e.getKeyCode()) {
                // 更新扩展信息
                updateExpandInfo();
            }
        }
    };

    @Override
    public void actionPerformed(ActionEvent e) {

        String command = e.getActionCommand();

        if ("show_signed".equals(command)) {
            // 显示签名信息
            credentialControllerInterfaces.showSigned();
        } else if ("batch_signed".equals(command)) {
            // 批量签名
            credentialControllerInterfaces.batchSigned();
        } else if ("create_credentials".equals(command)) {
            // 创建签名证书
            CreateCredentialsDialog createCredentialsDialog = new CreateCredentialsDialog(
                    frame, credentialControllerInterfaces, credentialModelInterfaces);
            createCredentialsDialog.setVisible(true);
        } else if ("import_credentials".equals(command)) {
            // 导入签名证书
            credentialControllerInterfaces.importCredentials();
        } else if ("export_credentials".equals(command)) {
            // 导出签名证书
            credentialControllerInterfaces.exportCredentials(signedProperties);
        } else if ("refresh".equals(command)) {
            // 刷新
            credentialControllerInterfaces.measurementsChanged();
        } else if ("delete".equals(command)) {
            // 删除签名证书
            credentialControllerInterfaces.deleteCredentials(getSelectCredentiales());
        } else if ("signed_file".equals(command)) {
            // 签名文件
            credentialControllerInterfaces.signedFile(getSelectCredentials());
        } else if ("default_credentials".equals(command)) {
            // 设置成默认的签名证书
            credentialControllerInterfaces.setDefalutCredentials(getSelectCredentials());
        }
    }

    private SignedProperty getSelectCredentials() {

        int rowCount = credentialsTable.getSelectedRowCount();

        if (rowCount <= 0) return null;

        return signedProperties.get(credentialsTable.getSelectedRow());
    }

    private SignedProperty[] getSelectCredentiales() {

        int rowCount = credentialsTable.getSelectedRowCount();

        if (rowCount <= 0) return null;

        int[] indexs = credentialsTable.getSelectedRows();
        SignedProperty[] signedPropertys = new SignedProperty[indexs.length];

        for (int i = 0; i < indexs.length; i++) {
            signedPropertys[i] = this.signedProperties.get(indexs[i]);
        }

        return signedPropertys;
    }

    @Override
    public void update(Observable o, Object arg) {

        if (arg == null) return ;

        signedProperties = (List<SignedProperty>)arg;
        clearAllRow();
        addRows(signedProperties);
        updateExpandInfo();
    }

    /**
     * 删除表中所有的行
     */
    private void clearAllRow() {

        int rowCount = credentialsTableModel.getRowCount();

        for (int i = 0; i < rowCount; i++) {
            credentialsTableModel.removeRow(0);
        }
    }

    private void addRows(List<SignedProperty> signedProperties) {

        if (signedProperties == null || signedProperties.isEmpty()) {
            return ;
        }

        for (int i = 0; i < signedProperties.size(); i++) {
            addRow(signedProperties.get(i));
        }
    }

    private void addRow(SignedProperty signerInfo) {

        if (signerInfo == null)	return ;

        String[] values = {Integer.toString(signerInfo.getId()),
                signerInfo.getKeystorePassword(), signerInfo.getSignedName(),
                signerInfo.getSignedPassword(), signerInfo.getFile().getPath(),
                signerInfo.getCreateTime()};

        credentialsTableModel.addRow(values);
    }

    private class CredentialsTableModel extends DefaultTableModel {
        @Override
        public boolean isCellEditable(int row, int column) {
            return false;
        }
    }
}
