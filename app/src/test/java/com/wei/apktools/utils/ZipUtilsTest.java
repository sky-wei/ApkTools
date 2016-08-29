package com.wei.apktools.utils;

import org.junit.Test;

import java.io.File;
import java.util.zip.ZipEntry;

import static org.junit.Assert.*;

public class ZipUtilsTest {

    @Test
    public void testCopyFile() throws Exception {

        ZipUtils.copyFile(
                new File("/media/jingcai.wei_ii/TempFile/xiaomiexingxing3zuixinban_46.apk"),
                new File("/media/jingcai.wei_ii/TempFile/test.apk"), new ZipUtils.ZipEntryFilter() {
                    @Override
                    public boolean accept(ZipEntry entry) {
                        if (entry.getName().startsWith("META-INF/")) {
                            return false;
                        }
                        return true;
                    }
                });
    }
}