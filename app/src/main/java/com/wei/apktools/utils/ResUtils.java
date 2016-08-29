package com.wei.apktools.utils;

import java.io.InputStream;
import java.net.URL;

/**
 * Created by starrysky on 15-1-27.
 */
public class ResUtils {

    public static URL getResource(String name) {

        if (name == null) return null;

        return ResUtils.class.getResource(name);
    }

    public static InputStream getResourceAsStream(String name) {

        if (name == null) return null;

        return ResUtils.class.getResourceAsStream(name);
    }
}
