package com.wei.apktools.core;

/**
 * Created by jingcai.wei on 3/22/2014.
 */
public interface Handle {

    /**
     * 用来处理指定的包文件,并返回需要回编译的文件
     * @param packageFile 传入的参数
     * @return 返回的结果
     */
    public PackageFile onHandle(PackageFile packageFile) throws HandleException;
}
