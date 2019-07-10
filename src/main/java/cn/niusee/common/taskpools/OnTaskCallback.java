/*
 * Niusee server-common
 *
 * Copyright 2015-2017 by Niusee.inc. All rights reserved.
 */
package cn.niusee.common.taskpools;

/**
 * 任务的执行状态回调
 *
 * @author Qianliang Zhang
 */
public interface OnTaskCallback {
    /**
     * 任务开始的回调
     *
     * @param task 线程任务
     */
    void onTaskStart(ITask task);

    /**
     * 任务运行成功的回调
     *
     * @param task 线程任务
     */
    void onTaskSuccess(ITask task);

    /**
     * 任务运行失败的回调
     *
     * @param task 线程任务
     */
    void onTaskFail(ITask task);
}
