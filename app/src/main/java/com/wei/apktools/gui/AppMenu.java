package com.wei.apktools.gui;

import com.wei.apktools.config.EnvProperty;
import com.wei.apktools.core.Controller;
import com.wei.apktools.interfaces.MainControllerInterfaces;
import com.wei.apktools.interfaces.MainModelInterfaces;
import com.wei.apktools.interfaces.MenuControllerInterfaces;
import com.wei.apktools.interfaces.MenuModelInterfaces;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;

/**
 * Created by starrysky on 15-1-27.
 */
public class AppMenu extends JMenuBar implements ActionListener {

    private JFrame frame;
    private MenuControllerInterfaces menuControllerInterfaces;
    private MenuModelInterfaces menuModelInterfaces;

    public AppMenu(JFrame frame, MenuControllerInterfaces menuControllerInterfaces, MenuModelInterfaces menuModelInterfaces) {
        this.frame = frame;
        this.menuControllerInterfaces = menuControllerInterfaces;
        this.menuModelInterfaces = menuModelInterfaces;

        initMenuBar();
    }

    private void initMenuBar() {

        JMenu menu = new JMenu("菜单(M)");
        menu.setMnemonic(KeyEvent.VK_M);
        add(menu);

        JMenuItem quit = new JMenuItem("退出");
        quit.setActionCommand("quit");
        quit.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_Q, InputEvent.CTRL_DOWN_MASK));
        quit.addActionListener(this);
        menu.add(quit);

        JMenu settings = new JMenu("设置(S)");
        settings.setMnemonic(KeyEvent.VK_S);
        add(settings);

        JMenuItem compileSettings = new JMenuItem("编译设置");
        compileSettings.setActionCommand("compile_settings");
        compileSettings.addActionListener(this);
        settings.add(compileSettings);
        settings.addSeparator();

        JMenu credentialsSettings = new JMenu("证书设置");

        JMenuItem defaultCredentials = new JMenuItem("默认证书");
        defaultCredentials.setActionCommand("default_credentials");
        defaultCredentials.addActionListener(this);
        credentialsSettings.add(defaultCredentials);

        settings.add(credentialsSettings);
        settings.addSeparator();

        JMenuItem themeSettings = new JMenuItem("界面设置");
        themeSettings.setActionCommand("theme_settings");
        themeSettings.addActionListener(this);
        settings.add(themeSettings);

        JMenu help = new JMenu("帮助(H)");
        help.setMnemonic(KeyEvent.VK_H);
        add(help);

        JMenuItem about = new JMenuItem("关于");
        about.setActionCommand("about");
        about.addActionListener(this);
        help.add(about);
    }

    @Override
    public void actionPerformed(ActionEvent e) {

        String command = e.getActionCommand();

        if ("quit".equals(command)) {

            // 退出程序
            menuControllerInterfaces.quit();
        } else if ("theme_settings".equals(command)) {

            // 主题设置
            ThemeSettingDialog themeSettingDialog = new ThemeSettingDialog(
                    frame, menuControllerInterfaces, menuModelInterfaces);
            themeSettingDialog.setVisible(true);
        } else if ("compile_settings".equals(command)) {

            // 编译设置
            ComSettingsDialog comSettingsDialog = new ComSettingsDialog(
                            frame, menuControllerInterfaces, menuModelInterfaces);
            comSettingsDialog.setVisible(true);
        } else if ("about".equals(command)) {

            // 关于界面
            AboutDialog aboutDialog = new AboutDialog(frame);
            aboutDialog.setVisible(true);
        } else if ("default_credentials".equals(command)) {

            // 默认的证书设置
            CredentialsSettingDialog credentialsSettingDialog = new CredentialsSettingDialog(
                            frame, menuControllerInterfaces, menuModelInterfaces);
            credentialsSettingDialog.setVisible(true);
        }
    }
}
