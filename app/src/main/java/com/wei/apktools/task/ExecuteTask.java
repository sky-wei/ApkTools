package com.wei.apktools.task;

import com.wei.apktools.core.HandleException;

/**
 * Created by starrysky on 15-2-10.
 */
public interface ExecuteTask {

    /**
     * 处理任务的回调方法
     * @param execute
     */
    void onHandler(Execute execute) throws HandleException, Execute.BrutException;
}
