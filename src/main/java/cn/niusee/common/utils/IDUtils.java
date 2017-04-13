/*
 * Niusee server-common
 *
 * Copyright 2015-2017 by Niusee.inc. All rights reserved.
 */
package cn.niusee.common.utils;

import java.util.UUID;

/**
 * ID工具类
 *
 * @author Qianliang Zhang
 */
public class IDUtils {

    // 防止继承
    private IDUtils() {

    }

    /**
     * 用来产生随机ID的字符
     */
    private static final String CHARS = "0123456789abcdefghijklmnopqrstuvwxyz";

    /**
     * 产生8位数的随机ID
     *
     * @return 8位数的随机字符ID
     */
    public static String randomIdIn8Bits() {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < 8; i++) {
            sb.append(CHARS.charAt((int) ((Math.random() * 100000) % CHARS.length())));
        }
        return sb.toString();
    }

    /**
     * 产生8位数的随机ID的另一种方式
     *
     * @return 8位数的随机字符ID
     */
    public static String randomIdIn8Bits2() {
        return MD5Utils.md5(UUID.randomUUID() + String.valueOf(System.currentTimeMillis()))
                .substring(0, 8);
    }

    /**
     * 产生16位数的随机ID
     *
     * @return 16位数的随机字符ID
     */
    public static String randomIdIn16Bits() {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < 16; i++) {
            sb.append(CHARS.charAt((int) ((Math.random() * 100000) % CHARS.length())));
        }
        return sb.toString();
    }

    /**
     * 产生16位数的随机ID的另一种方式
     *
     * @return 16位数的随机字符ID
     */
    public static String randomIdIn16Bits2() {
        return MD5Utils.md5(UUID.randomUUID() + String.valueOf(System.currentTimeMillis()))
                .substring(0, 16);
    }

    /**
     * 产生32位数的随机ID
     *
     * @return 32位数的随机字符ID
     */
    public static String randomIdIn32Bits() {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < 32; i++) {
            sb.append(CHARS.charAt((int) ((Math.random() * 100000) % CHARS.length())));
        }
        return sb.toString();
    }

    /**
     * 产生32位数的随机ID的另一种方式
     *
     * @return 32位数的随机字符ID
     */
    public static String randomIdIn32Bits2() {
        return MD5Utils.md5(UUID.randomUUID() + String.valueOf(System.currentTimeMillis()));
    }
}
