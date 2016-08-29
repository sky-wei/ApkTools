package com.wei.apktools.core;

import com.wei.apktools.task.Execute;
import com.wei.apktools.utils.StringUtils;
import com.wei.apktools.utils.ZipUtils;
import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.zip.ZipEntry;

/**
 * Created by jingcai.wei on 3/17/14.
 */
public class SignedTool {

    protected Execute execute;

    public SignedTool(Execute execute) {
        this.execute = execute;
    }

    /**
     * 给指定的文件进行签名
     * @param property 签名证书信息
     * @param source 需要签名的文件路径
     * @param target 保存已经签名的文件路径
     * @throws java.io.FileNotFoundException
     * @throws Execute.BrutException
     */
    public void signed(SignedProperty property, File source, File target)
            throws IOException, Execute.BrutException {

        if (property == null || target == null) {
            throw new NullPointerException();
        }

        File tempFile = null;

        try {
            // 生成没有签名的临时文件
            tempFile = buildUnSignedFile(source);

            // 签名文件命令
            StringBuilder command = new StringBuilder();

            command.append("jarsigner -verbose");
            command.append(" -keystore ").append(property.getFile().getPath());
            command.append(" -signedjar ").append(target.getPath());
            command.append(" ").append(tempFile.getPath());
            command.append(" ").append(property.getSignedName());
            command.append(" -storepass ").append(property.getKeystorePassword());
            command.append(" -keypass ").append(property.getSignedPassword());
            command.append(" -digestalg SHA1 -sigalg MD5withRSA -sigfile CERT");

            // 执行命令
            execute.insertionExec(command.toString());
        } finally {
            // 清除临时文件
            if (tempFile != null) FileUtils.deleteQuietly(tempFile);
        }
    }

    private File buildUnSignedFile(File source) throws IOException {

        if (source == null) {
            throw new NullPointerException();
        }

        if (!source.isFile()) {
            throw new FileNotFoundException("源文件不存在!");
        }

        File tempFile = new File(source.getParentFile(), StringUtils.dateToString() + ".temp");

        ZipUtils.copyFile(source, tempFile, new ZipUtils.ZipEntryFilter() {
            @Override
            public boolean accept(ZipEntry entry) {
                if (entry.getName().startsWith("META-INF/")) {
                    return false;
                }
                return true;
            }
        });
        return tempFile;
    }

    /**
     * 生成相应的签名证书文件(签名证书文件路径在签名信息类中)
     * @param property 签名证书信息
     * @throws Execute.BrutException
     */
    public void buildSigned(SignedProperty property) throws Execute.BrutException {

        if (property == null) {
            throw new NullPointerException();
        }

        // 签名文件命令
        StringBuilder command = new StringBuilder();

        command.append("keytool -genkey -dname");
        command.append(" cn=").append(property.getName());
        command.append(",ou=").append(property.getOrganization());
        command.append(",o=").append(property.getOrganization());
        command.append(",l=").append(property.getCity());
        command.append(",st=").append(property.getProvince());
        command.append(",c=").append(property.getCode());
        command.append(" -alias ").append(property.getSignedName());
        command.append(" -keypass ").append(property.getSignedPassword());
        command.append(" -keystore ").append(property.getFile().getPath());
        command.append(" -keyalg RSA -sigalg MD5withRSA");
        command.append(" -storepass ").append(property.getKeystorePassword());
        command.append(" -validity ").append(property.getTerm());

        // 执行命令
        execute.insertionExec(command.toString());
    }


    /**
     * 用来验证指定的文件的签名信息
     * @param source 被签名的文件
     * @throws java.io.FileNotFoundException
     * @throws Execute.BrutException
     */
    public void verifySigned(File source)
            throws FileNotFoundException, Execute.BrutException {

        if (source == null) {
            throw new NullPointerException();
        }

        if (!source.isFile()) {
            throw new FileNotFoundException("源文件不存在!");
        }

        // 验证签名的命令
        String command = "jarsigner -verify -verbose -certs " + source.getPath();

        // 执行命令
        execute.insertionExec(command);
    }

    public Execute getExecute() {
        return execute;
    }
}
