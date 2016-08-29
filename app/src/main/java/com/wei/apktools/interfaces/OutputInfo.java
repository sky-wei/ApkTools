package com.wei.apktools.interfaces;

/**
 * Created by starrysky on 15-2-4.
 *
 * 输出信息的接口
 */
public interface OutputInfo {

    /**
     * 输出信息
     * @param info
     */
    void onOutput(String info);

    /**
     * 输出信息
     * @param info
     * @param tr
     */
    void onOutput(String info, Throwable tr);
}
