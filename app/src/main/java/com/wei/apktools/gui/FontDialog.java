package com.wei.apktools.gui;

import com.wei.apktools.utils.Constant;
import com.wei.apktools.utils.ImageUtils;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

/**
 * Created by starrysky on 15-2-2.
 */
public class FontDialog extends JDialog implements ActionListener {

    private static final int WIDTH = 377;
    private static final int HEIGHT = 280;

    private Font curFont;
    private JList<String> fontNames;
    private JList<String> fontStyles;
    private JList<String> fontSizes;
    private JLabel showLabel;

    private boolean cancel;

    private JButton confirmButton;
    private JButton cancelButton;

    public FontDialog(JFrame frame, Font initialFont) {
        super(frame, true);

        cancel = true;

        initPanel();

        setDefaultContent(initialFont);

        GuiUtils.pack(frame, this, false);
    }

    private void initPanel() {

        setTitle("界面设置");
        setIconImage(ImageUtils.loadInsideImage(Constant.APP_ICON));

        setLayout(new BorderLayout());
        setContentPane(newContentPanel());
    }

    private JPanel newContentPanel() {

        JPanel contentPanel = new JPanel(null);
        GuiUtils.revisionSize(contentPanel, WIDTH, HEIGHT);

        contentPanel.add(newFontPanel());
        contentPanel.add(newAppendPanel());

        return contentPanel;
    }

    private JPanel newFontPanel() {

        String[] styles = { "正常", "粗体", "斜体", "粗体+斜体"};
        String[] sizes = new String[100];
        for (int i = 0; i < 100; i++) {
            sizes[i] = "" + (i + 1);
        }

        JPanel fontPanel = new JPanel(null);
        fontPanel.setBounds(12, 20, WIDTH - 24, 200);

        FontListListener fontListListener = new FontListListener();
        GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();

        fontNames = new JList<String>(ge.getAvailableFontFamilyNames());
        fontNames.setVisibleRowCount(7);
        fontNames.addListSelectionListener(fontListListener);

        fontStyles = new JList<String>(styles);
        fontStyles.setVisibleRowCount(7);
        fontStyles.addListSelectionListener(fontListListener);

        fontSizes = new JList<String>(sizes);
        fontSizes.setVisibleRowCount(7);
        fontSizes.addListSelectionListener(fontListListener);

        JLabel name = new JLabel( "名称");
        name.setBounds(10, 0, 41, 16);

        JLabel style = new JLabel( "样式");
        style.setBounds(170, 0, 41, 16);

        JLabel size = new JLabel( "大小");
        size.setBounds(270, 0, 41, 16);

        showLabel = new JLabel( "AaBbCcDdEeFfGgHhIiJjKkLlMm...XxYyZz", SwingConstants.CENTER);
        showLabel.setBounds(10, 165, 340, 49);

        JScrollPane fontNamesp = new JScrollPane(fontNames);
        fontNamesp.setBounds(10, 25, 150, 125);

        JScrollPane fontStylesp = new JScrollPane(fontStyles);
        fontStylesp.setBounds( 170, 25, 90, 125);

        JScrollPane fontSizesp = new JScrollPane(fontSizes);
        fontSizesp.setBounds( 270, 25, 70, 125);

        fontPanel.add(name);
        fontPanel.add(style);
        fontPanel.add(size);
        fontPanel.add(showLabel);
        fontPanel.add(fontNamesp);
        fontPanel.add(fontStylesp);
        fontPanel.add(fontSizesp);

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

    private void setDefaultContent(Font initialFont) {

        curFont = initialFont == null ? showLabel.getFont() : initialFont;

        String fontName = curFont.getName();
        int fontStyle = curFont.getStyle();
        int fontSize = curFont.getSize();

        fontNames.setSelectedValue(fontName, true);
        fontSizes.setSelectedValue("" + fontSize, true);

        if (((fontStyle & Font.BOLD) != 0) && ((fontStyle & Font.ITALIC) != 0) ) {
            fontStyles.setSelectedValue( "粗体+斜体", true);
        } else if ( (fontStyle & Font.BOLD) != 0  ) {
            fontStyles.setSelectedValue( "粗体", true);
        } else if ( (fontStyle & Font.ITALIC) != 0 ) {
            fontStyles.setSelectedValue( "斜体", true);
        } else {
            fontStyles.setSelectedValue( "正常", true);
        }
    }

    private void refresh() {
        showLabel.setFont(curFont);
    }

    public Font getSelectFont() {
        return curFont;
    }

    public boolean isCancel() {
        return cancel;
    }

    @Override
    public void actionPerformed(ActionEvent e) {

        String action = e.getActionCommand();

        if ("confirm".equals(action)) {
            cancel = false;
        } else if ("cancel".equals(action)) {
            cancel = true;
        }
        dispose();
    }

    private class FontListListener implements ListSelectionListener {

        @Override
        public void valueChanged(ListSelectionEvent e) {

            String fontName = curFont.getName();
            int fontStyle = curFont.getStyle();
            int fontSize = curFont.getSize();

            String selectName = fontNames.getSelectedValue();
            if (selectName != null) {
                fontName = selectName;
            }

            String selectSize = fontSizes.getSelectedValue();
            if (selectSize != null) {
                fontSize = Integer.parseInt(selectSize);
            }

            String selectStyle = fontStyles.getSelectedValue();
            if (selectStyle != null) {
                fontStyle = 0;

                if (selectStyle.indexOf("正常") != -1) {
                    fontStyle = Font.PLAIN;
                } else {
                    if (selectStyle.indexOf("斜体") != -1) {
                        fontStyle = Font.ITALIC;
                    }
                    if (selectStyle.indexOf("粗体") != -1) {
                        fontStyle |= Font.BOLD;
                    }
                }
            }

            curFont = new Font(fontName, fontStyle, fontSize);
            refresh();
        }
    }
}
