/*
 * Niusee server-common
 *
 * Copyright 2015-2020 by Niusee.inc. All rights reserved.
 */
package cn.niusee.common.taskpools;

/**
 * 不可取消的线程任务定义类
 *
 * @author Qianliang Zhang
 */
public interface INonCancelableTask extends ITask {

    @Override
    default void cancel() {
        throw new IllegalStateException("task non-cancelable");
    }
}
