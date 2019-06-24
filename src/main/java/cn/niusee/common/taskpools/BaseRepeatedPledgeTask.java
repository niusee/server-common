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
public abstract class BaseRepeatedPledgeTask implements ITask {

    /**
     * 限定次数。-1代表不限定次数
     */
    private final int limitRepeatedTimes;

    /**
     * 任务是否被取消
     */
    private boolean canceled = false;

    public BaseRepeatedPledgeTask(int limitRepeatedTimes) {
        this.limitRepeatedTimes = limitRepeatedTimes;
    }

    @Override
    public void cancel() {
        canceled = true;
    }

    @Override
    public boolean run() {
        int currentRunningTime = 0;
        boolean success = false;
        while (!success && !canceled && (limitRepeatedTimes != -1 && currentRunningTime < limitRepeatedTimes)) {
            success = doJob();
            currentRunningTime++;
        }
        return success;
    }

    /**
     * 工作
     *
     * @return 工作的结果
     */
    public abstract boolean doJob();
}
