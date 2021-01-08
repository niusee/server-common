/*
 * Niusee server-common
 *
 * Copyright 2015-2017 by Niusee.inc. All rights reserved.
 */
package cn.niusee.common.taskpools.retrytask;

/**
 * 重试线程任务执行管理类接口定义类
 *
 * @author Qianliang Zhang
 */
public interface IRetryTaskExecutors {
    /**
     * 运行重试线程任务
     *
     * @param task                重试线程任务
     * @param onRetryTaskCallback 重试任务运行状态回调类
     * @return 返回分配给任务的任务ID
     */
    String executeTask(IRetryTask task, OnRetryTaskCallback onRetryTaskCallback);

    /**
     * 取消重试线程任务
     *
     * @param taskId 任务ID
     */
    void cancelTask(String taskId);
}
