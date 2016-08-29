package com.wei.apktools.gui;

import com.wei.apktools.config.EnvProperty;
import com.wei.apktools.interfaces.MainModelInterfaces;
import com.wei.apktools.interfaces.OutInfoControllerInterfaces;
import com.wei.apktools.interfaces.OutInfoModelInterfaces;
import com.wei.apktools.interfaces.OutputInfo;
import com.wei.apktools.utils.Log;
import org.apache.commons.io.FileUtils;

import javax.swing.*;
import javax.swing.text.BadLocationException;
import javax.swing.text.Document;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by starrysky on 15-2-4.
 */
public class OutInfoPanel extends JPanel implements OutputInfo, ItemListener, ActionListener {

    private OutInfoControllerInterfaces outInfoControllerInterfaces;
    private OutInfoModelInterfaces outInfoModelInterfaces;

    private JTextArea textArea;
    private JComboBox<String> bufferComboBox;

    // 这个暂时不动
    private int bufferLine;
    private int startOffset;

    public OutInfoPanel(OutInfoControllerInterfaces outInfoControllerInterfaces, OutInfoModelInterfaces outInfoModelInterfaces) {
        this.outInfoControllerInterfaces = outInfoControllerInterfaces;
        this.outInfoModelInterfaces = outInfoModelInterfaces;

        initPanel();
        setPanel();
    }

    private void initPanel() {

        setLayout(new BorderLayout());

        add(newToolPanel(), BorderLayout.NORTH);
        add(newOutputPanel(), BorderLayout.CENTER);
    }

    private void setPanel() {

        int line = outInfoModelInterfaces.getBufferLine();

        if (line == 0) {
            bufferLine = Integer.parseInt(bufferComboBox.getItemAt(0));
            startOffset = (int)(bufferLine * 0.6f);
        } else {
            bufferLine = line;
            startOffset = (int)(bufferLine * 0.6f);
            bufferComboBox.setSelectedItem(Integer.toString(line));
        }
    }

    private JPanel newToolPanel() {

        JPanel toolPanel = new JPanel(new BorderLayout());
        toolPanel.setBorder(BorderFactory.createEmptyBorder(4, 2, 2, 2));

        String[] values = {"400", "600", "800", "1000", "1200", "1400", "1600"};
        bufferComboBox = new JComboBox<>(values);
        bufferComboBox.addItemListener(this);

        JToolBar toolBar = new JToolBar();
        toolBar.setFloatable(false);
        toolBar.add(new JLabel("缓冲行数: "));
        toolBar.add(bufferComboBox);
        toolBar.addSeparator();
        toolBar.add(GuiUtils.newButton("置顶", "top", this));
        toolBar.add(GuiUtils.newButton("置尾", "tail", this));
        toolBar.addSeparator();
        toolBar.add(GuiUtils.newButton("另存为", "save", this));
        toolBar.addSeparator();
        toolBar.add(GuiUtils.newButton("清空", "clear", this));

        toolPanel.add(toolBar, BorderLayout.EAST);

        return toolPanel;
    }

    private JScrollPane newOutputPanel() {

        textArea = new JTextArea();

        textArea.setEditable(false);
        textArea.setBackground(getBackground());
        textArea.setForeground(getForeground());

        JScrollPane scrollPane = new JScrollPane(textArea);
        scrollPane.setBorder(BorderFactory.createEmptyBorder(0, 2, 2, 2));

        return scrollPane;
    }

    @Override
    public void onOutput(String info) {
        onOutput(info, null);
    }

    @Override
    public void onOutput(String info, Throwable tr) {
        append(info + "\n");

        if (tr != null) {
            append(tr.toString() + "\n");

            StackTraceElement[] trace = tr.getStackTrace();
            for (StackTraceElement traceElement : trace) {
                append("\tat " + traceElement + "\n");
            }
        }
    }

    private synchronized void append(String info) {

        if (info == null) return ;

        textArea.append(info);
        textArea.setCaretPosition(textArea.getDocument().getLength());

        int lineCount = textArea.getLineCount();

        if (lineCount > bufferLine) {
            try {
                int startOff = textArea.getLineStartOffset(startOffset);
                int endOff = textArea.getLineEndOffset(lineCount - 1);
                int length = endOff - startOff;
                if (length > 0) {
                    String newText = textArea.getText(startOff, length);
                    textArea.setText(newText);
                }
            } catch (BadLocationException e) {
                Log.e("BadLocationException!", e);
                textArea.setText("BadLocationException: " + e.getMessage());
            }
        }
    }

    @Override
    public void itemStateChanged(ItemEvent e) {

        if (ItemEvent.SELECTED == e.getStateChange()) {

            String value = bufferComboBox.getItemAt(bufferComboBox.getSelectedIndex());

            bufferLine = Integer.parseInt(value);
            startOffset = (int)(bufferLine * 0.6f);

            // 设置属性信息
            outInfoControllerInterfaces.setBufferLine(bufferLine);
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {

        String command = e.getActionCommand();

        if ("top".equals(command)) {
            // 置顶
            textArea.setCaretPosition(0);
        } else if ("tail".equals(command)) {
            // 置尾
            textArea.setCaretPosition(textArea.getDocument().getLength());
        } else if ("save".equals(command)) {
            // 保存
            outInfoControllerInterfaces.saveContent(textArea.getText());
        } else if ("clear".equals(command)) {
            // 清除
            textArea.setText("");
        }
    }
}
