/*
 * Niusee server-common
 *
 * Copyright 2015-2016 by Niusee.inc. All rights reserved.
 */
package cn.niusee.common.schedule;

/**
 * 计划工作定义类
 *
 * @author Qianliang Zhang
 */
public interface IScheduledJob {

    /**
     * 执行任务。计划服务的触发生效时被执行。
     *
     * @param service 计划服务
     * @throws CloneNotSupportedException 非法调用时抛出错误
     */
    void execute(ISchedulingService service) throws CloneNotSupportedException;
}
