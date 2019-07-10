/*
 * Niusee server-common
 *
 * Copyright 2015-2017 by Niusee.inc. All rights reserved.
 */
package cn.niusee.common.taskpools.repeat;

import cn.niusee.common.logger.LoggerHelper;

import java.util.Queue;
import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.locks.ReentrantLock;

/**
 * 重试次数的线程任务执行管理类
 *
 * @author Qianliang Zhang
 */
public class SimpleRetryTaskExecutors implements IRetryTaskExecutors {

    /**
     * 重试线程任务类封装类
     */
    private class SimpleRetryTaskWrapper implements Runnable {
        /**
         * 分配任务的ID
         */
        private final String taskId;

        /**
         * 重试线程任务类
         */
        private final IRetryTask task;

        /**
         * 当前运行次数序号
         */
        private int currentRunningIndex = 0;

        /**
         * 重试任务状态回调
         */
        private final OnRetryTaskCallback onRetryTaskCallback;

        SimpleRetryTaskWrapper(String taskId, IRetryTask task, OnRetryTaskCallback onRetryTaskCallback) {
            this.taskId = taskId;
            this.task = task;
            this.onRetryTaskCallback = onRetryTaskCallback;
        }

        /**
         * 分配任务的ID
         *
         * @return 分配任务的ID
         */
        String getTaskId() {
            return taskId;
        }

        /**
         * 获取重试线程任务类
         *
         * @return 重试线程任务类
         */
        IRetryTask getTask() {
            return task;
        }

        @Override
        public void run() {
            if (onRetryTaskCallback != null) {
                // 第一次运行，回调开始
                if (currentRunningIndex == 0) {
                    onRetryTaskCallback.onTaskStart(task);
                } else {
                    onRetryTaskCallback.onTaskRetry(task, currentRunningIndex);
                }
            }
            boolean result = task.run();
            // 运行不成功，并且没到重试次数
            if (!result && currentRunningIndex < task.getRetryTimes()) {
                currentRunningIndex++;
                // 重新运行
                retryTask(this);
            } else {
                if (result) {
                    if (onRetryTaskCallback != null) {
                        onRetryTaskCallback.onTaskSuccess(task);
                    }
                } else {
                    if (onRetryTaskCallback != null) {
                        onRetryTaskCallback.onTaskFail(task);
                    }
                }
                runningTaskPool.remove(taskId);
                // 下一个任务
                distributeNextTask();
            }
        }

        /**
         * 取消任务
         */
        private void cancel() {
            task.cancel();
            // 下一个任务
            distributeNextTask();
        }
    }

    /**
     * 日记
     */
    private static final LoggerHelper log = new LoggerHelper(SimpleRetryTaskExecutors.class);

    /**
     * 线程任务执行管理类名称前缀
     */
    private final static String EXECUTOR_NAME = "SimpleTaskExecutors-";

    /**
     * 线程任务执行管理类名称
     */
    private final String tag;

    /**
     * 待运行线程任务队列
     */
    private final Queue<SimpleRetryTaskWrapper> retryTaskQueue;

    /**
     * 任务运行线程池
     */
    private final ThreadPoolExecutor taskExecutors;

    /**
     * 同时运行数
     */
    private final int coreTaskSize;

    /**
     * 运行中的任务集合管理
     */
    private final ConcurrentMap<String, SimpleRetryTaskWrapper> runningTaskPool;

    /**
     * 任务锁
     */
    private final ReentrantLock lock = new ReentrantLock();

    /**
     * 任务ID的数值记录
     */
    private final AtomicInteger taskNumber = new AtomicInteger(1);

    public SimpleRetryTaskExecutors(String tag, int corePoolSize) {
        this.tag = EXECUTOR_NAME + tag + "-";
        this.coreTaskSize = corePoolSize;
        retryTaskQueue = new ConcurrentLinkedQueue<>();
        taskExecutors = (ThreadPoolExecutor) Executors.newCachedThreadPool();
        runningTaskPool = new ConcurrentHashMap<>(corePoolSize);
    }

    /**
     * 分配下一批任务
     */
    private void distributeNextTask() {
        try {
            lock.lock();
            distributeTask();
        } finally {
            lock.unlock();
        }
    }

    /**
     * 重新运行任务
     *
     * @param retryTaskWrapper 重试任务
     */
    private void retryTask(SimpleRetryTaskWrapper retryTaskWrapper) {
        taskExecutors.execute(retryTaskWrapper);
    }

    /**
     * 添加新的任务
     *
     * @param retryTaskWrapper 新的任务
     */
    private void addTask(SimpleRetryTaskWrapper retryTaskWrapper) {
        retryTaskQueue.add(retryTaskWrapper);
        try {
            lock.lock();
            distributeTask();
        } finally {
            lock.unlock();
        }
    }

    /**
     * 分配任务
     */
    private void distributeTask() {
        // 任务数还没达到限制
        if (runningTaskPool.size() < coreTaskSize && !retryTaskQueue.isEmpty()) {
            SimpleRetryTaskWrapper runRetryTaskWrapper = retryTaskQueue.poll();
            // 线程池运行任务
            taskExecutors.execute(runRetryTaskWrapper);
            // 放到运行中的集合中
            runningTaskPool.put(runRetryTaskWrapper.taskId, runRetryTaskWrapper);
            // 递归循环任务
            distributeTask();
        }
    }

    @Override
    public String executeTask(IRetryTask task, OnRetryTaskCallback onRetryTaskCallback) {
        if (task != null) {
            String taskId = tag + taskNumber.getAndIncrement();
            log.debug("add task: {}", taskId);
            // 添加任务
            addTask(new SimpleRetryTaskWrapper(taskId, task, onRetryTaskCallback));
            return taskId;
        }
        return null;
    }

    @Override
    public void cancelTask(String taskId) {
        log.debug("cancel task: {}", taskId);
        // 检查是否存在等待队列中，直接移除
        retryTaskQueue.removeIf(taskWrapper -> taskWrapper.getTaskId().equals(taskId));

        // 检查是否在运行队列，移除
        if (runningTaskPool.containsKey(taskId)) {
            runningTaskPool.remove(taskId).cancel();
        }
    }
}
