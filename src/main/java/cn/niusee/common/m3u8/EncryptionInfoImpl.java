/*
 * Niusee server-common
 *
 * Copyright 2015-2020 by Niusee.inc. All rights reserved.
 */
package cn.niusee.common.m3u8;

import java.net.URI;

/**
 * 加密信息类实现类
 *
 * @author Qianliang Zhang
 */
public class EncryptionInfoImpl implements EncryptionInfo {
    /**
     * 加密地址
     */
    private final URI uri;

    /**
     * 加密方法
     */
    private final String method;

    public EncryptionInfoImpl(URI uri, String method) {
        this.uri = uri;
        this.method = method;
    }

    @Override
    public URI getURI() {
        return uri;
    }

    @Override
    public String getMethod() {
        return method;
    }

    @Override
    public String toString() {
        return "EncryptionInfoImpl{" +
                "uri=" + uri +
                ", method='" + method + '\'' +
                '}';
    }

    @Override
    public String toM3U8() {
        return M3uConstants.EXT_X_KEY + ":METHOD=" + method + (uri == null ? "" : ",URI=\"" + uri + "\"");
    }
}
