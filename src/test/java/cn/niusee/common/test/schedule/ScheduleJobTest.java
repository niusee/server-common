/*
 * Niusee server-common
 *
 * Copyright 2015-2017 by Niusee.inc. All rights reserved.
 */
package cn.niusee.common.test.schedule;

import cn.niusee.common.schedule.QuartzSchedulingService;
import junit.framework.TestCase;

import java.util.Date;

/**
 * 测试计划任务
 *
 * @author Qianliang Zhang
 */
public class ScheduleJobTest extends TestCase {

    public void testScheduleJob() {
        String testName = QuartzSchedulingService.getInstance().addScheduledJob(1000,
                service -> System.out.println("testScheduleJob"));

        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        QuartzSchedulingService.getInstance().removeScheduledJob(testName);
    }

    public void testScheduledOnceJobAfterDelay() {
        String testName = QuartzSchedulingService.getInstance().addScheduledOnceJobAfterDelay(1000,
                service -> System.out.println("testScheduledOnceJobAfterDelay"));

        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        QuartzSchedulingService.getInstance().removeScheduledJob(testName);
    }

    public void testScheduledJobAfterDelay() {
        String testName = QuartzSchedulingService.getInstance().addScheduledJobAfterDelay(1000, 1000,
                service -> System.out.println("testScheduledJobAfterDelay"));

        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        QuartzSchedulingService.getInstance().removeScheduledJob(testName);
    }

    public void testScheduledOnceJobInSpecificDate() {
        long time  = System.currentTimeMillis() + 5000;
        Date date = new Date(time);
        String testName = QuartzSchedulingService.getInstance().addScheduledOnceJobInSpecificDate(date,
                service -> System.out.println("testScheduledOnceJobInSpecificDate"));

        try {
            Thread.sleep(6000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        QuartzSchedulingService.getInstance().removeScheduledJob(testName);
    }
}
