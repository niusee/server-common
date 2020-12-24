/*
 * Niusee server-common
 *
 * Copyright 2015-2020 by Niusee.inc. All rights reserved.
 */
package cn.niusee.common.m3u8;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * M3U8解析的静态信息
 *
 * @author Qianliang Zhang
 */
final class M3uConstants {

    private M3uConstants() {
        throw new AssertionError("Not allowed");
    }

    /**
     * M3U标签开始标志
     */
    final static String COMMENT_PREFIX = "#";

    /**
     * M3U标签EXT标志
     */
    final static String EXT_PREFIX = "#EXT";

    /**
     * M3U8文件的开始标志，在第一行出现。
     */
    final static String EXT_M3U = "#EXTM3U";

    /**
     * 版本信息
     */
    final static String EXT_X_VERSION = "#EXT-X-VERSION";

    /**
     * EXTINF是视频的标志，包括视频的时长的等信息，格式如：#EXTINF:&lt;duration&gt;,&lt;title&gt;
     * 下一行紧跟着视频的播放地址。
     * <p/>
     * [duration]在旧版本是正数，代表视频的时长，新的版本是浮点数。
     * [title]视频的标题，可以不设定。
     */
    final static String EXT_INF = "#EXTINF";

    /**
     * EXT-X-TARGETDURATION代表了整个列表中最长时长媒体的时间长度。在M3U8文件中必须出现。
     * 格式：#EXT-X-TARGETDURATION:&lt;seconds&gt;
     */
    final static String EXT_X_TARGET_DURATION = "#EXT-X-TARGETDURATION";

    /**
     * 定义当前m3u8文件中第一个文件的序列号，每个ts文件在m3u8文件中都有固定唯一的序列号，该序列号用于在MBR时切换码率进行对齐。
     * 格式: #EXT-X-MEDIA-SEQUENCE:&lt;number&gt;
     * <p/>
     * 如果M3U列表不包含EXT-X-MEDIA-SEQUENCE标签，列表的一个播放视频媒体被认为为1。
     */
    final static String EXT_X_MEDIA_SEQUENCE = "#EXT-X-MEDIA-SEQUENCE";

    /**
     * 定义加密方式和key文件的url，用于取得16bytes的key文件解码ts文件。
     * 格式: #EXT-X-KEY:METHOD=&lt;method&gt;[,URI="&lt;URI&gt;"]
     * <p/>
     * The METHOD parameter specifies the encryption method.  The URI parameter, if present,
     * specifies how to obtain the key.
     * <p/>
     * Version 1.0 of the protocol defines two encryption methods: NONE and AES-128.  An encryption
     * method of NONE means that media files are not encrypted.
     * <p/>
     * An encryption method of AES-128 means that media files are encrypted using the Advanced Encryption
     * Standard [AES_128] with a 128-bit key and PKCS7 padding [RFC3852].
     * <p/>
     * A new EXT-X-KEY supersedes any prior EXT-X-KEY.
     * If no EXT-X-KEY tag is present then media files are not encrypted.
     */
    final static String EXT_X_KEY = "#EXT-X-KEY";

    /**
     * The EXT-X-PROGRAM-DATE-TIME tag associates the beginning of the next
     * media file with an absolute date and/or time.  The date/time
     * representation is ISO/IEC 8601:2004 [ISO_8601] and SHOULD indicate a
     * time zone.  For example:   #EXT-X-PROGRAM-DATE-TIME:&lt;YYYY-MM-DDThh:mm:ssZ&gt;
     */
    final static String EXT_X_PROGRAM_DATE_TIME = "#EXT-X-PROGRAM-DATE-TIME";

    /**
     * 是否允许客户端缓存播放内容。
     * 格式：#EXT-X-ALLOW-CACHE:&lt;YES|NO&gt;
     */
    final static String EXT_X_ALLOW_CACHE = "#EXT-X-ALLOW-CACHE";

    /**
     * The EXT-X-STREAM-INF tag indicates that the next URI in the Playlist
     * file identifies another Playlist file.  Its format is:
     * <p/>
     * #EXT-X-STREAM-INF:[attribute=value][,attribute=value]*
     * &lt;URI&gt;
     * <p/>
     * The following attributes are defined for the EXT-X-STREAM-INF tag:
     * <p/>
     * BANDWIDTH=&lt;n&gt;
     * <p/>
     * where n is an approximate upper bound of the stream bitrate,
     * expressed as a number of bits per second.
     * <p/>
     * PROGRAM-ID=&lt;i&gt;
     * <p/>
     * where i is a number that uniquely identifies a particular
     * presentation within the scope of the Playlist file.
     * <p/>
     * A Playlist file MAY contain multiple EXT-X-STREAM-INF URIs with the
     * same PROGRAM-ID to describe variant streams of the same presentation.
     */
    final static String EXT_X_STREAM_INF = "#EXT-X-STREAM-INF";

    /**
     * EXT-X-ENDLIST 表明m3u8文件的结束。live m3u8没有该tag。
     */
    final static String EXT_X_ENDLIST = "#EXT-X-ENDLIST";

    /**
     * The EXT-X-DISCONTINUITY 当遇到该tag的时候说明以下属性发生了变化：
     * <p/>
     * file format
     * <p/>
     * number and type of tracks
     * <p/>
     * encoding parameters
     * <p/>
     * encoding sequence
     * <p/>
     * timestamp sequence
     * <p/>
     * Its format is:
     * <p/>
     * #EXT-X-DISCONTINUITY
     */
    final static String EXT_X_DISCONTINUITY = "#EXT-X-DISCONTINUITY";

    /**
     * 标签信息获取正则表达式帮助类
     */
    static class Patterns {

        private Patterns() {
            throw new AssertionError();
        }

        /**
         * EXTINF标签查找正则表达式
         */
        final static Pattern EXTINF =
                Pattern.compile(tagPattern(M3uConstants.EXT_INF) + "\\s*(-1|[0-9\\.]*)\\s*(?:,((.*)))?");

        /**
         * 版本信息
         */
        final static Pattern EXT_X_VERSION =
                Pattern.compile(tagPattern(M3uConstants.EXT_X_VERSION) + "([0-9]*)");

        /**
         * 获取标签的查找正则表达式
         *
         * @param tagName 标签名称
         * @return 标签的查找正则表达式
         */
        private static String tagPattern(String tagName) {
            return "\\s*" + tagName + "\\s*:\\s*";
        }

        /**
         * 媒体加密信息标签获取正则表达式。
         * #EXT-X-KEY:METHOD=&lt;method&gt;[,URI="&lt;URI&gt;"
         * #EXT-X-KEY:METHOD=AES-128,URI="https://priv.example.com/key.php?r=52"
         */
        final static Pattern EXT_X_KEY =
                Pattern.compile(tagPattern(M3uConstants.EXT_X_KEY)
                        + "METHOD=([0-9A-Za-z\\-]*)(,URI=\"(([^\\\\\"]*.*))\")?");

        /**
         * 列表媒体最长时长标签获取正则表达式
         */
        final static Pattern EXT_X_TARGET_DURATION =
                Pattern.compile(tagPattern(M3uConstants.EXT_X_TARGET_DURATION) + "([0-9]*)");

        /**
         * 列表媒体唯一表示标签获取正则表达式
         */
        final static Pattern EXT_X_MEDIA_SEQUENCE =
                Pattern.compile(tagPattern(M3uConstants.EXT_X_MEDIA_SEQUENCE) + "([0-9]*)");

        /**
         * 列表媒体节目日期标签获取正则表达式。YYY-MM-DDThh:mm:ss
         */
        final static Pattern EXT_X_PROGRAM_DATE_TIME =
                Pattern.compile(tagPattern(M3uConstants.EXT_X_PROGRAM_DATE_TIME) + "(.*)");

        /**
         * EXT_X_PROGRAM_DATE_TIME的时间转换
         *
         * @param line       EXT_X_PROGRAM_DATE_TIME对应的解析信息
         * @param lineNumber lEXT_X_PROGRAM_DATE_TIME对应的解析信息行数
         * @return 时间戳时间
         * @throws M3u8ParseException 解析错误
         */
        static long toDate(String line, int lineNumber) throws M3u8ParseException {
            Matcher matcher = Patterns.EXT_X_PROGRAM_DATE_TIME.matcher(line);

            if (!matcher.find() || !matcher.matches() || matcher.groupCount() < 1) {
                throw new M3u8ParseException(line, lineNumber, " must specify date-time");
            }

            SimpleDateFormat iso8601Format = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssZ");
            System.out.println(iso8601Format.format(new Date()));
            String dateTime = matcher.group(1);
            try {
                return iso8601Format.parse(dateTime).getTime();
            } catch (java.text.ParseException e) {
                throw new M3u8ParseException(line, lineNumber, e);
            }
        }
    }
}
