package com.wei.apktools.gui;

import com.wei.apktools.bean.CompileBean;
import com.wei.apktools.config.EnvProperty;
import com.wei.apktools.interfaces.MenuControllerInterfaces;
import com.wei.apktools.interfaces.MenuModelInterfaces;
import com.wei.apktools.utils.Constant;
import com.wei.apktools.utils.ImageUtils;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

/**
 * Created by starrysky on 15-1-19.
 */
public class ComSettingsDialog extends JDialog implements ActionListener {

    private static final int WIDTH = 500;
    private static final int HEIGHT = 370;

    private MenuControllerInterfaces menuControllerInterfaces;
    private MenuModelInterfaces menuModelInterfaces;

    private JTextField jdkTextField;
    private JButton selectJdkPath;

    private JTextField outTextField;
    private JButton selectOutPath;

    private JTextField tempTextField;
    private JButton selectTempPath;

    private JCheckBox clearTempDir;

    private JButton confirmButton;
    private JButton cancelButton;

    public ComSettingsDialog(JFrame frame, MenuControllerInterfaces menuControllerInterfaces, MenuModelInterfaces menuModelInterfaces) {
        super(frame, true);

        this.menuControllerInterfaces = menuControllerInterfaces;
        this.menuModelInterfaces = menuModelInterfaces;

        // 初始化内容
        initPanel();

        // 设置内容
        setContent();

        GuiUtils.pack(frame, this, false);
    }

    private JPanel newContentPanel() {

        JPanel contentPanel = new JPanel(null);
        GuiUtils.revisionSize(contentPanel, WIDTH, HEIGHT);

        contentPanel.add(newJdkPanel());
        contentPanel.add(newOutDirPanel());
        contentPanel.add(newTempDirPanel());
        contentPanel.add(newAppendPanel());

        return contentPanel;
    }

    private JPanel newJdkPanel() {

        JPanel jdkPanel = new JPanel(null);
        jdkPanel.setBounds(12, 20, WIDTH - 24, 80);
        jdkPanel.setBorder(BorderFactory.createTitledBorder(
                BorderFactory.createEtchedBorder(), "JDK设置"));

        int w = jdkPanel.getWidth();
        int h = jdkPanel.getHeight();

        jdkTextField = new JTextField();
        jdkTextField.setEditable(false);
        jdkTextField.setBounds(18, ((h - 30) >> 1) + 4, w - 110, 30);

        selectJdkPath = new JButton("选择");
        selectJdkPath.setActionCommand("jdk_path");
        selectJdkPath.addActionListener(this);
        selectJdkPath.setBounds(jdkTextField.getX() + jdkTextField.getWidth() + 10,
                ((h - 28) >> 1) + 4, w - jdkTextField.getWidth() - 46, 28);

        jdkPanel.add(jdkTextField);
        jdkPanel.add(selectJdkPath);

        return jdkPanel;
    }

    private JPanel newOutDirPanel() {

        JPanel outDirPanel = new JPanel(null);

        outDirPanel.setBounds(12, 120, WIDTH - 24, 80);
        outDirPanel.setBorder(BorderFactory.createTitledBorder(
                BorderFactory.createEtchedBorder(), "输出目录设置"));

        int w = outDirPanel.getWidth();
        int h = outDirPanel.getHeight();

        outTextField = new JTextField();
        outTextField.setEditable(false);
        outTextField.setBounds(18, ((h - 30) >> 1) + 4, w - 110, 30);

        selectOutPath = new JButton("选择");
        selectOutPath.setActionCommand("out_path");
        selectOutPath.addActionListener(this);
        selectOutPath.setBounds(outTextField.getX() + outTextField.getWidth() + 10,
                ((h - 28) >> 1) + 4, w - outTextField.getWidth() - 46, 28);

        outDirPanel.add(outTextField);
        outDirPanel.add(selectOutPath);

        return outDirPanel;
    }

    private JPanel newTempDirPanel() {

        JPanel tempDirPanel = new JPanel(null);

        tempDirPanel.setBounds(12, 220, WIDTH - 24, 80);
        tempDirPanel.setBorder(BorderFactory.createTitledBorder(
                BorderFactory.createEtchedBorder(), "临时目录设置"));

        int w = tempDirPanel.getWidth();
        int h = tempDirPanel.getHeight();

        tempTextField = new JTextField();
        tempTextField.setEditable(false);
        tempTextField.setBounds(18, ((h - 30) >> 1) + 4, w - 110, 30);

        selectTempPath = new JButton("选择");
        selectTempPath.setActionCommand("temp_path");
        selectTempPath.addActionListener(this);
        selectTempPath.setBounds(tempTextField.getX() + tempTextField.getWidth() + 10,
                ((h - 28) >> 1) + 4, w - tempTextField.getWidth() - 46, 28);

        tempDirPanel.add(tempTextField);
        tempDirPanel.add(selectTempPath);

        return tempDirPanel;
    }

    private JPanel newAppendPanel() {

        JPanel appendPanel = new JPanel(null);

        appendPanel.setBounds(12, 316, WIDTH - 24, 40);

        int w = appendPanel.getWidth();
        int h = appendPanel.getHeight();

        clearTempDir = new JCheckBox("是否清除临时目录");
        clearTempDir.setBounds(0, (h - 28) >> 1, 160, 28);

        confirmButton = new JButton("确定");
        confirmButton.setActionCommand("confirm");
        confirmButton.addActionListener(this);
        confirmButton.setBounds(w - 146, (h - 28) >> 1, 60, 28);

        cancelButton = new JButton("取消");
        cancelButton.setActionCommand("cancel");
        cancelButton.addActionListener(this);
        cancelButton.setBounds(w - 66, (h - 28) >> 1, 60, 28);

        appendPanel.add(clearTempDir);
        appendPanel.add(confirmButton);
        appendPanel.add(cancelButton);

        return appendPanel;
    }

    protected void initPanel() {

        setTitle("编译设置");
        setIconImage(ImageUtils.loadInsideImage(Constant.APP_ICON));

        setLayout(new BorderLayout());
        setContentPane(newContentPanel());
    }

    protected void setContent() {

        CompileBean compileBean = menuModelInterfaces.geCompileConfig();

        jdkTextField.setToolTipText("JDK_HOME");
        jdkTextField.setText(compileBean.getJdkPath());

        outTextField.setToolTipText("输出目录");
        outTextField.setText(compileBean.getOutPath());

        tempTextField.setToolTipText("临时目录");
        tempTextField.setText(compileBean.getTempPath());

        clearTempDir.setSelected(compileBean.isClearTempDir());
    }

    @Override
    public void actionPerformed(ActionEvent e) {

        String command = e.getActionCommand();

        if ("confirm".equals(command)) {

            // 保存环境属性信息
            CompileBean compileBean = menuModelInterfaces.geCompileConfig();

            compileBean.setJdkPath(jdkTextField.getText());
            compileBean.setOutPath(outTextField.getText());
            compileBean.setTempPath(tempTextField.getText());
            compileBean.setClearTempDir(clearTempDir.isSelected());

            menuControllerInterfaces.setCompileConfig(compileBean);
            dispose();
        } else if ("cancel".equals(command)) {

            dispose();
        } else if ("jdk_path".equals(command)) {

            // 选择jdk的文件目录
            File fileDir = GuiUtils.selectOpenFileDir(this, jdkTextField.getText());
            if (fileDir!= null) jdkTextField.setText(fileDir.getPath());
        } else if ("out_path".equals(command)) {

            // 选择输出的文件目录
            File fileDir = GuiUtils.selectOpenFileDir(this, outTextField.getText());
            if (fileDir!= null) outTextField.setText(fileDir.getPath());
        } else if ("temp_path".equals(command)) {

            // 选择临时的文件目录
            File fileDir = GuiUtils.selectOpenFileDir(this, tempTextField.getText());
            if (fileDir!= null) tempTextField.setText(fileDir.getPath());
        }
    }
}

