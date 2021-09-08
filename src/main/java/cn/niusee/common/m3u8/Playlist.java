/*
 * Niusee server-common
 *
 * Copyright 2015-2020 by Niusee.inc. All rights reserved.
 */
package cn.niusee.common.m3u8;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.StringReader;
import java.nio.channels.Channels;
import java.nio.channels.ReadableByteChannel;
import java.util.Iterator;
import java.util.List;

/**
 * M3U8列表实现类
 *
 * @author Qianliang Zhang
 */
public final class Playlist implements Iterable<Element> {

    /**
     * 解析M3U列表信息
     *
     * @param readable 需解析的输入信息
     * @return 解析结果
     * @throws M3u8ParseException 输入信息为空抛出错误
     */
    public static Playlist parse(Readable readable) throws M3u8ParseException {
        if (readable == null) {
            throw new NullPointerException("playlist");
        }
        return PlaylistParser.create(PlaylistType.M3U8).parse(readable);
    }

    /**
     * 解析M3U列表信息
     *
     * @param playlist 需解析的输入信息
     * @return 解析结果
     * @throws M3u8ParseException 输入信息为空抛出错误
     */
    public static Playlist parse(String playlist) throws M3u8ParseException {
        if (playlist == null) {
            throw new NullPointerException("playlist");
        }
        return parse(new StringReader(playlist));
    }

    /**
     * 解析M3U列表信息
     *
     * @param playlist 需解析的输入信息
     * @return 解析结果
     * @throws M3u8ParseException 输入信息为空抛出错误
     */
    public static Playlist parse(InputStream playlist) throws M3u8ParseException {
        if (playlist == null) {
            throw new NullPointerException("playlist");
        }
        return parse(new InputStreamReader(playlist));
    }

    /**
     * 解析M3U列表信息
     *
     * @param playlist 需解析的输入信息
     * @return 解析结果
     * @throws M3u8ParseException 输入信息为空抛出错误
     */
    public static Playlist parse(ReadableByteChannel playlist) throws M3u8ParseException {
        if (playlist == null) {
            throw new NullPointerException("playlist");
        }
        return parse(Channels.newReader(playlist,
                java.nio.charset.Charset.defaultCharset().name()));
    }

    /**
     * 列表的子内容
     */
    private final List<Element> elements;

    /**
     * 版本信息
     */
    private final int version;

    /**
     * 媒体最大的播放时长
     */
    private int targetDuration;

    /**
     * 媒体的序列号
     */
    private final int mediaSequenceNumber;

    /**
     * 是否设置了结束标志
     */
    private final boolean endSet;

    public Playlist(List<Element> elements, boolean endSet, int version, int targetDuration,
                    int mediaSequenceNumber) {
        if (elements == null) {
            throw new NullPointerException("elements");
        }
        this.targetDuration = targetDuration;
        this.elements = elements;
        this.endSet = endSet;
        this.version = version;
        this.mediaSequenceNumber = mediaSequenceNumber;
    }

    /**
     * 获取媒体最大的播放时长
     *
     * @return 媒体最大的播放时长
     */
    public int getTargetDuration() {
        return targetDuration;
    }

    /**
     * 列表的子内容递归
     *
     * @return 列表的子内容递归
     */
    public Iterator<Element> iterator() {
        return elements.iterator();
    }

    /**
     * 获取列表的子内容列表
     *
     * @return 列表的子内容列表
     */
    public List<Element> getElements() {
        return elements;
    }

    /**
     * 获取是否设置了结束标志
     *
     * @return 是否设置了结束标志
     */
    public boolean isEndSet() {
        return endSet;
    }

    /**
     * 获取版本信息
     * ø
     *
     * @return 版本信息
     */
    public int getVersion() {
        return version;
    }

    /**
     * 获取媒体的序列号
     *
     * @return 媒体的序列号
     */
    public int getMediaSequenceNumber() {
        return mediaSequenceNumber;
    }

    /**
     * 整理数据
     */
    public void fixTargetDuration() {
        double realTargetDuration = -1;
        for (Element element : elements) {
            if (element.getExactDuration() > realTargetDuration) {
                realTargetDuration = element.getExactDuration();
            }
        }
        if (targetDuration < realTargetDuration) {
            targetDuration = (int) Math.ceil(realTargetDuration);
        }
    }

    @Override
    public String toString() {
        return "PlayListImpl{" +
                "elements=" + elements +
                ", version=" + version +
                ", endSet=" + endSet +
                ", targetDuration=" + targetDuration +
                ", mediaSequenceNumber=" + mediaSequenceNumber +
                '}';
    }

    /**
     * 生成M3u8内容
     *
     * @return M3U8文件内容
     */
    public String toM3u8() {
        StringBuilder sb = new StringBuilder(M3uConstants.EXT_M3U).append("\r\n");
        if (version > 0) {
            sb.append(M3uConstants.EXT_X_VERSION).append(":").append(version).append("\r\n");
        }
        sb.append(M3uConstants.EXT_X_MEDIA_SEQUENCE).append(":").append(mediaSequenceNumber).append("\r\n");
        sb.append(M3uConstants.EXT_X_TARGET_DURATION).append(":").append(targetDuration).append("\r\n");
        for (Element element : elements) {
            sb.append(element.toM3U8()).append("\r\n");
        }
        if (endSet) {
            sb.append(M3uConstants.EXT_X_ENDLIST);
        }
        return sb.toString();
    }
}
