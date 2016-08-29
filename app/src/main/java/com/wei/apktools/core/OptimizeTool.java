package com.wei.apktools.core;

import com.wei.apktools.task.Execute;

import java.io.File;
import java.io.FileNotFoundException;

/**
 * Created by jingcai.wei on 3/20/2014.
 */
public class OptimizeTool {

    protected Execute execute;

    public OptimizeTool(Execute execute) {

        this.execute = execute;
    }

    public void optimize(File source, File target)
            throws FileNotFoundException, Execute.BrutException {

        if (source == null || target == null) {

            throw new NullPointerException("优化的文件不能为NULL!");
        }

        if (!source.isFile()) {

            throw new FileNotFoundException("源文件不存在!");
        }

        // 优化包的命令
        String command = "zipalign -vf 4 " + source.getPath() + " " + target.getPath();

        // 执行命令
        execute.insertionExec(command);
    }

    public Execute getExecute() {
        return execute;
    }
}
