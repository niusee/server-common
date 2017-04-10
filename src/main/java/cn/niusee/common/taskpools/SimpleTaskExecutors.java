/*
 * Niusee server-common
 *
 * Copyright 2015-2017 by Niusee.inc. All rights reserved.
 */
package cn.niusee.common.taskpools;

import cn.niusee.common.logger.LoggerHelper;

import java.util.List;
import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

/**
 * 基础线程任务执行管理类
 *
 * @author Qianliang Zhang
 */
public class SimpleTaskExecutors implements ITaskExecutors {

    /**
     * 日记
     */
    private static LoggerHelper log = new LoggerHelper(SimpleTaskExecutors.class);

    /**
     * 线程任务执行管理类名称前缀
     */
    private final static String EXECUTOR_NAME = "SimpleTaskExecutors-";

    /**
     * 线程任务执行管理类名称
     */
    private final String tag;

    /**
     * 任务运行线程池
     */
    private ThreadPoolExecutor taskExecutors;

    /**
     * 运行中的任务集合管理
     */
    private ConcurrentMap<String, ITask> runningTaskPool;

    /**
     * 任务ID的数值记录
     */
    private AtomicInteger taskNumber = new AtomicInteger(0);

    public SimpleTaskExecutors(String tag, int corePoolSize) {
        this.tag = EXECUTOR_NAME + tag + "-";
        taskExecutors = new ThreadPoolExecutor(corePoolSize, corePoolSize, 0L, TimeUnit.SECONDS,
                new LinkedBlockingQueue<>());
        runningTaskPool = new ConcurrentHashMap<>(corePoolSize);
    }

    @Override
    public String addTask(ITask task, ITaskCallback callback) {
        if (task != null) {
            String taskId = tag + taskNumber.getAndIncrement();
            log.debug("Add task: {}", taskId);
            task.setTaskId(taskId);
            task.setTaskCallback(callback);
            taskExecutors.execute(task);
            return taskId;
        }
        return null;
    }

    @Override
    public void cancelTask(String taskId) {
        log.debug("Cancel task: {}", taskId);
        // 检查是否在等待队列中
        List<Runnable> resultList = taskExecutors.getQueue().parallelStream()
                .filter(runnable -> ((ITask) runnable).getTaskId().equals(taskId)).collect(Collectors.toList());
        // 在等待队列中移除
        resultList.forEach(e -> taskExecutors.getQueue().remove(e));

        // 检查运行队列
        if (runningTaskPool.containsKey(taskId)) {
            runningTaskPool.remove(taskId).cancel();
        }
    }
}
