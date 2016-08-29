package com.wei.apktools.controller;

import com.wei.apktools.gui.GuiUtils;
import com.wei.apktools.interfaces.OutInfoControllerInterfaces;
import com.wei.apktools.interfaces.OutInfoModelInterfaces;
import com.wei.apktools.model.OutInfoModel;

import javax.swing.*;
import java.io.File;

/**
 * Created by starrysky on 15-3-16.
 */
public class OutInfoController implements OutInfoControllerInterfaces {

    private JFrame frame;
    private OutInfoModelInterfaces outInfoModelInterfaces;

    public OutInfoController(JFrame frame, OutInfoModelInterfaces outInfoModelInterfaces) {
        this.frame = frame;
        this.outInfoModelInterfaces = outInfoModelInterfaces;
    }

    @Override
    public void setBufferLine(int line) {
        outInfoModelInterfaces.setBufferLine(line);
    }

    @Override
    public void saveContent(String content) {

        if (content == null || content.length() <= 0) {
            // 没有内容需要保存
            JOptionPane.showMessageDialog(frame,
                    "没有内容，无法保存至文件。", "警告", JOptionPane.WARNING_MESSAGE);
            return ;
        }

        // 保存输出信息到目录下
        File lastDir = outInfoModelInterfaces.getLastDir();
        File saveDir = GuiUtils.selectSaveFileDir(frame, lastDir == null ? null : lastDir.getPath());

        if (saveDir == null) return ;

        if (outInfoModelInterfaces.saveToDir(saveDir, content)) {
            // 保存信息成功
            JOptionPane.showMessageDialog(frame,
                    "保存信息成功。", "提示", JOptionPane.INFORMATION_MESSAGE);
            return ;
        }

        // 保存信息失败
        JOptionPane.showMessageDialog(frame,
                "保存信息失败，错误详情请查看日志文件信息。", "错误", JOptionPane.ERROR_MESSAGE);
    }
}
