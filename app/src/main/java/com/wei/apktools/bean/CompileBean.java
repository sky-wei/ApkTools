package com.wei.apktools.bean;

/**
 * Created by starrysky on 15-3-16.
 */
public class CompileBean {

    private String jdkPath;
    private String outPath;
    private String tempPath;
    private boolean clearTempDir;

    public String getJdkPath() {
        return jdkPath;
    }

    public void setJdkPath(String jdkPath) {
        this.jdkPath = jdkPath;
    }

    public String getOutPath() {
        return outPath;
    }

    public void setOutPath(String outPath) {
        this.outPath = outPath;
    }

    public String getTempPath() {
        return tempPath;
    }

    public void setTempPath(String tempPath) {
        this.tempPath = tempPath;
    }

    public boolean isClearTempDir() {
        return clearTempDir;
    }

    public void setClearTempDir(boolean clearTempDir) {
        this.clearTempDir = clearTempDir;
    }
}
