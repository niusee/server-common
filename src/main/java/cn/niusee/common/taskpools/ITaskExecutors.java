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
     * 添加线程任务
     *
     * @param task     线程任务
     * @param callback 任务状态回调
     * @return 返回分配给任务的任务ID
     */
    String addTask(ITask task, ITaskCallback callback);

    /**
     * 取消线程任务
     *
     * @param taskId 任务ID
     */
    void cancelTask(String taskId);
}
