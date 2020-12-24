/*
 * Niusee server-common
 *
 * Copyright 2015-2020 by Niusee.inc. All rights reserved.
 */
package cn.niusee.common.m3u8;

/**
 * M3U8的结伴结构实现类
 *
 * @author Qianliang Zhang
 */
public final class ElementImpl implements Element {
    /**
     * 加密信息
     */
    private final EncryptionInfo encryptionInfo;

    /**
     * 播放时长信息
     */
    private final double duration;

    /**
     * 切片地址
     */
    private final String uri;

    /**
     * 是否连续
     */
    private boolean discontinuity;

    /**
     * 标题信息
     */
    private final String title;

    public ElementImpl(EncryptionInfo encryptionInfo, double duration, String uri, String title,
                       boolean discontinuity) {
        if (uri == null) {
            throw new NullPointerException("uri");
        }
        if (duration < -1) {
            throw new IllegalArgumentException();
        }

        this.encryptionInfo = encryptionInfo;
        this.duration = duration;
        this.uri = uri;
        this.title = title;
        this.discontinuity = discontinuity;
    }

    @Override
    public String getTitle() {
        return title;
    }

    @Override
    public int getDuration() {
        return (int) Math.round(duration);
    }

    @Override
    public void setDiscontinuity(boolean discontinuity) {
        this.discontinuity = discontinuity;
    }

    @Override
    public boolean isDiscontinuity() {
        return discontinuity;
    }

    @Override
    public double getExactDuration() {
        return duration;
    }

    @Override
    public String getURI() {
        return uri;
    }

    @Override
    public boolean isEncrypted() {
        return encryptionInfo != null;
    }

    @Override
    public EncryptionInfo getEncryptionInfo() {
        return encryptionInfo;
    }

    @Override
    public String toString() {
        return "ElementImpl{" +
                ", encryptionInfo=" + encryptionInfo +
                ", discontinuity=" + discontinuity +
                ", duration=" + duration +
                ", uri=" + uri +
                ", title='" + title + '\'' +
                '}';
    }

    @Override
    public String toM3U8() {
        String result = "";
        if (isDiscontinuity()) {
            result += M3uConstants.EXT_X_DISCONTINUITY + "\r\n";
        }
        if (isEncrypted()) {
            result += encryptionInfo.toM3U8() + "\r\n";
        }
        result += M3uConstants.EXT_INF + ":" + duration + ",";
        if (title != null) {
            result += title;
        }
        result += "\r\n" + uri;
        return result;
    }
}
