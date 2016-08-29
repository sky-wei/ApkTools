package com.wei.apktools.adaper;

import com.wei.apktools.task.Execute;
import com.wei.apktools.interfaces.OutputInfo;

import java.io.*;

/**
 * Created by starrysky on 15-2-10.
 */
public class OutInfoAdaper {

    public OutInfoAdaper(Execute execute, OutputInfo outputInfo) throws IOException {

        PipedInputStream in = new PipedInputStream();
        OutputStream out = new PipedOutputStream(in);
    }
}
