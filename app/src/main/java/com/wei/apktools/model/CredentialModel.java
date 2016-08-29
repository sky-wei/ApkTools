package com.wei.apktools.model;

import com.wei.apktools.bean.CredentialsBean;
import com.wei.apktools.config.EnvProperty;
import com.wei.apktools.core.HandleException;
import com.wei.apktools.core.SignedProperty;
import com.wei.apktools.core.SignedTool;
import com.wei.apktools.db.CredentialDB;
import com.wei.apktools.interfaces.CredentialModelInterfaces;
import com.wei.apktools.task.Execute;
import com.wei.apktools.task.ExecuteTask;
import com.wei.apktools.task.TaskManager;
import com.wei.apktools.utils.Constant;
import com.wei.apktools.utils.JsonUtils;
import com.wei.apktools.utils.Log;
import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Observable;

/**
 * Created by starrysky on 15-3-17.
 */
public class CredentialModel extends Observable implements CredentialModelInterfaces {

    public static final SimpleDateFormat DATA_FORMAT = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    private EnvProperty envProperty;
    private CredentialDB credentialDB;

    private String lastShowSigned;
    private String lastSignedDir;

    public CredentialModel() {
        envProperty = EnvProperty.getInstance();
        credentialDB = new CredentialDB();
    }

    @Override
    public CredentialsBean getCredentialsConfig() {

        CredentialsBean credentialsBean = new CredentialsBean();

        credentialsBean.setKeyPassword(envProperty.get(EnvProperty.CREDENTIALS_KEYPASSWORD));
        credentialsBean.setAlias(envProperty.get(EnvProperty.CREDENTIALS_ALIAS));
        credentialsBean.setPassword(envProperty.get(EnvProperty.CREDENTIALS_PASSWORD));
        credentialsBean.setPath(envProperty.get(EnvProperty.CREDENTIALS_PATH));

        return credentialsBean;
    }

    @Override
    public void setCredentialsConfig(CredentialsBean credentialsBean) {

        // 保存默认签名信息
        envProperty.put(EnvProperty.CREDENTIALS_KEYPASSWORD, credentialsBean.getKeyPassword());
        envProperty.put(EnvProperty.CREDENTIALS_ALIAS, credentialsBean.getAlias());
        envProperty.put(EnvProperty.CREDENTIALS_PASSWORD, credentialsBean.getPassword());
        envProperty.put(EnvProperty.CREDENTIALS_PATH, credentialsBean.getPath());
        envProperty.saveEnvProperty();
    }

    @Override
    public String getLastShowSigned() {
        return lastShowSigned;
    }

    @Override
    public void showSigned(final String filePath) {

        if (filePath == null) return ;

        // 保存最后的文件路径
        lastShowSigned = filePath;

        TaskManager.getInstance().addTask(new ExecuteTask() {
            @Override
            public void onHandler(Execute execute) throws Execute.BrutException {
                try {
                    SignedTool signedTool = new SignedTool(execute);
                    signedTool.verifySigned(new File(filePath));
                } catch (FileNotFoundException e) {
                    Log.d("源文件不存在", e);
                }
            }
        });
    }

    @Override
    public String getLastSignedDir() {
        return lastSignedDir;
    }

    @Override
    public void batchSigned(final CredentialsBean credentialsBean, final File... files) {

        if (credentialsBean == null || files == null) return ;

        // 保存最后的文件目录
        lastSignedDir = files[0].getParent();

        // 进行签名
        signedFile(newSignedProperty(credentialsBean), files);
    }

    @Override
    public void createCredentials(final SignedProperty signedProperty) {

        if (signedProperty == null) return ;

        TaskManager.getInstance().addTask(new ExecuteTask() {
            @Override
            public void onHandler(Execute execute) throws HandleException, Execute.BrutException {

                // 生成签名证书
                SignedTool signedTool = new SignedTool(execute);
                signedTool.buildSigned(signedProperty);

                // 保存签名证书
                signedProperty.setCreateTime(DATA_FORMAT.format(new Date()));
                if (!credentialDB.insertCredential(signedProperty)) {
                    throw new HandleException("保存签名证书异常");
                }
                measurementsChanged();
            }
        });
    }

    @Override
    public void measurementsChanged() {
        setChanged();
        notifyObservers(credentialDB.queryCredentials());
    }

    @Override
    public boolean deleteCredentials(SignedProperty... signedPropertys) {

        if (signedPropertys == null) return false;

        for (SignedProperty signedProperty : signedPropertys) {
            credentialDB.deleteCredential(signedProperty.getId());
            FileUtils.deleteQuietly(signedProperty.getFile());
        }
        return true;
    }

    @Override
    public void signedFile(final SignedProperty signedProperty, final File... files) {

        if (signedProperty == null || files == null) return ;

        TaskManager.getInstance().addTask(new ExecuteTask() {
            @Override
            public void onHandler(Execute execute) throws Execute.BrutException {

                // 签名文件属性
                SignedTool signedTool = new SignedTool(execute);

                for (int i = 0; i < files.length; i++) {
                    try {
                        File target = new File(files[i].getParentFile(), "signed_" + files[i].getName());
                        // 进入签名
                        signedTool.signed(signedProperty, files[i], target);
                    } catch (IOException e) {
                        Log.e("签名文件异常", e);
                    }
                }
            }
        });
    }

    @Override
    public boolean importCredentials(File file) {

        if (file == null || !file.isFile()) return false;

        try {
            File configFile = new File(file.getPath() + ".info");
            String configContent = FileUtils.readFileToString(configFile, "UTF-8");
            // 导入的配置信息
            File target = getSaveCredentialFile(file);
            FileUtils.copyFile(file, target);
            SignedProperty signedProperty = JsonUtils.fromJson(configContent, SignedProperty.class);
            signedProperty.setFile(target);
            credentialDB.insertCredential(signedProperty);
            return true;
        } catch (Exception e) {
            Log.e("导入签名证书出错了", e);
        }
        return false;
    }

    private File getSaveCredentialFile(File file) {

        int i = 1;
        File saveFile = new File(Constant.CREDENTIAL_DIR, file.getName());

        while (true) {
            if (!saveFile.isFile()) {
                return saveFile;
            }
            saveFile = new File(Constant.CREDENTIAL_DIR, i + "_" + file.getName());
            i++;
        }
    }

    @Override
    public boolean exportCredentials(File dir, SignedProperty signedProperty) {

        if (dir == null || signedProperty == null) return false;

        if (!dir.exists()) dir.mkdirs();

        try {
            // 处理的文件
            File source = signedProperty.getFile();
            FileUtils.copyFile(source, new File(dir, source.getName()));

            // 处理配置信息
            String configContent = JsonUtils.toJsonString(signedProperty);
            FileUtils.write(new File(dir, source.getName() + ".info"), configContent, "UTF-8");
            return true;
        } catch (Exception e) {
            Log.e("导出签名证书出错了", e);
        }
        return false;
    }

    private SignedProperty newSignedProperty(CredentialsBean credentialsBean) {

        SignedProperty signedProperty = new SignedProperty();

        signedProperty.setKeystorePassword(credentialsBean.getKeyPassword());
        signedProperty.setSignedName(credentialsBean.getAlias());
        signedProperty.setSignedPassword(credentialsBean.getPassword());
        signedProperty.setFile(new File(credentialsBean.getPath()));

        return signedProperty;
    }
}
