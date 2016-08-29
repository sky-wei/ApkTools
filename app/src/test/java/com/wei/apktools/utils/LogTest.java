package com.wei.apktools.utils;

import org.junit.Test;

public class LogTest {

    @Test
    public void testI() throws Exception {

        Log.i("Test...........");
    }

    @Test
    public void testI1() throws Exception {

        Log.i("Test...........", new NullPointerException("test"));
    }

    @Test
    public void testE() throws Exception {

        Log.e("Test.......", new Exception("test"));
    }
}