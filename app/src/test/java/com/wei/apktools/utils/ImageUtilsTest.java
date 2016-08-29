package com.wei.apktools.utils;

import org.junit.Test;

import static org.junit.Assert.*;

public class ImageUtilsTest {

    @Test
    public void testLoadInsideImage() throws Exception {

        ImageUtils.loadInsideImage(Constant.APP_ICON);
    }
}