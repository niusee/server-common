/*
 * Niusee server-common
 *
 * Copyright 2015-2017 by Niusee.inc. All rights reserved.
 */
package cn.niusee.common.taskpools;

import cn.niusee.common.logger.LoggerHelper;

import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * 基础线程任务执行管理类
 *
 * @author Qianliang Zhang
 */
public class SimpleTaskExecutors implements ITaskExecutors {

    /**
     * 线程任务类封装类
     */
    private class SimpleTaskWrapper implements Runnable {
        /**
         * 分配任务的ID
         */
        private final String taskId;

        /**
         * 线程任务类
         */
        private final ITask task;

        /**
         * 任务状态回调
         */
        private final OnTaskCallback onTaskCallback;

        SimpleTaskWrapper(String taskId, ITask task, OnTaskCallback onTaskCallback) {
            this.taskId = taskId;
            this.task = task;
            this.onTaskCallback = onTaskCallback;
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
         * 获取线程任务类
         *
         * @return 线程任务类
         */
        ITask getTask() {
            return task;
        }

        @Override
        public void run() {
            runningTaskPool.put(taskId, this);
            if (onTaskCallback != null) {
                onTaskCallback.onTaskStart(task);
            }
            boolean result = task.run();
            runningTaskPool.remove(taskId);
            if (result) {
                if (onTaskCallback != null) {
                    onTaskCallback.onTaskSuccess(task);
                }
            } else {
                if (onTaskCallback != null) {
                    onTaskCallback.onTaskFail(task);
                }
            }
        }

        /**
         * 取消任务
         */
        private void cancel() {
            task.cancel();
        }
    }

    /**
     * 日记
     */
    private static final LoggerHelper log = new LoggerHelper(SimpleTaskExecutors.class);

    /**
     * 线程任务执行管理类名称
     */
    private final String tag;

    /**
     * 任务运行线程池
     */
    private final ThreadPoolExecutor taskExecutors;

    /**
     * 运行中的任务集合管理
     */
    private final ConcurrentMap<String, SimpleTaskWrapper> runningTaskPool;

    /**
     * 任务ID的数值记录
     */
    private final AtomicInteger taskNumber = new AtomicInteger(1);

    public SimpleTaskExecutors(String tag, int corePoolSize) {
        this.tag = tag + "-";
        taskExecutors = (ThreadPoolExecutor) Executors.newFixedThreadPool(corePoolSize);
        runningTaskPool = new ConcurrentHashMap<>(corePoolSize);
    }

    @Override
    public String executeTask(ITask task, OnTaskCallback onTaskCallback) {
        if (task != null) {
            String taskId = tag + taskNumber.getAndIncrement();
            log.debug("Add task: {}", taskId);
            SimpleTaskWrapper taskWrapper = new SimpleTaskWrapper(taskId, task, onTaskCallback);
            taskExecutors.execute(taskWrapper);
            return taskId;
        }
        return null;
    }

    @Override
    public void cancelTask(String taskId) {
        log.debug("Cancel task: {}", taskId);
        // 检查是否存在等待队列中，直接移除
        taskExecutors.getQueue().removeIf(runnable -> ((SimpleTaskWrapper) runnable).getTaskId().equals(taskId));

        // 检查运行队列
        if (runningTaskPool.containsKey(taskId)) {
            runningTaskPool.remove(taskId).cancel();
        }
    }
}
