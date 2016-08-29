package com.wei.apktools.gui;

import com.wei.apktools.config.Theme;
import com.wei.apktools.config.ThemeProperty;
import com.wei.apktools.controller.MenuController;
import com.wei.apktools.interfaces.MenuControllerInterfaces;
import com.wei.apktools.interfaces.MenuModelInterfaces;
import com.wei.apktools.utils.Constant;
import com.wei.apktools.utils.ImageUtils;

import javax.swing.*;
import javax.swing.plaf.ColorUIResource;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

/**
 * Created by starrysky on 15-2-2.
 */
public class ThemeSettingDialog extends JDialog implements ActionListener {

    private static final int WIDTH = 360;
    private static final int HEIGHT = 280;

    private JFrame frame;
    private MenuControllerInterfaces menuControllerInterfaces;
    private MenuModelInterfaces menuModelInterfaces;

    private JPanel focusColor;
    private JPanel backgroundColor;
    private JPanel fontColor;

    private JLabel fontLabel;

    private JButton confirmButton;
    private JButton cancelButton;

    public ThemeSettingDialog(JFrame frame, MenuControllerInterfaces menuControllerInterfaces, MenuModelInterfaces menuModelInterfaces) {
        super(frame, true);

        this.frame = frame;
        this.menuControllerInterfaces = menuControllerInterfaces;
        this.menuModelInterfaces = menuModelInterfaces;

        // 初始化内容
        initPanel();

        // 设置内容
        setContent();

        GuiUtils.pack(frame, this, false);
    }

    private void initPanel() {

        setTitle("界面设置");
        setIconImage(ImageUtils.loadInsideImage(Constant.APP_ICON));

        setLayout(new BorderLayout());
        setContentPane(newContentPanel());
    }

    private void setContent() {

        ThemeProperty themeProperty = menuModelInterfaces.getThemeProperty();

        focusColor.setBackground(themeProperty.getPrimary3());
        backgroundColor.setBackground(themeProperty.getSecondary3());
        fontColor.setBackground(themeProperty.getBlack());

        fontLabel.setFont(themeProperty.getFont());
    }

    private JPanel newContentPanel() {

        JPanel contentPanel = new JPanel(null);
        GuiUtils.revisionSize(contentPanel, WIDTH, HEIGHT);

        contentPanel.add(newSettingsColorPanel());
        contentPanel.add(newFontSettingPanel());
        contentPanel.add(newAppendPanel());

        return contentPanel;
    }

    private JPanel newSettingsColorPanel() {

        JPanel colorPanel = new JPanel(null);
        colorPanel.setBounds(12, 20, WIDTH - 24, 112);
        colorPanel.setBorder(BorderFactory.createTitledBorder(BorderFactory.createEtchedBorder(), "颜色设置"));

        focusColor = new JPanel(null);
        focusColor.setBounds(20, 30, 40, 26);
        focusColor.setBorder(BorderFactory.createEtchedBorder());
        focusColor.addMouseListener(mouseAdapter);

        JLabel label = new JLabel("焦点颜色");
        label.setBounds(70, 28, 100, 28);

        colorPanel.add(focusColor);
        colorPanel.add(label);

        backgroundColor = new JPanel(null);
        backgroundColor.setBounds(180, 30, 40, 26);
        backgroundColor.setBorder(BorderFactory.createEtchedBorder());
        backgroundColor.addMouseListener(mouseAdapter);

        label = new JLabel("背景颜色");
        label.setBounds(230, 28, 100, 28);

        colorPanel.add(backgroundColor);
        colorPanel.add(label);

        fontColor = new JPanel(null);
        fontColor.setBounds(20, 70, 40, 26);
        fontColor.setBorder(BorderFactory.createEtchedBorder());
        fontColor.addMouseListener(mouseAdapter);

        label = new JLabel("字体颜色");
        label.setBounds(70, 68, 100, 28);

        colorPanel.add(fontColor);
        colorPanel.add(label);

        return colorPanel;
    }

    private JPanel newFontSettingPanel() {

        JPanel fontPanel = new JPanel(null);
        fontPanel.setBounds(12, 152, WIDTH - 24, 60);
        fontPanel.setBorder(BorderFactory.createTitledBorder(BorderFactory.createEtchedBorder(), "字体设置"));

        fontLabel = new JLabel( "我只是打酱油的...", SwingConstants.CENTER | SwingConstants.LEFT);
        fontLabel.setBackground(Color.RED);
        fontLabel.setBounds(20, 20, 220, 28);

        JButton fontButton = new JButton("字体");
        fontButton.setBounds(256, 20, 60, 28);
        fontButton.setToolTipText("设置程序字体");
        fontButton.setActionCommand("fonts_settings");
        fontButton.addActionListener(this);

        fontPanel.add(fontLabel);
        fontPanel.add(fontButton);

        return fontPanel;
    }

    private JPanel newAppendPanel() {

        JPanel appendPanel = new JPanel(null);

        appendPanel.setBounds(12, 227, WIDTH - 24, 40);

        int w = appendPanel.getWidth();
        int h = appendPanel.getHeight();

        confirmButton = new JButton("确定");
        confirmButton.setActionCommand("confirm");
        confirmButton.addActionListener(this);
        confirmButton.setBounds(w - 146, (h - 28) >> 1, 60, 28);

        cancelButton = new JButton("取消");
        cancelButton.setActionCommand("cancel");
        cancelButton.addActionListener(this);
        cancelButton.setBounds(w - 66, (h - 28) >> 1, 60, 28);

        appendPanel.add(confirmButton);
        appendPanel.add(cancelButton);

        return appendPanel;
    }

    private void mouseAction(MouseEvent e) {

        Object source = e.getSource();

        if (focusColor == source) {

            Color color = GuiUtils.selectColor(this, "选择焦点颜色", focusColor.getBackground());
            if (color != null) focusColor.setBackground(color);
        } else if (backgroundColor == source) {

            Color color = GuiUtils.selectColor(this, "选择背景颜色", backgroundColor.getBackground());
            if (color != null) backgroundColor.setBackground(color);
        } else if (fontColor == source) {

            Color color = GuiUtils.selectColor(this, "选择字体颜色", fontColor.getBackground());
            if (color != null) fontColor.setBackground(color);
        }
    }

    private MouseAdapter mouseAdapter = new MouseAdapter() {
        @Override
        public void mousePressed(MouseEvent e) {
            super.mousePressed(e);
            mouseAction(e);
        }
    };

    @Override
    public void actionPerformed(ActionEvent e) {

        String command = e.getActionCommand();

        if ("fonts_settings".equals(command)) {

            // 进入字体设置界面
            FontDialog fontDialog = new FontDialog(frame, fontLabel.getFont());
            fontDialog.setVisible(true);

            // 设置显示字体样式
            if (!fontDialog.isCancel()) fontLabel.setFont(fontDialog.getSelectFont());
        } else if ("confirm".equals(command)) {

            ThemeProperty themeProperty = menuModelInterfaces.getThemeProperty();

            // 确定事件操作
            themeProperty.setPrimary(focusColor.getBackground());
            themeProperty.setSecondary(backgroundColor.getBackground());
            themeProperty.setBlack(new ColorUIResource(fontColor.getBackground()));
            themeProperty.setFont(fontLabel.getFont());

            menuControllerInterfaces.setThemeProperty(themeProperty);
            dispose();
        } else if ("cancel".equals(command)) {

            // 取消事件操作
            dispose();
        }
    }
}
