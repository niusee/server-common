/*
 * Niusee server-common
 *
 * Copyright 2015-2017 by Niusee.inc. All rights reserved.
 */
package cn.niusee.common.taskpools.retrytask;

/**
 * 重试任务的执行状态回调
 *
 * @author Qianliang Zhang
 */
public interface OnRetryTaskCallback {
    /**
     * 重试任务开始的回调
     *
     * @param task 重试线程任务
     */
    void onTaskStart(IRetryTask task);

    /**
     * 重试任务重试的回调
     *
     * @param task       重试线程任务
     * @param tryingTime 当前重试次数
     */
    void onTaskRetry(IRetryTask task, int tryingTime);

    /**
     * 重试任务运行成功的回调
     *
     * @param task 重试线程任务
     */
    void onTaskSuccess(IRetryTask task);

    /**
     * 重试任务运行失败的回调
     *
     * @param task 重试线程任务
     */
    void onTaskFail(IRetryTask task);
}
