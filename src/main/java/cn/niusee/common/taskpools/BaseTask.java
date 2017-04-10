/*
 * Niusee server-common
 *
 * Copyright 2015-2017 by Niusee.inc. All rights reserved.
 */
package cn.niusee.common.taskpools;

/**
 * 线程任务基类
 *
 * @author Qianliang Zhang
 */
public abstract class BaseTask implements ITask {
    /**
     * 任务ID
     */
    protected String taskId;

    /**
     * 任务状态回调类
     */
    protected ITaskCallback taskCallback;

    @Override
    public void setTaskId(String taskId) {
        this.taskId = taskId;
    }

    @Override
    public String getTaskId() {
        return taskId;
    }

    @Override
    public void setTaskCallback(ITaskCallback taskCallback) {
        this.taskCallback = taskCallback;
    }
}
