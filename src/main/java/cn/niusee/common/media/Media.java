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
         * 获取流索引
         *
         * @return 流索引
         */
        public int getIndex() {
            return index;
        }

        /**
         * 获取编码格式名称
         *
         * @return 编码格式名称
         */
        public String getCodecName() {
            return codec_name;
        }

        /**
         * 获取流类型
         *
         * @return 流类型
         */
        public String getCodecType() {
            return codec_type;
        }

        /**
         * 获取视频流的宽
         *
         * @return 视频流的宽
         */
        public int getWidth() {
            return width;
        }

        /**
         * 获取视频流的高
         *
         * @return 视频流的高
         */
        public int getHeight() {
            return height;
        }

        /**
         * 获取视频流帧的格式
         *
         * @return 视频流帧的格式
         */
        public String getFixFormat() {
            return fix_format;
        }

        /**
         * 获取视频流帧率
         *
         * @return 视频流帧率
         */
        public String getFrameRate() {
            return r_frame_rate;
        }

        /**
         * 获取视频流平均帧率
         *
         * @return 视频流平均帧率
         */
        public String getAvgFrameRate() {
            return avg_frame_rate;
        }

        /**
         * 获取视频流码率
         *
         * @return 视频流码率
         */
        public int getBitRate() {
            return bit_rate;
        }

        /**
         * 获取音频流采样率
         *
         * @return 音频流采样率
         */
        public int getSampleRate() {
            return sample_rate;
        }

        /**
         * 获取音频流通道数
         *
         * @return 音频流通道数
         */
        public int getChannels() {
            return channels;
        }

        /**
         * 获取流时长(单位为秒)
         *
         * @return 流时长（秒）
         */
        public double getDurationInSeconds() {
            return duration;
        }

        /**
         * 获取流时长(单位为毫秒)
         *
         * @return 流时长（毫秒）
         */
        public long getDurationInMilliseconds() {
            return (long) (duration * 1000);
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

        /**
         * 获取媒体格式名称
         *
         * @return 媒体格式名称
         */
        public String getFormatName() {
            return format_name;
        }

        /**
         * 获取媒体时长(单位为秒)
         *
         * @return 媒体时长（秒）
         */
        public double getDurationInSeconds() {
            return duration;
        }

        /**
         * 获取媒体时长(单位为毫秒)
         *
         * @return 媒体时长（毫秒）
         */
        public long getDurationInMilliseconds() {
            return (long) (duration * 1000);
        }

        /**
         * 获取媒体码率
         *
         * @return 媒体码率
         */
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

    /**
     * 获取媒体格式信息
     *
     * @return 媒体格式信息
     */
    public Format getFormat() {
        return format;
    }

    /**
     * 获取媒体流信息
     *
     * @return 媒体流信息
     */
    public List<Stream> getStreams() {
        return streams;
    }

    /**
     * 获取媒体中的第一个视频流信息
     *
     * @return 视频流信息
     */
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

    /**
     * 获取媒体中第一个音频流信息
     *
     * @return 音频流信息
     */
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
