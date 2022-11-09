/*
 * Niusee server-common
 *
 * Copyright 2015-2017 by Niusee.inc. All rights reserved.
 */
package cn.niusee.common.test.taskpools;

import cn.niusee.common.logger.LoggerHelper;
import cn.niusee.common.taskpools.INonCancelableTask;
import cn.niusee.common.taskpools.ITask;
import cn.niusee.common.taskpools.OnTaskCallback;
import cn.niusee.common.taskpools.SimpleTaskExecutors;
import junit.framework.TestCase;

/**
 * 测试线程池运行任务
 *
 * @author Qianliang Zhang
 */
public class TaskExecutorsTest extends TestCase {

    private static final LoggerHelper log = new LoggerHelper(TaskExecutorsTest.class);

    public void testTaskExecutors1() {
        SimpleTaskExecutors executors = new SimpleTaskExecutors("Test-Executors", 3);
        for (int i = 1; i <= 20; i++) {
            final int index = i;
            executors.executeTask(new ITask() {
                @Override
                public void cancel() {

                }

                @Override
                public boolean run() {
                    log.debug("test running: {}", index);
                    try {
                        Thread.sleep(2000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    return index % 2 != 0;
                }
            }, new OnTaskCallback() {
                @Override
                public void onTaskStart(ITask task) {
                    log.debug("test start: {}", index);
                }

                @Override
                public void onTaskSuccess(ITask task) {
                    log.debug("test success: {}", index);
                }

                @Override
                public void onTaskFail(ITask task) {
                    log.debug("test error: {}", index);
                }
            });
        }

        try {
            Thread.sleep(30000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void testTaskExecutors2() {
        SimpleTaskExecutors executors = new SimpleTaskExecutors("Test-NonCallback-Executors", 3);
        for (int i = 1; i <= 20; i++) {
            final int index = i;
            executors.executeTaskWithoutCallback(new ITask() {
                @Override
                public void cancel() {

                }

                @Override
                public boolean run() {
                    log.debug("test running: {}", index);
                    return true;
                }
            });
        }
    }

    public void testTaskExecutors3() {
        SimpleTaskExecutors executors = new SimpleTaskExecutors("Test-WithoutCallback-Executors", 3);
        for (int i = 1; i <= 20; i++) {
            final int index = i;
            executors.executeTaskWithoutCallback((INonCancelableTask) () -> {
                log.debug("test running: {}", index);
                return true;
            });
        }
    }
}
