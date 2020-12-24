/*
 * Niusee server-common
 *
 * Copyright 2015-2020 by Niusee.inc. All rights reserved.
 */
package cn.niusee.common.m3u8;

import java.net.URI;

/**
 * M3U8的基本结构构建方法类
 *
 * @author Qianliang Zhang
 */
public class ElementBuilder {
    /**
     * 时长
     */
    private double duration;

    /**
     * 地址
     */
    private String uri;

    /**
     * 加密信息
     */
    private EncryptionInfo encryptionInfo;

    /**
     * 标题信息
     */
    private String title;

    /**
     * 是否连续
     */
    private boolean discontinuity = false;

    public ElementBuilder() {

    }

    /**
     * 获取节目的标题信息
     *
     * @return 节目的标题信息
     */
    public String getTitle() {
        return title;
    }

    /**
     * 设置节目的标题信息
     *
     * @param title 节目的标题信息
     * @return 构建类实例
     */
    public ElementBuilder title(String title) {
        this.title = title;
        return this;
    }

    /**
     * 获取时长
     *
     * @return 时长
     */
    public double getDuration() {
        return duration;
    }

    /**
     * 设置时长
     *
     * @param duration 时长
     * @return 构建类实例
     */
    public ElementBuilder duration(double duration) {
        this.duration = duration;
        return this;
    }

    /**
     * 设置是否连续
     *
     * @param discontinuity 是否连续
     * @return 构建类实例
     */
    public ElementBuilder discontinuity(boolean discontinuity) {
        this.discontinuity = discontinuity;
        return this;
    }

    /**
     * 获取播放地址
     *
     * @return 播放地址
     */
    public String getUri() {
        return uri;
    }

    /**
     * 设置播放地址
     *
     * @param uri 播放地址
     * @return 构建类实例
     */
    public ElementBuilder uri(String uri) {
        this.uri = uri;
        return this;
    }

    /**
     * 重设定加密信息
     *
     * @return 构建类实例
     */
    public ElementBuilder resetEncryptedInfo() {
        encryptionInfo = null;
        return this;
    }

    /**
     * 重设定列表类
     *
     * @return 构建类实例
     */
    public ElementBuilder reset() {
        duration = 0;
        uri = null;
        title = null;
        discontinuity = false;
        resetEncryptedInfo();
        return this;
    }

    /**
     * 设定加密信息
     *
     * @param info 加密信息
     * @return 构建类实例
     */
    public ElementBuilder encrypted(EncryptionInfo info) {
        this.encryptionInfo = info;
        return this;
    }

    /**
     * 设定加密信息
     *
     * @param uri    加密地址
     * @param method 加密方法
     * @return 构建类实例
     */
    public ElementBuilder encrypted(final URI uri, final String method) {
        encryptionInfo = new EncryptionInfoImpl(uri, method);
        return this;
    }

    /**
     * 生成M3U8基本信息
     *
     * @return M3U8基本信息
     */
    public Element create() {
        return new ElementImpl(encryptionInfo, duration, uri, title, discontinuity);
    }
}

