package com.wei.apktools.config;

import com.google.gson.reflect.TypeToken;
import com.wei.apktools.utils.JsonUtils;
import com.wei.apktools.utils.Log;
import org.apache.commons.io.FileUtils;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by starrysky on 15-1-17.
 */
public class EnvProperty {

    /**  JDK_HOME */
    public static final String JDK_HOME = "JDK_HOME";
    /** 反编译的临时目录 */
    public static final String TEMP_DIR = "TEMP_DIR";
    /** 是否清空临时目录 */
    public static final String CLEAR_TEMP = "CLEAR_TEMP";
    /** 已完成的目录 */
    public static final String COMPLETE_DIR = "COMPLETE_DIR";

    /**  输出的缓存行数  */
    public static final String OUT_BUFFER_LINE = "OUT_BUFFER_LINE";

    /**  证书密码 */
    public static final String CREDENTIALS_KEYPASSWORD = "CREDENTIALS_KEYPASSWORD";
    /**  别名 */
    public static final String CREDENTIALS_ALIAS = "CREDENTIALS_ALIAS";
    /**  密码 */
    public static final String CREDENTIALS_PASSWORD = "CREDENTIALS_PASSWORD";
    /**  证书路径 */
    public static final String CREDENTIALS_PATH = "CREDENTIALS_PATH";


    private static EnvProperty envProperty;

    public static EnvProperty getInstance() {

        if (envProperty == null) {
            envProperty = new EnvProperty();
        }
        return envProperty;
    }

    private Map<String, String> property = new HashMap<>();

    public EnvProperty() {

        initEnvProperty();
    }

    /**
     * 初始化环境属性
     */
    private void initEnvProperty() {

        // 重新加载
        reloadEnvProperty();
    }

    /**
     * 加载本地的环境属性
     * @return
     */
    private Map<String, String> loadEnvProperty(File configFile) {

        if (configFile == null
                || !configFile.exists()
                || !configFile.isFile()) return null;

        try {
            return JsonUtils.fromJson(FileUtils.readFileToString(configFile, "UTF-8"),
                    new TypeToken<Map<String, String>>() {}.getType());
        } catch (Exception e) {
            Log.e("加载配置文件异常!", e);
        }
        return null;
    }

    /**
     * 加载默认的环境属性
     * @return
     */
    private Map<String, String> loadDefalutEnvProperty() {

        Map<String, String> defalutProperty = new HashMap<>();

        // 默认输出的目录
        File outDir = new File("out");

        defalutProperty.put(JDK_HOME, System.getenv("JAVA_HOME"));
        defalutProperty.put(TEMP_DIR, new File(outDir, "tempDir").getPath());
        defalutProperty.put(COMPLETE_DIR, new File(outDir, "completeDir").getPath());
        defalutProperty.put(CLEAR_TEMP, Boolean.toString(false));

        return defalutProperty;
    }

    /**
     * 获取环境配置目录
     * @return
     */
    public File getConfigDir() {
        return new File("config");
    }

    /**
     * 获取本地环境属性文件
     * @return
     */
    public File getEnvPropertyFile() {
        return new File(getConfigDir(), "envProperty.ini");
    }

    /**
     * 重新加载环境属性信息
     */
    public void reloadEnvProperty() {

        File propertyFile = getEnvPropertyFile();

        // 加载本地属性信息
        Map<String, String> localProperty = loadEnvProperty(propertyFile);

        if (localProperty == null || localProperty.isEmpty()) {
            // 加载默认的属性信息
            localProperty = loadDefalutEnvProperty();
        }

        // 添加所有的环境属性
        property.clear();
        property.putAll(localProperty);
    }

    /**
     * 保存环境属性到本地文件
     */
    public void saveEnvProperty() {

        File configDir = getConfigDir();

        if (!configDir.exists()) configDir.mkdirs();

        try {
            FileUtils.write(getEnvPropertyFile(),
                    JsonUtils.toJsonString(property), "UTF-8");
        } catch (Exception e) {
            Log.e("保存环境属性信息失败!", e);
        }
    }

    /**
     * 添加环境属性值
     * @param key
     * @param value
     */
    public void put(String key, String value) {
        property.put(key, value);
    }

    /**
     * 添加环境属性值
     * @param key
     * @param value
     */
    public void putBoolean(String key, boolean value) {
        property.put(key, Boolean.toString(value));
    }

    /**
     * 添加环境属性值
     * @param key
     * @param value
     */
    public void putInt(String key, int value) {
        property.put(key, Integer.toString(value));
    }

    /**
     * 获取环境属性值
     * @param key
     * @return
     */
    public String get(String key) {
        return property.get(key);
    }

    /**
     * 获取环境属性值
     * @param key
     * @return
     */
    public int getInt(String key) {
        String value = property.get(key);
        return value == null ? 0 : Integer.parseInt(value);
    }

    /**
     * 获取环境属性值
     * @param key
     * @return
     */
    public boolean getBoolean(String key) {
        String value = property.get(key);
        return value == null ? false : Boolean.parseBoolean(value);
    }

    /**
     * 判断指定的环境属性是否存在
     * @param key
     * @return
     */
    public boolean containsKey(Object key) {
        return property.containsKey(key);
    }

    /**
     * 移除指定环境属性
     * @param key
     * @return
     */
    public String remove(Object key) {
        return property.remove(key);
    }

    /**
     * 清空所有环境属性
     */
    public void clear() {
        property.clear();
    }
}
