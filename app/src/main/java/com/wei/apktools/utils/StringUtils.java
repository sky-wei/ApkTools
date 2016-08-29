package com.wei.apktools.utils;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by starrysky on 15-3-18.
 */
public class StringUtils {

    public static final SimpleDateFormat DATEFORMAT = new SimpleDateFormat("yyyyMMddHHmmss");

    public static boolean isEmpty(String strs) {
        return strs == null || strs.trim().length() <= 0 ? true : false;
    }

    public static String dateToString() {
        return DATEFORMAT.format(new Date());
    }
}
