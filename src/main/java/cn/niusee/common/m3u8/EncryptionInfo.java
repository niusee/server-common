/*
 * Niusee server-common
 *
 * Copyright 2015-2020 by Niusee.inc. All rights reserved.
 */
package cn.niusee.common.m3u8;

import java.net.URI;

/**
 * 加密信息
 *
 * @author Qianliang Zhang
 */
public interface EncryptionInfo {
    /**
     * 获取加密地址
     *
     * @return 加密地址
     */
    URI getURI();

    /**
     * 获取加密方法，如aes128等。
     *
     * @return 加密方法
     */
    String getMethod();

    /**
     * 获取M3U8的内容
     *
     * @return M3U8的内容
     */
    String toM3U8();
}

