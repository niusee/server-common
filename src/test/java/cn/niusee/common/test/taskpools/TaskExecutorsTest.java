/*
 * Niusee server-common
 *
 * Copyright 2015-2017 by Niusee.inc. All rights reserved.
 */
package cn.niusee.common.test.taskpools;

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

    public void testTaskExecutors() {
        SimpleTaskExecutors executors = new SimpleTaskExecutors("Test", 3);
        for (int i = 1; i <= 10; i++) {
            final int index = i;
            executors.executeTask(new ITask() {
                @Override
                public void cancel() {

                }

                @Override
                public boolean run() {
                    System.out.println("test running: " + index);
                    return index % 2 != 0;
                }
            }, new OnTaskCallback() {
                @Override
                public void onTaskStart(ITask task) {
                    System.out.println("test start: " + index);
                }

                @Override
                public void onTaskSuccess(ITask task) {
                    System.out.println("test complete: " + index);
                }

                @Override
                public void onTaskFail(ITask task) {
                    System.out.println("test error: " + index);
                }
            });
        }

        for (int i = 11; i <= 20; i++) {
            final int index = i;
            executors.executeTaskWithoutCallback(new ITask() {
                @Override
                public void cancel() {

                }

                @Override
                public boolean run() {
                    System.out.println("test running: " + index);
                    return true;
                }
            });
        }

        for (int i = 21; i <= 30; i++) {
            final int index = i;
            executors.executeTaskWithoutCallback((INonCancelableTask) () -> {
                System.out.println("test running: " + index);
                return true;
            });
        }
    }
}
