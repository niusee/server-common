/*
 * Niusee server-common
 *
 * Copyright 2015-2017 by Niusee.inc. All rights reserved.
 */
package cn.niusee.common.test.taskpools;

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

    public void testTaskExecutors() {
        List<String> tasks = new ArrayList<>(20);
        SimpleRetryTaskExecutors executors = new SimpleRetryTaskExecutors("RetryTest", 3);
        for (int i = 1; i <= 20; i++) {
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
                    System.out.println("test running: " + index + " times: " + currentTime);
                    try {
                        Thread.sleep(200);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    return currentTime >= getRetryTimes() || index % 2 != 0;
                }
            }, new OnRetryTaskCallback() {

                @Override
                public void onTaskStart(IRetryTask task) {
                    System.out.println("test start: " + index);
                }

                @Override
                public void onTaskRetry(IRetryTask task, int tryingTime) {
                    System.out.println("test retry: " + index);
                }

                @Override
                public void onTaskSuccess(IRetryTask task) {
                    System.out.println("test complete: " + index);
                }

                @Override
                public void onTaskFail(IRetryTask task) {
                    System.out.println("test error: " + index);
                }
            }));
        }

        for (int i = 1; i <= 20; i++) {
            if ( i % 2 != 0) {
                System.out.println("cancel " + (i - 1));
                executors.cancelTask(tasks.get(i));
            }
        }

        try {
            Thread.sleep(10000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
