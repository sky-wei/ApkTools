package com.wei.apktools.gui;

import com.nilo.plaf.nimrod.NimRODLookAndFeel;
import com.nilo.plaf.nimrod.NimRODTheme;
import com.wei.apktools.utils.Log;

import javax.swing.*;
import javax.swing.filechooser.FileFilter;
import java.awt.*;
import java.awt.event.ActionListener;
import java.io.File;

public class GuiUtils {

	public static void revisionLocation(Window window) {

		Dimension dimension = Toolkit.getDefaultToolkit().getScreenSize();

		revisionLocation(window, dimension.width, dimension.height);
	}

	public static void revisionLocation(Window window, int screenWidth, int screenHeight) {

		if (window == null) return ;

		int width = window.getWidth();
		int height = window.getHeight();

		// 显示的居中位置
		Point location = new Point((screenWidth - width) >> 1, (screenHeight - height) >> 1);

		if (location.x < 0 || location.x > screenWidth) location.x = 0;
		if (location.y < 0 || location.y > screenHeight) location.y = 0;

		/* 设置提示框显示位置 */
		window.setLocation(location);
	}

	public static void revisionLocation(Window parent, Window child) {

		Dimension dimension = Toolkit.getDefaultToolkit().getScreenSize();

		revisionLocation(parent, child, dimension.width, dimension.height);
	}

	public static void revisionLocation(Window parent, Window child, int screenWidth, int screenHeight) {

		if (parent == null || child == null) return ;

		int x = parent.getX();
		int y = parent.getY();
		int w = parent.getWidth();
		int h = parent.getHeight();

		int width = child.getWidth();
		int height = child.getHeight();

		Point location = new Point(x + ((w - width) >> 1), y + ((h - height) >> 1));

		Dimension dimension = Toolkit.getDefaultToolkit().getScreenSize();

		if (location.x > dimension.width || location.x < 0
				|| location.y > dimension.height || location.y < 0) {

			// 超出屏幕判断处理
			location.setLocation(x, y);
		}

		/* 设置窗口显示位置 */
		child.setLocation(location);
	}

	public static void revisionSize(JComponent component, int width, int height) {

		if (component == null) return ;

		component.setPreferredSize(new Dimension(width, height));
	}

	public static void pack(JFrame frame, boolean resizable) {

		Dimension dimension = Toolkit.getDefaultToolkit().getScreenSize();

		pack(frame, dimension.width, dimension.height, resizable);
	}

	public static void pack(JFrame frame, int screenWidth, int screenHeight, boolean resizable) {

		if (frame != null) {
			frame.setResizable(resizable);
			frame.pack();
		}

		revisionLocation(frame, screenWidth, screenHeight);
	}

	public static void pack(Window parent, Dialog child, boolean resizable) {

		Dimension dimension = Toolkit.getDefaultToolkit().getScreenSize();

		pack(parent, child, dimension.width, dimension.height, resizable);
	}

	public static void pack(Window parent, Dialog child, int screenWidth, int screenHeight, boolean resizable) {

		if (child != null) {
			child.setResizable(resizable);
			child.pack();
		}

		revisionLocation(parent, child, screenWidth, screenHeight);
	}

	/**
	 * 选择文件并返回
	 * @param component
	 * @param defaultPath
	 * @return
	 */
	public static File selectOpenFile(Component component, String defaultPath) {
		return selectOpenFile(component, defaultPath, null);
	}

	/**
	 * 选择文件并返回
	 * @param component
	 * @param defaultPath
	 * @return
	 */
	public static File selectOpenFile(Component component, String defaultPath, FileFilter fileFilter) {

		if (component == null) return null;

		JFileChooser jFileChooser = new JFileChooser(defaultPath);
		jFileChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
		jFileChooser.setMultiSelectionEnabled(false);
		if (fileFilter != null) jFileChooser.setFileFilter(fileFilter);

		int returnVal = jFileChooser.showOpenDialog(component);

		if (returnVal == JFileChooser.APPROVE_OPTION) {

			File file = jFileChooser.getSelectedFile();

			if (file != null && file.exists()) return file;
		}

		return null;
	}

	/**
	 * 选择文件并返回
	 * @param component
	 * @param defaultPath
	 * @return
	 */
	public static File[] selectOpenFiles(Component component, String defaultPath, FileFilter fileFilter) {

		if (component == null) return null;

		JFileChooser jFileChooser = new JFileChooser(defaultPath);
		jFileChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
		jFileChooser.setMultiSelectionEnabled(true);
		if (fileFilter != null) jFileChooser.setFileFilter(fileFilter);

		int returnVal = jFileChooser.showOpenDialog(component);

		if (returnVal == JFileChooser.APPROVE_OPTION) {

			return jFileChooser.getSelectedFiles();
		}

		return null;
	}

	/**
	 * 选择文件目录并返回
	 * @param component
	 * @param defaultPath
	 * @return
	 */
	public static File selectOpenFileDir(Component component, String defaultPath) {

		if (component == null) return null;

		JFileChooser jFileChooser = new JFileChooser(defaultPath);
		jFileChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
		jFileChooser.setMultiSelectionEnabled(false);

		int returnVal = jFileChooser.showOpenDialog(component);

		if (returnVal == JFileChooser.APPROVE_OPTION) {

			File fileDir = jFileChooser.getSelectedFile();

			if (fileDir != null && fileDir.exists()) return fileDir;
		}

		return null;
	}

	/**
	 * 选择文件目录并返回
	 * @param component
	 * @param defaultPath
	 * @return
	 */
	public static File selectSaveFileDir(Component component, String defaultPath) {

		if (component == null) return null;

		JFileChooser jFileChooser = new JFileChooser(defaultPath);
		jFileChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
		jFileChooser.setMultiSelectionEnabled(false);

		int returnVal = jFileChooser.showSaveDialog(component);

		if (returnVal == JFileChooser.APPROVE_OPTION) {

			File fileDir = jFileChooser.getSelectedFile();

			if (fileDir != null && fileDir.exists()) return fileDir;
		}

		return null;
	}

	public static Color selectColor(Component component, String title, Color initialColor) {

		Color result = JColorChooser.showDialog(component, title, initialColor);

		if (result != null) {
			return result;
		}

		return initialColor;
	}

	public static JButton newButton(String text, String actionCommand, ActionListener actionListener) {
		return newButton(text, actionCommand, actionListener, null);
	}

	public static JButton newButton(String text, String actionCommand, ActionListener actionListener, String tipText) {

		JButton button = new JButton(text);
		button.setActionCommand(actionCommand);
		button.addActionListener(actionListener);
		button.setToolTipText(tipText);

		return button;
	}

	public static JMenuItem newMenuItem(String text, String actionCommand, ActionListener actionListener) {

		JMenuItem menuItem = new JMenuItem(text);
		menuItem.setActionCommand(actionCommand);
		menuItem.addActionListener(actionListener);

		return menuItem;
	}
}
