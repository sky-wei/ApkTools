package com.wei.apktools.gui;

import com.wei.apktools.interfaces.ApplicationControllerInterfaces;
import com.wei.apktools.interfaces.ApplicationModelInterfaces;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.*;

/**
 * Created by starrysky on 15-3-26.
 */
public class ApplicationPanel extends JPanel implements ActionListener {

    private JFrame frame;
    private ApplicationControllerInterfaces applicationControllerInterfaces;
    private ApplicationModelInterfaces applicationModelInterfaces;

    private ApplicationTableModel applicationTableModel;
    private JTable applicationTable;

    private JTextArea expandTextArea;

    public ApplicationPanel(JFrame frame, ApplicationControllerInterfaces applicationControllerInterfaces, ApplicationModelInterfaces applicationModelInterfaces) {
        this.frame = frame;
        this.applicationControllerInterfaces = applicationControllerInterfaces;
        this.applicationModelInterfaces = applicationModelInterfaces;

        initPanel();
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

        toolBar.add(GuiUtils.newButton("添加", "add_application", this), "添加需要操作的APK文件");
//        toolBar.add(GuiUtils.newButton("批量签名", "batch_signed", this, "使用默认签名证书对指定APK文件进行签名"));
//        toolBar.addSeparator(new Dimension(30, 20));
//        toolBar.add(GuiUtils.newButton("创建", "create_credentials", this), "创建签名证书");
//        toolBar.addSeparator();
//        toolBar.add(GuiUtils.newButton("导入", "import_credentials", this), "导入外部的签名证书");
//        toolBar.add(GuiUtils.newButton("导出", "export_credentials", this), "导出签名证书到指定目录下");

        toolPanel.add(toolBar, BorderLayout.EAST);

        return toolPanel;
    }

    private JPanel newContentPanel() {

        JPanel contentPanel = new JPanel(new BorderLayout());
        contentPanel.setBorder(BorderFactory.createEmptyBorder(4, 2, 2, 2));

        applicationTableModel = new ApplicationTableModel();
        applicationTableModel.addColumn("应用名称");
        applicationTableModel.addColumn("包名");
        applicationTableModel.addColumn("版本名");
        applicationTableModel.addColumn("版本号");
        applicationTableModel.addColumn("文件路径");

        applicationTable = new JTable(applicationTableModel);
        applicationTable.setAutoResizeMode(JTable.AUTO_RESIZE_LAST_COLUMN);
        applicationTable.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
        applicationTable.addMouseListener(tableMouseAdapter);
        applicationTable.addKeyListener(tableKeyAdapter);
        JScrollPane jScrollPane = new JScrollPane(applicationTable);

        // 显示扩展的信息
        expandTextArea = new JTextArea("\n\n\n\n\n\n\n\n\n\n");
        expandTextArea.setEditable(false);
        expandTextArea.setBackground(contentPanel.getBackground());
        JScrollPane expandScrollPane = new JScrollPane(expandTextArea);

        contentPanel.add(jScrollPane, BorderLayout.CENTER);
        contentPanel.add(expandScrollPane, BorderLayout.SOUTH);

        return contentPanel;
    }

    private void showPopupMenu(MouseEvent e) {

    }

    private void updateExpandInfo() {

    }

    @Override
    public void actionPerformed(ActionEvent e) {

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

    private class ApplicationTableModel extends DefaultTableModel {
        @Override
        public boolean isCellEditable(int row, int column) {
            return false;
        }
    }
}
