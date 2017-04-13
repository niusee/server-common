/*
 * Niusee server-common
 *
 * Copyright 2015-2017 by Niusee.inc. All rights reserved.
 */
package cn.niusee.common.test.schedule;

import cn.niusee.common.schedule.QuartzSchedulingService;
import junit.framework.TestCase;

/**
 * 测试计划任务
 *
 * @author Qianliang Zhang
 */
public class TestScheduleJob extends TestCase {

    public void testScheduleJob() {
        String testName = QuartzSchedulingService.getInstance().addScheduledJob(1000,
                service -> System.out.println("test"));

        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        QuartzSchedulingService.getInstance().removeScheduledJob(testName);
    }
}
