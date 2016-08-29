package com.wei.apktools.interfaces;

import java.io.File;

/**
 * Created by starrysky on 15-3-16.
 */
public interface OutInfoModelInterfaces {

    /**
     * 获取最后一次打开的目录
     * @return
     */
    File getLastDir();

    /**
     * 获取缓冲的行数
     * @return
     */
    int getBufferLine();

    /**
     * 设置缓冲的行数
     * @param line
     */
    void setBufferLine(int line);

    /**
     * 保存内容到指定的目录下，文件名为当前时间格式
     * @param content
     */
    boolean saveToDir(File dir, String content);
}
