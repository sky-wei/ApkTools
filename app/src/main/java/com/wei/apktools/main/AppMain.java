package com.wei.apktools.main;

import com.wei.apktools.controller.MainController;
import com.wei.apktools.interfaces.MainControllerInterfaces;
import com.wei.apktools.interfaces.MainModelInterfaces;
import com.wei.apktools.model.MainModel;

import javax.swing.*;

/**
 * Created by starrysky on 15-1-13.
 */
public class AppMain {

    public static void main(String[] args) {

        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {

                MainModelInterfaces mainModelInterfaces = new MainModel();
                MainControllerInterfaces mainControllerInterfaces = new MainController(mainModelInterfaces);
            }
        });
    }
}
