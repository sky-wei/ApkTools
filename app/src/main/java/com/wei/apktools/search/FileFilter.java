package com.wei.apktools.search;

import java.io.File;

/**
 * 文件过滤接口
 *
 * Created by jingcai.wei on 3/14/14.
 */
public interface FileFilter {

    /**
     * 使用判断指定的文件或目录是否可用
     * @param file 过滤的文件
     * @return true:文件可用,false:文件被过滤
     */
    public boolean accept(File file);
}
