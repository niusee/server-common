/*
 * Niusee nginx-live-proxy
 *
 * Copyright 2015-2016 by Niusee.inc. All rights reserved.
 */
package cn.niusee.common.process;

/**
 * 命令行进程运行情况的回调类
 *
 * @author Qianliang Zhang
 */
public interface OnProcessRunnerCallback {
    /**
     * 进程开始的回调
     *
     * @param processRunner 进程
     */
    void onStart(ProcessRunner processRunner);

    /**
     * 进程过程中运行信息的回调
     *
     * @param processRunner 进程
     * @param message       运行信息
     */
    void onMessage(ProcessRunner processRunner, String message);

    /**
     * 进程结束的回调
     *
     * @param processRunner 进程
     * @param success       运行的结果
     */
    void onComplete(ProcessRunner processRunner, boolean success);
}
