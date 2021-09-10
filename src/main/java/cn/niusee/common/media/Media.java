/*
 * Niusee server-common
 *
 * Copyright 2015-2017 by Niusee.inc. All rights reserved.
 */
package cn.niusee.common.media;

import com.google.gson.Gson;

import java.util.List;

/**
 * 媒体信息类。包括音视频信息、各式信息
 *
 * @author Qianliang Zhang
 */
public class Media {
    /**
     * 音视频流的信息
     */
    public static class Stream {
        /**
         * 流在媒体中的索引
         */
        private int index;

        /**
         * 编码格式名称,如:h264、aac
         */
        private String codec_name;

        /**
         * 流类型,如包括:video、audio、subtitle、data
         */
        private String codec_type;

        /**
         * 编码文件，如：Baseline、Main、High
         */
        private String profile;

        /**
         * 图像格式，如：yuv420p、yuvj420p
         */
        private String pix_fmt;

        /**
         * 视频流的宽(只包含在流类型在video中)
         */
        private int width;

        /**
         * 视频流的高(只包含在流类型在video中)
         */
        private int height;

        /**
         * 视频流帧格式(只包含在流类型在video中),如:yuv420p
         */
        private String fix_format;

        /**
         * 视频流帧率(只包含在流类型在video中),主要指编码时设定的数值
         * NOTE:这里用字符串,防止出现3000/1001,25/1等数据格式
         */
        private String r_frame_rate;

        /**
         * 视频流平均帧率(只包含在流类型在video中)
         * NOTE:这里用字符串,防止出现3000/1001,25/1等数据格式
         */
        private String avg_frame_rate;

        /**
         * 流码率,单位为b/s
         */
        private int bit_rate;

        /**
         * 音频流采样率(只包含在流类型在audio中)
         */
        private int sample_rate;

        /**
         * 音频流通道数(只包含在流类型在audio中)
         */
        private int channels;

        /**
         * 流的时长
         */
        private double duration;

        /**
         * 视频的扫描方式。progressive：逐行扫描。‘tt’、‘bb’、‘tb’、‘bt’：隔行扫描
         */
        private String field_order;

        public int getIndex() {
            return index;
        }

        public String getCodecName() {
            return codec_name;
        }

        public String getCodecType() {
            return codec_type;
        }

        public String getProfile() {
            return profile;
        }

        public String getPixFmt() {
            return pix_fmt;
        }

        public int getWidth() {
            return width;
        }

        public int getHeight() {
            return height;
        }

        public String getFixFormat() {
            return fix_format;
        }

        public String getFrameRate() {
            return r_frame_rate;
        }

        public String getAvgFrameRate() {
            return avg_frame_rate;
        }

        public int getBitRate() {
            return bit_rate;
        }

        public int getSampleRate() {
            return sample_rate;
        }

        public int getChannels() {
            return channels;
        }

        public double getDurationInSeconds() {
            return duration;
        }

        public long getDurationInMilliseconds() {
            return (long) (duration * 1000);
        }

        public String getFieldOrder() {
            return field_order;
        }

        public boolean isProgressiveVideo() {
            return "progressive".equals(field_order);
        }

        public boolean isInterlacedVideo() {
            return "tt".equals(field_order) || "bb".equals(field_order) || "tb".equals(field_order)
                    || "bt".equals(field_order);
        }

        @Override
        public String toString() {
            Gson gson = new Gson();
            return gson.toJson(this);
        }
    }

    /**
     * 媒体格式信息
     */
    public static class Format {
        /**
         * 媒体格式名称
         */
        private String format_name;

        /**
         * 媒体文件的时长(单位为秒,包含有小数点)
         */
        private double duration;

        /**
         * 媒体文件的码率(单位为b/s)
         */
        private int bit_rate;

        public String getFormatName() {
            return format_name;
        }

        public double getDurationInSeconds() {
            return duration;
        }

        public long getDurationInMilliseconds() {
            return (long) (duration * 1000);
        }

        public int getBitRate() {
            return bit_rate;
        }

        @Override
        public String toString() {
            Gson gson = new Gson();
            return gson.toJson(this);
        }
    }

    /**
     * 媒体格式信息
     */
    private Format format;

    /**
     * 媒体流信息
     */
    private List<Stream> streams;

    public Format getFormat() {
        return format;
    }

    public List<Stream> getStreams() {
        return streams;
    }

    public Stream getFirstVideoStream() {
        if (streams != null) {
            for (Stream stream : streams) {
                if ("video".equals(stream.codec_type)) {
                    return stream;
                }
            }
        }
        return null;
    }

    public Stream getFirstAudioStream() {
        if (streams != null) {
            for (Stream stream : streams) {
                if ("audio".equals(stream.codec_type)) {
                    return stream;
                }
            }
        }
        return null;
    }

    @Override
    public String toString() {
        Gson gson = new Gson();
        return gson.toJson(this);
    }
}
