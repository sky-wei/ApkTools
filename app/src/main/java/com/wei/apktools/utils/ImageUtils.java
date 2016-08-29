package com.wei.apktools.utils;

import org.apache.commons.io.IOUtils;

import javax.imageio.ImageIO;
import java.awt.*;
import java.io.File;
import java.io.InputStream;

/**
 * Created by starrysky on 15-1-17.
 */
public class ImageUtils {

    /**
     * 获取指定文件的图片
     * @param file 图片路径
     * @return Image
     */
    public static Image loadImage(File file) {

        if (file == null || !file.exists()) {
            return null;
        }

        try {
            return ImageIO.read(file);
        } catch (Exception e) {
            Log.e("加载图片异常", e);
        }
        return null;
    }

    /**
     * 获取指定文件的图片
     * @param file 图片路径
     * @return Image
     */
    public static Image loadImage(String file) {

        if (file == null) return null;

        return loadImage(new File(file));
    }

    /**
     * 获取内部图片
     * @param name
     * @return
     */
    public static Image loadInsideImage(String name) {

        InputStream is = null;

        try {
            is = ResUtils.getResourceAsStream(name);
            return ImageIO.read(is);
        } catch (Exception e) {
            Log.e("加载内部图片错误", e);
        } finally {
            IOUtils.closeQuietly(is);
        }
        return null;
    }
}
