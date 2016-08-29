package com.wei.apktools.interfaces;

import java.io.File;

/**
 * Created by starrysky on 15-3-16.
 */
public interface OutInfoControllerInterfaces {

    /**
     * 设置缓冲的行数
     * @param line
     */
    void setBufferLine(int line);

    /**
     * 保存内容，文件名为当前时间格式
     * @param content
     */
    void saveContent(String content);
}
