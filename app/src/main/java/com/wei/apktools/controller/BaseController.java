package com.wei.apktools.controller;

import javax.swing.*;

/**
 * Created by starrysky on 15-3-16.
 */
public abstract class BaseController {

    private JFrame frame;

    public BaseController(JFrame frame) {
        this.frame = frame;
    }

    public JFrame getFrame() {
        return frame;
    }

//    /**
//     * 退出
//     */
//    public abstract void quit();
}
