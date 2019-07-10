/*
 * Niusee server-common
 *
 * Copyright 2015-2017 by Niusee.inc. All rights reserved.
 */
package cn.niusee.common.taskpools;

/**
 * 线程任务执行管理类接口定义类
 *
 * @author Qianliang Zhang
 */
public interface ITaskExecutors {
    /**
     * 运行线程任务
     *
     * @param task           线程任务
     * @param onTaskCallback 任务运行状态回调类
     * @return 返回分配给任务的任务ID
     */
    String executeTask(ITask task, OnTaskCallback onTaskCallback);

    /**
     * 取消线程任务
     *
     * @param taskId 任务ID
     */
    void cancelTask(String taskId);
}
