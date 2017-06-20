/*
 * Niusee server-common
 *
 * Copyright 2015-2017 by Niusee.inc. All rights reserved.
 */
package cn.niusee.common.taskpools;

/**
 * 循环一定次数的任务类。注意：需要长时间运行的任务不要使用此类
 *
 * @author Qianliang Zhang
 */
public abstract class CyclesTimeTask extends BaseTask {
    /**
     * 限定次数。-1代表不限定次数
     */
    private int limitTimes;

    /**
     * 任务是否被取消
     */
    private boolean canceled = false;

    public CyclesTimeTask(int limitTimes) {
        this.limitTimes = limitTimes;
    }

    @Override
    public void cancel() {
        canceled = true;
    }

    @Override
    public void run() {
        if (taskCallback != null) {
            taskCallback.onTaskStart(this);
        }
        int currentRunningTime = 0;
        boolean success = false;
        while (!success && !canceled && (limitTimes != -1 && currentRunningTime < limitTimes)) {
            success = doJob();
            limitTimes++;
        }

        if (success) {
            if (taskCallback != null) {
                taskCallback.onTaskComplete(this);
            }
        } else {
            if (taskCallback != null) {
                taskCallback.onTaskError(this);
            }
        }
    }

    /**
     * 工作
     *
     * @return 工作的结果
     */
    public abstract boolean doJob();
}
