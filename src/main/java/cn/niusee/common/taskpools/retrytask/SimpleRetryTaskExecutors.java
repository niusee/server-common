/*
 * Niusee server-common
 *
 * Copyright 2015-2017 by Niusee.inc. All rights reserved.
 */
package cn.niusee.common.taskpools.retrytask;

import cn.niusee.common.logger.LoggerHelper;

import java.util.Queue;
import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicLong;
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
         * 是否取消
         */
        private volatile boolean cancel = false;

        /**
         * 当前运行次数
         */
        private int currentRunningTimes = 0;

        /**
         * 重试任务状态回调
         */
        private final OnRetryTaskCallback onRetryTaskCallback;

        SimpleRetryTaskWrapper(String taskId, IRetryTask task, OnRetryTaskCallback onRetryTaskCallback) {
            this.taskId = taskId;
            this.task = task;
            this.onRetryTaskCallback = onRetryTaskCallback;
        }

        @Override
        public void run() {
            // 在任务开始时取消，任务开始后，就运行一遍
            if (cancel) {
                return;
            }
            if (onRetryTaskCallback != null) {
                // 第一次运行，回调开始
                if (currentRunningTimes == 0) {
                    onRetryTaskCallback.onTaskStart(task);
                } else {
                    onRetryTaskCallback.onTaskRetry(task, currentRunningTimes);
                }
            }
            boolean result = task.run();
            // 运行不成功，并且没到重试次数
            if (!result && currentRunningTimes < task.getRetryTimes()) {
                currentRunningTimes++;
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
            cancel = true;
            task.cancel();
        }
    }

    /**
     * 日记
     */
    private static final LoggerHelper log = new LoggerHelper(SimpleRetryTaskExecutors.class);

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
    private final AtomicLong taskNumber = new AtomicLong(1);

    public SimpleRetryTaskExecutors(String tag, int corePoolSize) {
        this.tag = tag + "-";
        this.coreTaskSize = corePoolSize;
        retryTaskQueue = new ConcurrentLinkedQueue<>();
        runningTaskPool = new ConcurrentHashMap<>(corePoolSize);
        taskExecutors = (ThreadPoolExecutor) Executors.newCachedThreadPool();
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
        distributeNextTask();
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
            log.debug("add retry task: {}", taskId);
            // 添加任务
            addTask(new SimpleRetryTaskWrapper(taskId, task, onRetryTaskCallback));
            return taskId;
        }
        return null;
    }

    @Override
    public void cancelTask(String taskId) {
        log.debug("cancel retry task: {}", taskId);
        // 检查是否存在等待队列中，直接移除
        retryTaskQueue.removeIf(taskWrapper -> taskWrapper.taskId.equals(taskId));

        // 检查是否在运行队列，移除
        if (runningTaskPool.containsKey(taskId)) {
            runningTaskPool.remove(taskId).cancel();
            // 下一个任务
            distributeNextTask();
        }
    }
}
