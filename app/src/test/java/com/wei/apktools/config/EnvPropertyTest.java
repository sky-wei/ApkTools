package com.wei.apktools.config;

import org.junit.Before;
import org.junit.Test;

public class EnvPropertyTest {

    EnvProperty envProperty;

    @Before
    public void setUp() throws Exception {

        envProperty = EnvProperty.getInstance();
    }

    @Test
    public void testGetConfigDir() throws Exception {

        System.out.println("Dir: " + envProperty.getConfigDir());
    }

    @Test
    public void testGetEnvPropertyFile() throws Exception {

        System.out.println("PropertyFile: " + envProperty.getEnvPropertyFile());
    }

    @Test
    public void testReloadEnvProperty() throws Exception {

        envProperty.reloadEnvProperty();

        testGet();
    }

    @Test
    public void testSaveEnvProperty() throws Exception {

        envProperty.saveEnvProperty();
    }

    @Test
    public void testPut() throws Exception {

        envProperty.put("TEST", "test");

        System.out.println("TEST: " + envProperty.get("TEST"));
    }

    @Test
    public void testGet() throws Exception {

        System.out.println("JDK_HOME: " + envProperty.get(EnvProperty.JDK_HOME));
        System.out.println("JDK_HOME: " + envProperty.get(EnvProperty.TEMP_DIR));
        System.out.println("JDK_HOME: " + envProperty.get(EnvProperty.CLEAR_TEMP));
        System.out.println("JDK_HOME: " + envProperty.get(EnvProperty.COMPLETE_DIR));
    }
}