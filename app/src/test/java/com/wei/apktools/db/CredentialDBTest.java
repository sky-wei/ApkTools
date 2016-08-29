package com.wei.apktools.db;

import com.wei.apktools.core.SignedProperty;
import org.junit.Before;
import org.junit.Test;

import java.io.File;

import static org.junit.Assert.*;

public class CredentialDBTest {

    private CredentialDB credentialDB;

    @Before
    public void setUp() throws Exception {
        credentialDB = new CredentialDB();
    }

    @Test
    public void testQueryCredentials() throws Exception {
        System.out.println(">> " + credentialDB.queryCredentials());
    }

    @Test
    public void testQueryCredential() throws Exception {
        System.out.println(">> " + credentialDB.queryCredential(1));
    }

    @Test
    public void testInsertCredential() throws Exception {
        SignedProperty signedProperty = new SignedProperty();
        signedProperty.setKeystorePassword("123456");
        signedProperty.setSignedName("123456");
        signedProperty.setSignedPassword("654321");
        signedProperty.setFile(new File("test/test.txt"));
        credentialDB.insertCredential(signedProperty);
    }

    @Test
    public void testDeleteCredential() throws Exception {
        System.out.println(">> " + credentialDB.deleteCredential(1));
    }

    @Test
    public void testUpdateCredential() throws Exception {

    }
}