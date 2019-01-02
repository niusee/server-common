/*
 * Niusee server-common
 *
 * Copyright 2015-2017 by Niusee.inc. All rights reserved.
 */
package cn.niusee.common.test.taskpools;

import cn.niusee.common.taskpools.BaseTask;
import cn.niusee.common.taskpools.ITask;
import cn.niusee.common.taskpools.ITaskCallback;
import cn.niusee.common.taskpools.SimpleTaskExecutors;
import junit.framework.TestCase;

/**
 * 测试线程池运行任务
 *
 * @author Qianliang Zhang
 */
public class TaskExecutorsTest extends TestCase {

    public void testTaskExecutors() {
        SimpleTaskExecutors executors = new SimpleTaskExecutors("Test", 10);
        executors.addTask(new BaseTask() {
            @Override
            public void cancel() {

            }

            @Override
            public void run() {
                if (taskCallback != null) {
                    taskCallback.onTaskStart(this);
                }

                System.out.println("test running");

                if (taskCallback != null) {
                    taskCallback.onTaskComplete(this);
                }
            }
        }, new ITaskCallback() {
            @Override
            public void onTaskStart(ITask task) {
                System.out.println("test start");
            }

            @Override
            public void onTaskComplete(ITask task) {
                System.out.println("test complete");
            }

            @Override
            public void onTaskError(ITask task) {
                System.out.println("test error");
            }
        });
    }
}
