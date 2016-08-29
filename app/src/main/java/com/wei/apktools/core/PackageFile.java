package com.wei.apktools.core;

import com.wei.apktools.search.FileFilter;
import com.wei.apktools.search.FileSearch;
import com.wei.apktools.utils.DocumentUtils;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;

import java.io.File;
import java.util.List;

/**
 * Created by jingcai.wei on 3/18/14.
 */
public class PackageFile {

    protected File fileDir;

    protected File manifest;
    protected File assetsDir;
    protected File smaliDir;
    protected File resDir;

    protected PackageInfo packageInfo;

    public PackageFile(File fileDir) throws HandleException {

        this.fileDir = fileDir;

        manifest = new File(fileDir, "AndroidManifest.xml");
        assetsDir = new File(fileDir, "assets");
        smaliDir = new File(fileDir, "smali");
        resDir = new File(fileDir, "res");

        analyzePackage();
    }

    private void analyzePackage() throws HandleException {

        try {
            Document document = DocumentUtils.getDocument(manifest);
            Element root = document.getRootElement();

            packageInfo = new PackageInfo();

            packageInfo.setPackageName(root.attributeValue("package"));
            packageInfo.setVersionName(root.attributeValue("versionName"));
            packageInfo.setVersionCode(Integer.parseInt(root.attributeValue("versionCode", "0")));
        } catch (DocumentException e) {
            throw new HandleException("解析包信息出错了!");
        }
    }

    public File getFileDir() {
        return fileDir;
    }

    public File getManifest() {
        return manifest;
    }

    public File getAssetsDir() {
        return assetsDir;
    }

    public File getSmaliDir() {
        return smaliDir;
    }

    public File getResDir() {
        return resDir;
    }

    public List<File> list() {

        return list(null);
    }

    public List<File> list(FileFilter filter) {

        FileSearch fileSearch = new FileSearch(filter);

        return fileSearch.search(fileDir);
    }

    public PackageInfo getPackageInfo() {
        return packageInfo;
    }

    public class PackageInfo {

        protected String packageName;
        protected String versionName;
        protected int versionCode;

        public String getVersionName() {
            return versionName;
        }

        public void setVersionName(String versionName) {
            this.versionName = versionName;
        }

        public int getVersionCode() {
            return versionCode;
        }

        public void setVersionCode(int versionCode) {
            this.versionCode = versionCode;
        }

        public String getPackageName() {
            return packageName;
        }

        public void setPackageName(String packageName) {
            this.packageName = packageName;
        }
    }
}
