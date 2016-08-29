package com.wei.apktools.model;

import com.wei.apktools.config.EnvProperty;
import com.wei.apktools.interfaces.MainModelInterfaces;
import com.wei.apktools.interfaces.OutInfoModelInterfaces;
import com.wei.apktools.utils.Log;
import org.apache.commons.io.FileUtils;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by starrysky on 15-3-16.
 */
public class OutInfoModel implements OutInfoModelInterfaces {

    SimpleDateFormat dateFormat = new SimpleDateFormat("yyMMddHHmmss");

    /** 用来临时保存上一次保存的路径 */
    private File lastDir;

    private EnvProperty envProperty;

    public OutInfoModel() {
        this.envProperty = EnvProperty.getInstance();
    }

    @Override
    public File getLastDir() {
        return lastDir;
    }

    @Override
    public int getBufferLine() {
        return envProperty.getInt(EnvProperty.OUT_BUFFER_LINE);
    }

    @Override
    public void setBufferLine(int line) {
        envProperty.putInt(EnvProperty.OUT_BUFFER_LINE, line);
        envProperty.saveEnvProperty();
    }

    @Override
    public boolean saveToDir(File dir, String content) {

        if (dir == null || content == null) return false;

        lastDir = dir;
        if (!dir.exists()) dir.mkdirs();

        try {
            // 保存的文件
            File saveFile = new File(dir, dateFormat.format(new Date()) + ".log");
            FileUtils.write(saveFile, content);
            return true;
        } catch (Exception e) {
            Log.e("保存输出信息失败", e);
        }
        return false;
    }
}
