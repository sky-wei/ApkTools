package com.wei.apktools.core;

/**
 * Created by jingcai.wei on 3/18/14.
 */
public class HandleException extends Exception {

    public HandleException() {
        super();
    }

    public HandleException(String msg) {
        super(msg);
    }

    public HandleException(String msg, Throwable tr) {
        super(msg, tr);
    }
}
