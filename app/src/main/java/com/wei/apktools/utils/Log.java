package com.wei.apktools.utils;

import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;

/**
 * Created by starrysky on 15-1-14.
 */
public class Log {

    {
        PropertyConfigurator.configure("src/main/resource/log4j.properties");
    }

    private static Logger logger = Logger.getLogger("apkToolsLog");

    public static void i(String msg) {
        logger.info(msg);
    }

    public static void i(String msg, Throwable tr) {
        logger.info(msg, tr);
    }

    public static void d(String msg) {
        logger.debug(msg);
    }

    public static void d(String msg, Throwable tr) {
        logger.debug(msg, tr);
    }

    public static void w(String msg) {
        logger.warn(msg);
    }

    public static void w(String msg, Throwable tr) {
        logger.warn(msg, tr);
    }

    public static void e(String msg) {
        logger.error(msg);
    }

    public static void e(String msg, Throwable tr) {
        logger.error(msg, tr);
    }
}
