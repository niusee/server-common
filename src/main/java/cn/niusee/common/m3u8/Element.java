/*
 * Niusee server-common
 *
 * Copyright 2015-2020 by Niusee.inc. All rights reserved.
 */
package cn.niusee.common.m3u8;

/**
 * M3U8的基本结构定义
 *
 * @author Qianliang Zhang
 */
public interface Element {

    /**
     * 获取M3U8列表的标题信息
     *
     * @return M3U8列表的标题信息
     */
    String getTitle();

    /**
     * 获取子视频的最接近的正数时长。兼容旧版本的m3u8草稿。
     *
     * @return 子视频的最接近的正数时长
     */
    int getDuration();

    /**
     * 获取子视频的时长（浮点数），对应新版本的m3u8草稿。
     *
     * @return 获取子视频的时长
     */
    double getExactDuration();

    /**
     * 视频媒体或者M3U8列表的播放地址
     *
     * @return 播放地址
     */
    String getURI();

    /**
     * 视频媒体是否加密
     *
     * @return 加密返回true，否则false
     */
    boolean isEncrypted();

    /**
     * 设置结构的中断状态
     *
     * @param discontinuity 结构的中断状态
     */
    void setDiscontinuity(boolean discontinuity);

    /**
     * 该结构前面是否中断。为true时，表明文件格式或者视频编码等信息发生变化
     *
     * @return 前面是否中断
     */
    boolean isDiscontinuity();

    /**
     * 获取媒体视频的加密信息
     *
     * @return 媒体视频的加密信息，如果非加密返回null。
     */
    EncryptionInfo getEncryptionInfo();

    /**
     * 获取M3U8的内容
     *
     * @return M3U8的内容
     */
    String toM3U8();
}
