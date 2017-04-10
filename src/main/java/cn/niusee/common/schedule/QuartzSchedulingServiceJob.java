/*
 * Niusee server-common
 *
 * Copyright 2015-2016 by Niusee.inc. All rights reserved.
 */

package cn.niusee.common.schedule;

import cn.niusee.common.logger.LoggerHelper;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

/**
 * 计划任务, 绑定计划服务
 *
 * @author Qianliang Zhang
 */
public class QuartzSchedulingServiceJob implements Job {
    /**
     * 计划服务名称
     */
    public static final String SCHEDULING_SERVICE = "scheduling_service";

    /**
     * 计划名称
     */
    public static final String SCHEDULED_JOB = "scheduled_job";

    /**
     * 日记
     */
    private LoggerHelper log = new LoggerHelper(QuartzSchedulingServiceJob.class);

    @Override
    public void execute(JobExecutionContext context) throws JobExecutionException {
        ISchedulingService service = (ISchedulingService) context.getJobDetail().getJobDataMap().get(SCHEDULING_SERVICE);
        IScheduledJob job = (IScheduledJob) context.getJobDetail().getJobDataMap().get(SCHEDULED_JOB);
        try {
            job.execute(service);
        } catch (Throwable e) {
            log.error("Job {} execution failed", job.toString(), e);
        }
    }
}
