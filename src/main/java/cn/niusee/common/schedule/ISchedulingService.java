/*
 * Niusee server-common
 *
 * Copyright 2015-2016 by Niusee.inc. All rights reserved.
 */
package cn.niusee.common.schedule;

import java.util.Date;

/**
 * 计划任务执行服务接口定义类
 *
 * @author Qianliang Zhang
 */
public interface ISchedulingService {

    /**
     * 添加执行一个周期执行的任务类
     *
     * @param interval 任务执行周期间隔（毫秒）
     * @param job      计划任务
     * @return 任务对应的KEY
     */
    String addScheduledJob(long interval, IScheduledJob job);

    /**
     * 添加一个延迟一定时间后（毫秒）执行一次的任务类
     *
     * @param delay 延迟时间（毫秒）
     * @param job   计划任务
     * @return 任务对应的KEY
     */
    String addScheduledOnceJobAfterDelay(long delay, IScheduledJob job);

    /**
     * 添加一个指定日期时间执行一次的任务类。如果指定时间在当前时间之前，则立即执行
     *
     * @param date 指定日期时间
     * @param job  计划任务
     * @return 任务对应的KEY
     */
    String addScheduledOnceJobInSpecificDate(Date date, IScheduledJob job);

    /**
     * 添加一个延迟一定时间执行的周期任务
     *
     * @param interval 任务执行周期间隔（毫秒）
     * @param delay    延迟执行的时间（毫秒）
     * @param job      计划任务
     * @return 任务对应的KEY
     */
    String addScheduledJobAfterDelay(long interval, long delay, IScheduledJob job);

    /**
     * 暂停指定名称的任务
     *
     * @param name 指定名称的任务
     */
    void pauseScheduledJob(String name);

    /**
     * 恢复指定名称的任务
     *
     * @param name 指定名称的任务
     */
    void resumeScheduledJob(String name);

    /**
     * 移除指定名称的任务
     *
     * @param name 指定名称的任务
     */
    void removeScheduledJob(String name);
}
