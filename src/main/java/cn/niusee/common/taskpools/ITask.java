/*
 * Niusee server-common
 *
 * Copyright 2015-2017 by Niusee.inc. All rights reserved.
 */
package cn.niusee.common.taskpools;

/**
 * 线程任务接口类
 *
 * @author Qianliang Zhang
 */
public interface ITask extends Runnable {
    /**
     * 设置任务ID
     *
     * @param taskId 任务ID
     */
    void setTaskId(String taskId);

    /**
     * 获取任务ID
     *
     * @return 任务ID
     */
    String getTaskId();

    /**
     * 设置任务状态回调
     *
     * @param taskCallback 任务状态回调
     */
    void setTaskCallback(ITaskCallback taskCallback);

    /**
     * 任务的取消方法
     */
    void cancel();
}
