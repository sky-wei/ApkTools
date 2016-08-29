package com.wei.apktools.gui;

import com.wei.apktools.utils.Constant;
import com.wei.apktools.utils.ImageUtils;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Random;

/**
 * Created by starrysky on 15-2-2.
 */
public class ImageDialog extends JDialog {

    private static final int WIDTH = 560;
    private static final int HEIGHT = 760;

    private static final String[] IMAGES = {"/image/love_0.jpg", "/image/love_1.jpg",
            "/image/love_2.jpg", "/image/love_3.jpg", "/image/love_4.jpg"};

    private int curIndex = 0;
    private JLabel imageLabel;

    public ImageDialog(JFrame frame) {
        super(frame, true);

        setTitle("亲爱的照片");
        setIconImage(ImageUtils.loadInsideImage(Constant.APP_ICON));

        setLayout(new BorderLayout());
        setContentPane(newContentPanel());

        GuiUtils.pack(frame, this, true);
    }

    private JPanel newContentPanel() {

        JPanel contentPanel = new JPanel(new BorderLayout());
        GuiUtils.revisionSize(contentPanel, WIDTH, HEIGHT);

        imageLabel = new JLabel(nextIcon());
        imageLabel.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);

                imageLabel.setIcon(nextIcon());
            }
        });

        contentPanel.add(imageLabel);

        return contentPanel;
    }

    private Icon nextIcon() {

        if (curIndex >= IMAGES.length) curIndex = 0;

        return new ImageIcon(ImageUtils.loadInsideImage(IMAGES[curIndex++]));
    }
}
