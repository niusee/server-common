/*
 * Niusee vod-server
 *
 * Copyright 2015-2017 by Niusee.inc. All rights reserved.
 */
package cn.niusee.common.util;

/**
 * 时间工具类
 *
 * @author Qianliang Zhang
 */
public class TimeUtils {

    private TimeUtils() {
        throw new RuntimeException("No implements");
    }

    /**
     * 获取当前的Unix时间戳
     *
     * @return 当前的Unix时间戳
     */
    public static long getCurrentUnixTime() {
        return System.currentTimeMillis() / 1000;
    }

}
