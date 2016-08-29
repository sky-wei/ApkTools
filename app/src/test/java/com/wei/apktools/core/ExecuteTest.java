package com.wei.apktools.core;

import com.wei.apktools.interfaces.OutputInfo;
import com.wei.apktools.task.Execute;
import org.junit.Test;

public class ExecuteTest {

    @Test
    public void testExec() throws Exception {

        Execute execute = new Execute();
        execute.setOutputInfo(new OutputInfo() {
            @Override
            public void onOutput(String info) {
                System.out.println(info);
            }

            @Override
            public void onOutput(String info, Throwable tr) {
                System.out.println(info);
                tr.printStackTrace();
            }
        });
        execute.exec("java -version");
    }

    @Test
    public void testOutput() throws Exception {

        println(new NullPointerException("test"));
    }

    private void println(Throwable tr) {

        tr.printStackTrace();

        System.out.println(tr);
        StackTraceElement[] trace = tr.getStackTrace();
        for (StackTraceElement traceElement : trace)
            System.out.println("\tat " + traceElement);
    }
}