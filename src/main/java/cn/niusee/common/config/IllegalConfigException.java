/*
 * Niusee server-common
 *
 * Copyright 2015-2017 by Niusee.inc. All rights reserved.
 */
package cn.niusee.common.config;

/**
 * 服务配置不正确抛出错误
 *
 * @author Qianliang Zhang
 */
public class IllegalConfigException extends Exception {
    /**
     * 配置抛出错误
     *
     * @param message 错误消息
     */
    public IllegalConfigException(String message) {
        super(message);
    }
}
