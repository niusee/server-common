/*
 * Niusee server-common
 *
 * Copyright 2015-2016 by Niusee.inc. All rights reserved.
 */
package cn.niusee.common.media;

import cn.niusee.common.utils.NumberUtils;

import java.util.List;

/**
 * 简易的媒体文件信息封装类。从Media信息中分析出平常简易的媒体信息。
 *
 * @author Qianliang Zhang
 */
public class SimpleMediaInfo {
    /**
     * 媒体信息类
     */
    private final Media media;

    /**
     * 简易媒体文件信息构造函数
     *
     * @param media 媒体信息类
     */
    public SimpleMediaInfo(Media media) {
        if (media == null) {
            throw new IllegalArgumentException("media can not be null");
        }
        this.media = media;
    }

    /**
     * 判断媒体文件是否有效
     *
     * @return 媒体文件是否有效
     */
    public boolean isMediaValid() {
        // 如果包含流数据,则认为是有效的
        List<Media.Stream> streams = media.getStreams();
        return streams != null && !streams.isEmpty();
    }

    /**
     * 判断媒体文件是否包含视频流
     *
     * @return 媒体文件是否包含视频流
     */
    public boolean hasVideo() {
        return media.getFirstVideoStream() != null;
    }

    /**
     * 判断媒体文件是否包含音频流
     *
     * @return 媒体文件是否包含音频流
     */
    public boolean hasAudio() {
        return media.getFirstAudioStream() != null;
    }

    /**
     * 获取媒体格式
     *
     * @return 媒体格式
     */
    public String getFormat() {
        Media.Format format = media.getFormat();
        return format == null ? null : format.getFormatName();
    }

    /**
     * 获取媒体的长度，单位秒
     *
     * @return 媒体的长度
     */
    public double getDurationInSeconds() {
        Media.Format format = media.getFormat();
        // 保留小数点2位
        return format == null ? 0 : Double.parseDouble(String.format("%.2f", format.getDurationInSeconds()));
    }

    /**
     * 获取媒体的长度，单位毫秒
     *
     * @return 媒体的长度
     */
    public long getDurationInMilliseconds() {
        Media.Format format = media.getFormat();
        return format == null ? 0 : format.getDurationInMilliseconds();
    }

    /**
     * 获取媒体的码率
     *
     * @return 媒体的码率
     */
    public int getBitrate() {
        Media.Format format = media.getFormat();
        return format == null ? 0 : format.getBitRate();
    }

    /**
     * 获取视频的编码格式
     *
     * @return 视频的编码格式
     */
    public String getVideoCodec() {
        Media.Stream s = media.getFirstVideoStream();
        return s == null ? null : s.getCodecName();
    }

    /**
     * 获取视频的编码Profile
     *
     * @return 视频的编码Profile
     */
    public String getVideoProfile() {
        Media.Stream s = media.getFirstVideoStream();
        return s == null ? null : s.getProfile();
    }

    /**
     * 获取视频的编码pix_fmt
     *
     * @return 视频的编码pix_fmt
     */
    public String getVideoPixFmt() {
        Media.Stream s = media.getFirstVideoStream();
        return s == null ? null : s.getPixFmt();
    }

    /**
     * 获取视频的时长
     *
     * @return 视频的时长
     */
    public long getVideoDuration() {
        Media.Stream s = media.getFirstVideoStream();
        return s == null ? 0 : s.getDurationInMilliseconds();
    }

    /**
     * 获取视频的尺寸宽度
     *
     * @return 视频的尺寸宽度
     */
    public int getWidth() {
        Media.Stream s = media.getFirstVideoStream();
        return s == null ? 0 : s.getWidth();
    }

    /**
     * 获取视频的尺寸高度
     *
     * @return 视频的尺寸高度
     */
    public int getHeight() {
        Media.Stream s = media.getFirstVideoStream();
        return s == null ? 0 : s.getHeight();
    }

    /**
     * 获取视频的尺寸
     *
     * @return 视频的尺寸
     */
    public String getSize() {
        return getWidth() + "x" + getHeight();
    }

    /**
     * 获取视频的码率
     *
     * @return 视频的码率
     */
    public int getVideoBitrate() {
        Media.Stream s = media.getFirstVideoStream();
        return s == null ? -1 : s.getBitRate();
    }

    /**
     * 获取视频的帧率
     *
     * @return 视频的帧率
     */
    public String getFps() {
        Media.Stream s = media.getFirstVideoStream();
        return s == null ? null : s.getFrameRate();
    }

    /**
     * 获取视频的帧率（浮点数模式）
     *
     * @return 视频的帧率（浮点数模式）
     */
    public double getFpsInDouble() {
        return formatFloatStr2Double(getFps());
    }

    /**
     * 获取视频的平均帧率
     *
     * @return 视频的平均帧率
     */
    public String getAvgFps() {
        Media.Stream s = media.getFirstVideoStream();
        return s == null ? null : s.getAvgFrameRate();
    }

    /**
     * 获取视频的平均帧率（浮点数模式）
     *
     * @return 视频的平均帧率（浮点数模式）
     */
    public double getAvgFpsInDouble() {
        return formatFloatStr2Double(getAvgFps());
    }

    /**
     * 是否是逐行扫描的视频
     *
     * @return 是否是逐行扫描的视频
     */
    public boolean isProgressiveVideo() {
        Media.Stream s = media.getFirstVideoStream();
        return s != null && s.isProgressiveVideo();
    }

    /**
     * 获取音频的编码格式
     *
     * @return 音频的编码格式
     */
    public String getAudioCodec() {
        Media.Stream s = media.getFirstAudioStream();
        return s == null ? null : s.getCodecName();
    }

    /**
     * 获取音频的时长
     *
     * @return 音频的时长
     */
    public long getAudioDuration() {
        Media.Stream s = media.getFirstAudioStream();
        return s == null ? 0 : s.getDurationInMilliseconds();
    }

    /**
     * 获取音频的采样率.
     *
     * @return 音频的采样率
     */
    public int getSampleRate() {
        Media.Stream s = media.getFirstAudioStream();
        return s == null ? 0 : s.getSampleRate();
    }

    /**
     * 获取音频的通道数
     *
     * @return 音频的通道数
     */
    public int getChannels() {
        Media.Stream s = media.getFirstAudioStream();
        return s == null ? 0 : s.getChannels();
    }

    /**
     * 获取音频的码率
     *
     * @return 音频的码率
     */
    public int getAudioBitrate() {
        Media.Stream s = media.getFirstAudioStream();
        return s == null ? 0 : s.getBitRate();
    }

    @Override
    public String toString() {
        if (isMediaValid()) {
            StringBuilder sb = new StringBuilder("{");
            sb.append("format={").append("format_name=").append(getFormat()).append(", duration=").
                    append(getDurationInMilliseconds()).append(", bit_rate=").append(getBitrate()).append("}");
            // 视频
            if (hasVideo()) {
                sb.append(", video={").append("codec_name=").append(getVideoCodec()).append(", profile=").
                        append(getVideoProfile()).append(", pix_fmt=").append(getVideoPixFmt()).append(", size=").
                        append(getSize()).append(", fps=").append(getAvgFps()).append(", bit_rate=").
                        append(getVideoBitrate()).append("}");
            }
            // 音频
            if (hasAudio()) {
                sb.append(", audio={").append("codec_name=").append(getAudioCodec()).append(", sample_rate=").
                        append(getSampleRate()).append(", channels=").append(getChannels()).append(", bit_rate=").
                        append(getAudioBitrate()).append("}");
            }
            return sb.toString();
        }
        return null;
    }

    /**
     * 格式化数字字符串为浮点数字
     *
     * @param floatStr 数字字符串
     * @return 浮点数字
     */
    private double formatFloatStr2Double(String floatStr) {
        if (floatStr == null) {
            return 0;
        }
        double realFps;
        if (NumberUtils.isDouble(floatStr)) {
            // 数字形式
            realFps = Double.parseDouble(floatStr);
        } else {
            // 25/1的形式
            try {
                String[] fpsNumbers = floatStr.split("/");
                realFps = Double.parseDouble(fpsNumbers[0]) / Double.parseDouble(fpsNumbers[1]);
            } catch (Exception e) {
                return 0;
            }
        }
        // 保留小数点2位
        return Double.parseDouble(String.format("%.2f", realFps));
    }
}
