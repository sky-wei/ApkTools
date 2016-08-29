package com.wei.apktools.core;

import com.wei.apktools.task.Execute;

import java.io.File;
import java.io.FileNotFoundException;

/**
 * Created by jingcai.wei on 3/17/14.
 */
public class ApkTool {

    private Execute execute;

    public ApkTool(Execute execute) {

        this.execute = execute;
    }

    /**
     * 反编译指定的源文件,并把编译的文件保存到指定的文件目录下
     * @param source 源文件路径
     * @param target 保存的文件目录
     * @throws java.io.FileNotFoundException
     * @throws Execute.BrutException
     */
    public void decompile(File source, File target)
            throws FileNotFoundException, Execute.BrutException {

        if (source == null || target == null) {

            throw new NullPointerException();
        }

        if (!source.isFile()) {

            throw new FileNotFoundException("源文件不存在!");
        }

        // 反编译命令
        String command = "java -jar apktool.jar d " + source.getPath() + " " + target.getPath();

        // 执行命令
        execute.insertionExec(command);
    }


    /**
     * 回编译指定目录下被反编译过的文件
     * @param source 源文件目录
     * @throws java.io.FileNotFoundException
     * @throws Execute.BrutException
     */
    public void compile(File source)
            throws FileNotFoundException, Execute.BrutException {

        if (source == null) {

            throw new NullPointerException();
        }

        if (!source.isDirectory()) {

            throw new FileNotFoundException("源文件目录不存在!");
        }

        // 回编译命令
        String command = "java -jar apktool.jar b " + source.getPath();

        // 执行命令
        execute.insertionExec(command);
    }

    public Execute getExecute() {
        return execute;
    }
}
