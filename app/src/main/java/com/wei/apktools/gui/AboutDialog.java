package com.wei.apktools.gui;

import com.wei.apktools.utils.Constant;
import com.wei.apktools.utils.ImageUtils;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Created by starrysky on 15-1-27.
 */
public class AboutDialog extends JDialog {

    private static final int WIDTH = 400;
    private static final int HEIGHT = 280;

    public AboutDialog(JFrame frame) {
        super(frame, true);

        setTitle("关于本程序");
        setIconImage(ImageUtils.loadInsideImage(Constant.APP_ICON));

        setLayout(new BorderLayout());
        setContentPane(newContentPanel());

        GuiUtils.pack(frame, this, false);
    }

    private JPanel newContentPanel() {

        JPanel contentPanel = new JPanel(null);
        GuiUtils.revisionSize(contentPanel, WIDTH, HEIGHT);

        Icon icon = new ImageIcon(ImageUtils.loadInsideImage("/image/love.png"));
        JLabel lable = new JLabel(icon);
        lable.setBounds(30, 2, 126, 166);
        contentPanel.add(lable);

        lable = new JLabel("有志者,事竟成,");
        lable.setBounds(200, 20, 200, 30);
        contentPanel.add(lable);

        lable = new JLabel("破斧沉舟,百日秦关终属楚.");
        lable.setBounds(200, 40, 200, 30);
        contentPanel.add(lable);

        lable = new JLabel("苦心人,天不负,");
        lable.setBounds(200, 65, 200, 30);
        contentPanel.add(lable);

        lable = new JLabel("卧薪尝胆,三千越甲可吞吴.");
        lable.setBounds(200, 85, 200, 30);
        contentPanel.add(lable);

        lable = new JLabel("微笑的人生更精彩...");
        lable.setBounds(200, 110, 120, 30);
        contentPanel.add(lable);

        lable = new JLabel("-- starrysky");
        lable.setBounds(280, 130, 100, 30);
        contentPanel.add(lable);

        JSeparator separator = new JSeparator(JSeparator.HORIZONTAL);
        separator.setBounds(30, 170, 340, 2);
        contentPanel.add(separator);

        lable = new JLabel("版本: " + Constant.VERSION_NAME);
        lable.setBounds(30, 180, 200, 30);
        contentPanel.add(lable);

        lable = new JLabel("作者: starrysky");
        lable.setBounds(30, 205, 200, 30);
        contentPanel.add(lable);

        lable = new JLabel("邮箱: jingcai.wei@163.com");
        lable.setBounds(30, 230, 200, 30);
        contentPanel.add(lable);

        JButton confirm = new JButton("确定");
        confirm.setBounds(305, 230, 65, 25);
        confirm.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent arg0) {
                AboutDialog.this.dispose();
            }
        });
        contentPanel.add(confirm);

        return contentPanel;
    }
}
