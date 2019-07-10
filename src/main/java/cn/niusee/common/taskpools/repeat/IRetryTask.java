/*
 * Niusee server-common
 *
 * Copyright 2015-2017 by Niusee.inc. All rights reserved.
 */
package cn.niusee.common.taskpools.repeat;

import cn.niusee.common.taskpools.ITask;

/**
 * 重试一定次数的任务接口定义类
 *
 * @author Qianliang Zhang
 */
public interface IRetryTask extends ITask {

    /**
     * 获取任务重试次数。0代表不重试，负数代表无限重试
     *
     * @return 任务重试次数。0代表不重试，负数代表无限重试
     */
    int getRetryTimes();
}
