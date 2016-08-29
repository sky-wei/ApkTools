package com.wei.apktools.core;

import com.wei.apktools.task.Execute;
import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

/**
 * Created by jingcai.wei on 3/22/2014.
 */
public class Integration {

    protected File workDir;
    protected Execute execute;

    protected File completeDir;
    protected File tempDir;

    protected ApkTool apkTool;
    protected SignedTool signedTool;
    protected OptimizeTool optimizeTool;

    protected SignedProperty signedProperty;

    public Integration(File workDir, Execute execute) {

        this.workDir = workDir;
        this.execute = execute;

        initIntegration();
    }

    public void initIntegration() {

        // 初始化工作目录
        tempDir = new File(workDir, "Temp");
        completeDir = new File(workDir, "Complete");

        // 对Apk反编译与回编译工作
        apkTool = new ApkTool(execute);

        // 对Apk签名与生成签名证书工具
        signedTool = new SignedTool(execute);

        // 对Apk优化的工具
        optimizeTool = new OptimizeTool(execute);

        // 创建目录 -
        if (!tempDir.isDirectory()) tempDir.mkdirs();
        if (!completeDir.isDirectory()) completeDir.mkdirs();
    }

    public SignedProperty getSignedProperty() {
        return signedProperty;
    }

    public void setSignedProperty(SignedProperty signedProperty) {
        this.signedProperty = signedProperty;
    }

    /**
     * 处理指定的APK包,当前方法集成反编译回编译签名与优化功能<br/>
     * 而中间的对包的操作全部由实现Handle接口的对象来处理
     * @param source 处理的源文件
     * @param handle 对包进行处理的对象
     * @return 最终完成的包的路径
     * @throws java.io.FileNotFoundException
     * @throws Execute.BrutException
     * @throws HandleException
     */
    public File handlePackage(File source, Handle handle)
            throws Execute.BrutException, HandleException, IOException {

        // 反编译返回的文件目录
        File defile = decompileSource(source);

        // 需要处理的包
        PackageFile handlePackage = new PackageFile(defile);

        // 进行处理
        PackageFile returnPackage = handle.onHandle(handlePackage);

        // 回编译需要的包
        File cofile = compileSource(returnPackage);

        // 签名回编译的文件
        File signedfile = signedSource(cofile);

        // 对文件进行优化
        File complete = optimizeSource(signedfile, returnPackage.getPackageInfo());

        // 删除临时目录
        FileUtils.deleteQuietly(defile);

        return complete;

    }

    /**
     * 反编译源文件,并返回反编译后的临时文件目录
     * @param source 源文件路径
     * @return 反编译后的临时文件目录
     * @throws java.io.FileNotFoundException
     * @throws Execute.BrutException
     */
    public File decompileSource(File source)
            throws FileNotFoundException, Execute.BrutException {

        // 反编译的文件目录
//        File target = new File(tempDir, FileUtils.getFileNameWithoutExtension(source.getPath()));
        File target = null;

                // 清除之前的文件
        FileUtils.deleteQuietly(target);

        // 反编译源文件
        apkTool.decompile(source, target);

        return target;
    }

    /**
     * 回编译指定的包文件,并返回回编译好的文件路径
     * @param packageFile 需要回编译的包
     * @return 回编译好的文件路径
     * @throws java.io.FileNotFoundException
     * @throws Execute.BrutException
     */
    public File compileSource(PackageFile packageFile)
            throws FileNotFoundException, Execute.BrutException {

        apkTool.compile(packageFile.getFileDir());

        // 生成的APK目录
        File distDir = new File(packageFile.getFileDir(), "dist");

        PackageFile.PackageInfo packageInfo = packageFile.getPackageInfo();

        return new File(distDir, packageInfo.getPackageName() + ".apk");
    }

    /**
     * 签名指定的文件,使用当前默认的签名证书进行签名
     * @param source 需要签名的文件
     * @return 签好名的文件路径
     * @throws java.io.FileNotFoundException
     * @throws Execute.BrutException
     */
    public File signedSource(File source)
            throws IOException, Execute.BrutException {

        File target = new File(source.getParentFile(), "Signed_" + source.getName());

        // 删除之前已经存在的
        if (!target.isFile()) target.delete();

        SignedProperty signedProperty = this.signedProperty;

        if (signedProperty == null) {

            signedProperty = getDefault();

            System.out.println("使用当前默认签名信息!");
        }

        signedTool.signed(signedProperty, source, target);

        return target;
    }

    /**
     * 对指定的文件进行优化,并返回优化好的文件路径
     * @param source 需要优化的文件
     * @param packageInfo 优化的包信息
     * @return 优化好的文件路径
     * @throws java.io.FileNotFoundException
     * @throws Execute.BrutException
     */
    public File optimizeSource(File source, PackageFile.PackageInfo packageInfo)
            throws FileNotFoundException, Execute.BrutException {

        File target = new File(completeDir, packageInfo.getPackageName() + ".apk");

        optimizeTool.optimize(source, target);

        return target;
    }


    public SignedProperty getDefault() throws Execute.BrutException {

        File keyDir = new File(workDir, "SignedFile");
        File keyFile = new File(keyDir, "default_key.keystore");

        SignedProperty property = new SignedProperty();

        property.setKeystorePassword("elephant");
        property.setTerm(10 * 12);
        property.setSignedName("elephant");
        property.setSignedPassword("elephant_xbwx");
        property.setName("wowowo");
        property.setOrganization("owowow");
        property.setCity("cococo");
        property.setProvince("ososos");
        property.setCode("us");
        property.setFile(keyFile);

        if (!keyDir.isDirectory()) keyDir.mkdirs();

        if (!keyFile.isFile()) {

            // 默认签名信息不存在,需要生成
            signedTool.buildSigned(property);
        }

        return property;
    }
}
