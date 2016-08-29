package com.wei.apktools.main;

import com.wei.apktools.gui.*;
import com.wei.apktools.interfaces.MainControllerInterfaces;
import com.wei.apktools.interfaces.MainModelInterfaces;
import com.wei.apktools.task.TaskManager;
import com.wei.apktools.utils.Constant;
import com.wei.apktools.utils.ImageUtils;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

/**
 * Created by starrysky on 15-3-4.
 */
public class MainFrame extends JFrame {

    public static final int WIDTH = 800;
    public static final int HEIGHT = 600;

    private MainControllerInterfaces mainControllerInterfaces;
    private MainModelInterfaces mainModelInterfaces;

    private JTabbedPane tabbedPane;

    private ApplicationPanel applicationPanel;
    private CredentialsPanel credentialsPanel;
    private OutInfoPanel outInfoPanel;

    public MainFrame(MainControllerInterfaces mainControllerInterfaces, MainModelInterfaces mainModelInterfaces) {
        this.mainControllerInterfaces = mainControllerInterfaces;
        this.mainModelInterfaces = mainModelInterfaces;
    }

    public void createView() {

        setTitle("Tools" + Constant.VERSION_NAME + "  -  starrysky");
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);

        setIconImage(ImageUtils.loadInsideImage(Constant.APP_ICON));
        setJMenuBar(new AppMenu(this,
                mainControllerInterfaces.getMenuControllerInterfaces(),
                mainModelInterfaces.getMenuModelInterfaces()));
        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosed(WindowEvent e) {
                super.windowClosed(e);
                // 退出事件处理
                mainControllerInterfaces.quit();
            }
        });

        tabbedPane = new JTabbedPane(JTabbedPane.BOTTOM);
        GuiUtils.revisionSize(tabbedPane, WIDTH, HEIGHT);

        // 添加到界面中
        add(tabbedPane, BorderLayout.CENTER);

        // 应用信息面板
        applicationPanel = new ApplicationPanel(this, null, null);

        // 签名信息面板
        credentialsPanel = new CredentialsPanel(
                this,
                mainControllerInterfaces.getCredentialControllerInterfaces(),
                mainModelInterfaces.getCredentialModelInterfaces());

        // 输出信息的面板
        outInfoPanel = new OutInfoPanel(
                mainControllerInterfaces.getOutInfoControllerInterfaces(),
                mainModelInterfaces.getOutInfoModelInterfaces());

        tabbedPane.add("应用管理", applicationPanel);
        tabbedPane.add("证书管理", credentialsPanel);
        tabbedPane.add("输出信息", outInfoPanel);

        // 设置当前主题
        mainModelInterfaces.getMenuModelInterfaces().refreshTheme(this);

        GuiUtils.pack(this, false);
        setVisible(true);

        // 设置输出...
        TaskManager.getInstance().setOutputInfo(outInfoPanel);

        // 添加特殊的事件处理
        tabbedPane.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                super.keyPressed(e);

                if (KeyEvent.VK_L == e.getKeyCode()
                        && e.isControlDown()
                        && e.isShiftDown()) {

                    // 显示输入的提示框
                    showInputDialog();
                }
            }
        });
    }

    /**
     * 特殊的方法，不需要提供给外部
     */
    private void showInputDialog() {

//        String result = JOptionPane.showInputDialog(
//                this, "请输入通行指令：", "提示", JOptionPane.QUESTION_MESSAGE);
//
//        if ("love".equals(result)) {
//
//            // 可以查看亲爱的照片
//            ImageDialog imageDialog = new ImageDialog(this);
//            imageDialog.setVisible(true);
//        }
    }
}
