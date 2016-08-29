package com.wei.apktools.core;

import java.io.File;

/**
 * 该类用来保存签名证书的相关信息<br/>
 *
 * <p>{@link #keystorePassword} 签名证书密码</p>
 * <p>{@link #term} 签名证书期限(单位:天)</p>
 * <p>{@link #signedName} 签名证书用户名</p>
 * <p>{@link #signedPassword} 签名证书用户密码</p>
 * <p>{@link #name} 签名证书信息人的名称</p>
 * <p>{@link #organization} 签名证书信息人所在机构</p>
 * <p>{@link #city} 签名证书信息人所在城市</p>
 * <p>{@link #province} 签名证书信息人所在省份</p>
 * <p>{@link #code} 签名证书信息人所在国家编号</p>
 * <p>{@link #createTime} 签名证书文件创建时间</p>
 * <p>{@link #file} 签名证书文件所在文件路径</p>
 *
 * Created by jingcai.wei on 3/17/14.
 */
public class SignedProperty {

    transient
    protected int id;

    protected String keystorePassword;
    protected int term;
    protected String signedName;
    protected String signedPassword;
    protected String name;
    protected String organization;
    protected String city;
    protected String province;
    protected String code;
    protected String createTime;

    transient
    protected File file;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getKeystorePassword() {
        return keystorePassword;
    }

    public void setKeystorePassword(String keystorePassword) {
        this.keystorePassword = keystorePassword;
    }

    public int getTerm() {
        return term;
    }

    public void setTerm(int term) {
        this.term = term;
    }

    public String getSignedName() {
        return signedName;
    }

    public void setSignedName(String signedName) {
        this.signedName = signedName;
    }

    public String getSignedPassword() {
        return signedPassword;
    }

    public void setSignedPassword(String signedPassword) {
        this.signedPassword = signedPassword;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getOrganization() {
        return organization;
    }

    public void setOrganization(String organization) {
        this.organization = organization;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public File getFile() {
        return file;
    }

    public void setFile(File file) {
        this.file = file;
    }
}
