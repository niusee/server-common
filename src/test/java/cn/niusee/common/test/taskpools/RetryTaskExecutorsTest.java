/*
 * Niusee server-common
 *
 * Copyright 2015-2017 by Niusee.inc. All rights reserved.
 */
package cn.niusee.common.test.taskpools;

import cn.niusee.common.logger.LoggerHelper;
import cn.niusee.common.taskpools.retrytask.IRetryTask;
import cn.niusee.common.taskpools.retrytask.OnRetryTaskCallback;
import cn.niusee.common.taskpools.retrytask.SimpleRetryTaskExecutors;
import junit.framework.TestCase;

import java.util.ArrayList;
import java.util.List;

/**
 * 测试线程池运行任务
 *
 * @author Qianliang Zhang
 */
public class RetryTaskExecutorsTest extends TestCase {

    private static final LoggerHelper log = new LoggerHelper(RetryTaskExecutorsTest.class);

    public void testTaskExecutors() {
        List<String> tasks = new ArrayList<>(20);
        SimpleRetryTaskExecutors executors = new SimpleRetryTaskExecutors("RetryTest", 3);
        for (int i = 1; i <= 100; i++) {
            final int index = i;
            tasks.add(executors.executeTask(new IRetryTask() {

                int currentTime = 0;

                @Override
                public int getRetryTimes() {
                    return 3;
                }

                @Override
                public void cancel() {

                }

                @Override
                public boolean run() {
                    currentTime++;
                    log.debug("test running: {} times: {}", index, currentTime);
                    try {
                        Thread.sleep(2000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    return currentTime >= getRetryTimes() || index % 2 != 0;
                }
            }, new OnRetryTaskCallback() {

                @Override
                public void onTaskStart(IRetryTask task) {
                    log.debug("test start: {}", index);
                }

                @Override
                public void onTaskRetry(IRetryTask task, int tryingTime) {
                    log.debug("test retry: {}", index);
                }

                @Override
                public void onTaskSuccess(IRetryTask task) {
                    log.debug("test success: {}", index);
                }

                @Override
                public void onTaskFail(IRetryTask task) {
                    log.debug("test error: {}", index);
                }
            }));
        }

        for (int i = 1; i <= 100; i++) {
            if ( i % 2 == 0) {
                String taskId = tasks.get((i - 1));
                executors.cancelTask(taskId);
            }
        }

        try {
            Thread.sleep(60000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
