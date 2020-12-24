/*
 * Niusee server-common
 *
 * Copyright 2015-2020 by Niusee.inc. All rights reserved.
 */
package cn.niusee.common.m3u8;

/**
 * M3U8播放列表类型
 *
 * @author Qianliang Zhang
 */
public enum PlaylistType {
    /**
     * M3U8类型
     */
    M3U8("UTF-8", "application/vnd.apple.mpegurl", "m3u8"),

    /**
     * M3U类型
     */
    M3U("US-ASCII", "audio/mpegurl", "m3u");

    /**
     * 编码格式
     */
    final String encoding;

    /**
     * 内容类型
     */
    final String contentType;

    /**
     * 文件后缀
     */
    final String extension;

    PlaylistType(String encoding, String contentType, String extension) {
        this.encoding = encoding;
        this.contentType = contentType;
        this.extension = extension;
    }

    /**
     * 获取列表的编码格式
     *
     * @return 列表的编码格式
     */
    public String getEncoding() {
        return encoding;
    }

    /**
     * 获取内容类型
     *
     * @return 内容类型
     */
    public String getContentType() {
        return contentType;
    }

    /**
     * 获取文件后缀
     *
     * @return 文件后缀
     */
    public String getExtension() {
        return extension;
    }
}
