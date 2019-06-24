/*
 * Niusee server-common
 *
 * Copyright 2015-2016 by Niusee.inc. All rights reserved.
 */
package cn.niusee.common.schedule;

import cn.niusee.common.logger.LoggerHelper;
import org.quartz.*;
import org.quartz.impl.StdSchedulerFactory;

import java.io.File;
import java.util.Date;
import java.util.concurrent.atomic.AtomicLong;

import static org.quartz.JobBuilder.newJob;
import static org.quartz.SimpleScheduleBuilder.simpleSchedule;
import static org.quartz.TriggerBuilder.newTrigger;

/**
 * 计划任务执行服务类
 *
 * @author Qianliang Zhang
 */
public class QuartzSchedulingService implements ISchedulingService {

    /**
     * 日记
     */
    private static LoggerHelper log = new LoggerHelper(QuartzSchedulingService.class);

    /**
     * 组任务名称
     */
    private static final String GROUP_NAME = "NIUSEE_SCHEDULE";

    /**
     * 周期服务类
     */
    private Scheduler scheduler;

    /**
     * 任务计数器
     */
    private AtomicLong jobDetailCounter = new AtomicLong(0);

    /**
     * 初始化计划服务单例
     */
    private static QuartzSchedulingService instance = new QuartzSchedulingService();

    /**
     * 获取计划服务单例
     *
     * @return 计划服务单例
     */
    public static QuartzSchedulingService getInstance() {
        return instance;
    }

    private QuartzSchedulingService() {
        try {
            // 计划任务服务工厂类
            SchedulerFactory factory = null;
            // 配置文件
            String confPath = "conf/quartz.properties";
            File conf = new File(confPath);
            if (conf.exists()) {
                log.info("found quartz.properties");
                factory = new StdSchedulerFactory(confPath);
            }
            // 没有配置文件的工厂类，使用默认的配置工厂类
            if (factory == null) {
                factory = new StdSchedulerFactory();
            }
            scheduler = factory.getScheduler();
            if (scheduler != null) {
                scheduler.start();
            } else {
                log.error("Scheduler was not started");
            }
        } catch (SchedulerException e) {
            e.printStackTrace();
        }
    }

    /**
     * 分配计划任务名称
     *
     * @return 计划任务名称
     */
    private String distributeJobName() {
        // 分配名称，Index自增
        return "ScheduledJob_" + jobDetailCounter.getAndIncrement();
    }

    /**
     * 添加执行计划任务
     *
     * @param job     计划任务
     * @param trigger 计划任务触发器
     */
    private void doScheduleJob(String name, Trigger trigger, IScheduledJob job) {
        if (scheduler != null) {
            log.debug("add schedule job: {}", name);
            try {
                scheduler.scheduleJob(buildJob(name, job), trigger);
            } catch (SchedulerException ex) {
                throw new RuntimeException(ex);
            }
        } else {
            log.warn("No scheduler is available");
        }
    }

    /**
     * 创建计划任务
     *
     * @param name 计划工作名称
     * @return 计划任务
     */
    private JobDetail buildJob(String name, IScheduledJob job) {
        JobDetail jobDetail = newJob(QuartzSchedulingServiceJob.class)
                .withIdentity(name, GROUP_NAME)
                .build();
        jobDetail.getJobDataMap().put(QuartzSchedulingServiceJob.SCHEDULING_SERVICE, this);
        jobDetail.getJobDataMap().put(QuartzSchedulingServiceJob.SCHEDULED_JOB, job);
        return jobDetail;
    }

    @Override
    public String addScheduledJob(long interval, IScheduledJob job) {
        // 分配名称
        String name = distributeJobName();
        Trigger trigger = newTrigger()
                .withIdentity(name, GROUP_NAME)
                .startNow()
                .withSchedule(simpleSchedule()
                        .withIntervalInMilliseconds(interval)
                        .repeatForever())
                .build();
        doScheduleJob(name, trigger, job);
        return name;
    }

    @Override
    public String addScheduledOnceJobAfterDelay(long delay, IScheduledJob job) {
        String name = distributeJobName();
        Trigger trigger = newTrigger()
                .withIdentity(name, GROUP_NAME)
                .startAt(new Date(System.currentTimeMillis() + delay))
                .withSchedule(simpleSchedule()
                        .withIntervalInSeconds(1)
                        .withRepeatCount(0))
                .build();
        doScheduleJob(name, trigger, job);
        return name;
    }

    @Override
    public String addScheduledOnceJobInSpecificDate(Date date, IScheduledJob job) {
        String name = distributeJobName();
        Trigger trigger = newTrigger()
                .withIdentity(name, GROUP_NAME)
                .startAt(date)
                .withSchedule(simpleSchedule()
                        .withIntervalInSeconds(1)
                        .withRepeatCount(0))
                .build();
        doScheduleJob(name, trigger, job);
        return name;
    }

    @Override
    public String addScheduledJobAfterDelay(long interval, long delay, IScheduledJob job) {
        String name = distributeJobName();
        Trigger trigger = newTrigger()
                .withIdentity(name, GROUP_NAME)
                .startAt(new Date(System.currentTimeMillis() + delay))
                .withSchedule(simpleSchedule()
                        .withIntervalInMilliseconds(interval)
                        .repeatForever())
                .build();
        doScheduleJob(name, trigger, job);
        return name;
    }

    @Override
    public void pauseScheduledJob(String name) {
        if (scheduler != null) {
            log.debug("pause schedule job: {}", name);
            try {
                scheduler.pauseJob(JobKey.jobKey(name, GROUP_NAME));
            } catch (SchedulerException e) {
                throw new RuntimeException(e);
            }
        } else {
            log.warn("No scheduler is available");
        }
    }

    @Override
    public void resumeScheduledJob(String name) {
        if (scheduler != null) {
            log.debug("resume schedule job: {}", name);
            try {
                scheduler.resumeJob(JobKey.jobKey(name, GROUP_NAME));
            } catch (SchedulerException e) {
                throw new RuntimeException(e);
            }
        } else {
            log.warn("No scheduler is available");
        }
    }

    @Override
    public void removeScheduledJob(String name) {
        if (scheduler != null) {
            log.debug("remove schedule job: {}", name);
            try {
                scheduler.deleteJob(JobKey.jobKey(name, GROUP_NAME));
            } catch (SchedulerException e) {
                throw new RuntimeException(e);
            }
        } else {
            log.warn("No scheduler is available");
        }
    }

    /**
     * 结束服务
     *
     * @throws Exception 结束服务出错时抛出错误
     */
    public void destroy() throws Exception {
        if (scheduler != null) {
            scheduler.shutdown();
        }
    }
}
