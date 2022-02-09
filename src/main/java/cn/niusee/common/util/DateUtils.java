/*
 * Niusee server-common
 *
 * Copyright 2015-2016 by Niusee.inc. All rights reserved.
 */
package cn.niusee.common.util;

import java.util.Date;

/**
 * 日期工具类
 *
 * @author Qianliang Zhang
 */
public class DateUtils {
    /**
     * 防止继承
     */
    private DateUtils() {
        throw new RuntimeException("No implements");
    }

    /**
     * 获取今天0点的时间戳
     *
     * @return 今天0点的时间戳
     */
    public static long getTodayMidnightTimestamp() {
        long time = System.currentTimeMillis();
        // 一天的时间（毫秒）
        long interval = 24 * 60 * 60 * 1000;
        long difference = 8 * 60 * 60 * 1000;
        return time / interval * interval - difference;
    }

    /**
     * 获取今天0点的日期
     *
     * @return 今天0点的日期
     */
    public static Date getTodayMidnightDate() {
        return new Date(getTodayMidnightTimestamp());
    }

    /**
     * 获取今天0点前指定时间的日期
     *
     * @param beforeTime 前指定时间
     * @return 今天0点前指定时间的日期
     */
    public static Date getTodayMidnightDateBeforeTime(long beforeTime) {
        return new Date(getTodayMidnightTimestamp() - beforeTime);
    }

    /**
     * 获取明天0点的时间戳
     *
     * @return 明天0点的时间戳
     */
    public static long geTomorrowMidnightTimestamp() {
        return getTodayMidnightTimestamp() + 24 * 60 * 60 * 1000;
    }

    /**
     * 获取明天0点的日期
     *
     * @return 明天0点的日期
     */
    public static Date getTomorrowMidnightDate() {
        return new Date(geTomorrowMidnightTimestamp());
    }

    /**
     * 获取明天0点过指定时间的日期
     *
     * @param passTime 过的时间
     * @return 明天0点过指定时间的日期
     */
    public static Date getTomorrowMidnightDatePassTimes(long passTime) {
        return new Date(geTomorrowMidnightTimestamp() + passTime);
    }
}
