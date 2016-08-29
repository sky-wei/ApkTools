package com.wei.apktools.config;

import com.nilo.plaf.nimrod.NimRODLookAndFeel;
import com.nilo.plaf.nimrod.NimRODTheme;
import com.wei.apktools.gui.GuiUtils;
import com.wei.apktools.utils.Log;
import org.apache.commons.io.FileUtils;

import javax.swing.*;
import java.io.*;
import java.util.Properties;

/**
 * Created by starrysky on 15-1-17.
 */
public class Theme {

    /**
     * 加载本地的界面主题属性
     * @param theme
     * @return
     */
    public ThemeProperty loadThemeProperty(String theme) {

        InputStream is = null;

        try {
            Properties properties = new Properties();

            is = new FileInputStream(theme);
            properties.load(is);

            ThemeProperty themeProperty = new ThemeProperty();
            themeProperty.setProperties(properties);

            return themeProperty;
        } catch (Exception e) {
            Log.e("读取 " + theme + " 出错了", e);
        } finally {
            try {
                if (is != null) is.close();
            } catch (Exception e) {
            }
        }

        return null;
    }

    /**
     * 保存界面主题属性到指定的路径
     * @param themeProperty
     * @param theme
     */
    public void saveThemeProperty(ThemeProperty themeProperty, String theme) {

        if (themeProperty == null || theme == null) return ;

        OutputStream out = null;

        try {
            File themeFile = new File(theme);
            File parentFile = themeFile.getParentFile();

            if (!parentFile.exists()) parentFile.mkdirs();

            out = new FileOutputStream(theme);
            themeProperty.getProperties().store(out, "Tools Theme - starrysky");
        } catch (Exception e) {
            Log.e("保存 " + theme + " 出错了..." , e);
        } finally {
            try {
                if (out != null) out.close();
            } catch (Exception e) {
            }
        }
    }

    /**
     * 对指定的JFrame设置相应的主题
     * @param frame
     * @param theme
     * @param updateUI
     */
    public void setTheme(JFrame frame, String theme, boolean updateUI) {

        File themeFile = new File(theme);

        if (!themeFile.exists()) {
            saveThemeProperty(new ThemeProperty(), theme);
        }

        setGuiTheme(theme);

        if (updateUI) {
            SwingUtilities.updateComponentTreeUI(frame);
        }
    }

    /**
     * 设置当前程序的界面主题
     * @param theme 主题文件
     */
    public void setGuiTheme(String theme) {

        try {
            NimRODLookAndFeel nimbusLookAndFeel = new NimRODLookAndFeel();
            NimRODTheme nimRODTheme = new NimRODTheme(theme);

            NimRODLookAndFeel.setCurrentTheme(nimRODTheme);
            UIManager.setLookAndFeel(nimbusLookAndFeel);
//			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e) {
            Log.e("Set Theme Exception!", e);
        }
    }
}
