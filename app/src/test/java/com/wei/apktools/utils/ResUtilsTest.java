package com.wei.apktools.utils;

import org.junit.Test;

import static org.junit.Assert.*;

public class ResUtilsTest {

    @Test
    public void testGetResource() throws Exception {

        System.out.println(">> " + ResUtils.getResource(Constant.APP_ICON));
    }
}