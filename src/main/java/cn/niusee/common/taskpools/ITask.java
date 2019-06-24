/*
 * Niusee server-common
 *
 * Copyright 2015-2017 by Niusee.inc. All rights reserved.
 */
package cn.niusee.common.taskpools;

/**
 * 线程任务定义类
 *
 * @author Qianliang Zhang
 */
public interface ITask {

    /**
     * 运行任务
     *
     * @return 任务运行结果。true：成功，false：失败
     */
    boolean run();

    /**
     * 取消任务
     */
    void cancel();
}
