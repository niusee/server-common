/*
 * Niusee server-common
 *
 * Copyright 2015-2017 by Niusee.inc. All rights reserved.
 */
package cn.niusee.common.test.schedule;

import cn.niusee.common.schedule.QuartzSchedulingService;
import junit.framework.TestCase;

import java.time.LocalDateTime;
import java.util.Date;

/**
 * 测试计划任务
 *
 * @author Qianliang Zhang
 */
public class ScheduleJobTest extends TestCase {

    public void setUp() {
        QuartzSchedulingService.getInstance();
    }

    public void testScheduleJob() {
        System.out.println("before testScheduleJob: " + LocalDateTime.now());
        String testName = QuartzSchedulingService.getInstance().addScheduledJob(1000,
                service -> System.out.println("testScheduleJob: " + LocalDateTime.now()));

        try {
            Thread.sleep(30000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        System.out.println("after testScheduleJob: " + LocalDateTime.now());
        QuartzSchedulingService.getInstance().removeScheduledJob(testName);

        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void testScheduledOnceJobAfterDelay() {
        System.out.println("before testScheduledOnceJobAfterDelay: " + LocalDateTime.now());
        String testName = QuartzSchedulingService.getInstance().addScheduledOnceJobAfterDelay(1000,
                service -> System.out.println("testScheduledOnceJobAfterDelay :" + LocalDateTime.now()));

        try {
            Thread.sleep(30000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        System.out.println("after testScheduledOnceJobAfterDelay: " + LocalDateTime.now());
        QuartzSchedulingService.getInstance().removeScheduledJob(testName);

        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void testScheduledJobAfterDelay() {
        System.out.println("before testScheduledJobAfterDelay: " + LocalDateTime.now());
        String testName = QuartzSchedulingService.getInstance().addScheduledJobAfterDelay(1000, 1000,
                service -> System.out.println("testScheduledJobAfterDelay: " + LocalDateTime.now()));

        try {
            Thread.sleep(30000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        System.out.println("after testScheduledJobAfterDelay: " + LocalDateTime.now());
        QuartzSchedulingService.getInstance().removeScheduledJob(testName);

        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void testScheduledOnceJobInSpecificDate() {
        System.out.println("before testScheduledOnceJobInSpecificDate: " + LocalDateTime.now());
        long time  = System.currentTimeMillis() + 5000;
        Date date = new Date(time);
        String testName = QuartzSchedulingService.getInstance().addScheduledOnceJobInSpecificDate(date,
                service -> System.out.println("testScheduledOnceJobInSpecificDate: " + LocalDateTime.now()));

        try {
            Thread.sleep(30000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        System.out.println("after testScheduledOnceJobInSpecificDate: " + LocalDateTime.now());
        QuartzSchedulingService.getInstance().removeScheduledJob(testName);

        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
