/*
 * Niusee server-common
 *
 * Copyright 2015-2020 by Niusee.inc. All rights reserved.
 */
package cn.niusee.common.m3u8;

/**
 * 列表子选项的其他信息
 *
 * @author Qianliang Zhang
 */
public interface PlaylistInfo {
    /**
     * 获取节目ID
     *
     * @return 节目ID
     */
    int getProgramId();

    /**
     * 获取带宽设置
     *
     * @return 带宽设置
     */
    int getBandWidth();

    /**
     * 获取编码信息
     *
     * @return 编码信息
     */
    String getCodecs();
}

