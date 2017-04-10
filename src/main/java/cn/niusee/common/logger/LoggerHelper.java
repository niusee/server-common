/*
 * Niusee server-common
 *
 * Copyright 2015-2017 by Niusee.inc. All rights reserved.
 */
package cn.niusee.common.logger;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 日记帮助类
 *
 * @author Qianliang Zhang
 */
public class LoggerHelper {
    /**
     * 日记
     */
    private final Logger logger;

    public LoggerHelper(Class<?> clazz) {
        logger = LoggerFactory.getLogger(clazz);
    }

    /**
     * 描述信息
     *
     * @param msg 信息
     */
    public void trace(String msg) {
        if (logger.isTraceEnabled()) {
            logger.trace(msg);
        }
    }

    /**
     * 描述信息
     *
     * @param format    信息格式
     * @param arguments 信息
     */
    public void trace(String format, Object... arguments) {
        if (logger.isTraceEnabled()) {
            logger.trace(format, arguments);
        }
    }

    /**
     * 调试信息
     *
     * @param msg 信息
     */
    public void debug(String msg) {
        if (logger.isDebugEnabled()) {
            logger.debug(msg);
        }
    }

    /**
     * 调试信息
     *
     * @param format    信息格式
     * @param arguments 信息
     */
    public void debug(String format, Object... arguments) {
        if (logger.isDebugEnabled()) {
            logger.debug(format, arguments);
        }
    }

    /**
     * 通知信息
     *
     * @param msg 信息
     */
    public void info(String msg) {
        if (logger.isInfoEnabled()) {
            logger.info(msg);
        }
    }

    /**
     * 通知信息
     *
     * @param format    信息格式
     * @param arguments 信息
     */
    public void info(String format, Object... arguments) {
        if (logger.isInfoEnabled()) {
            logger.info(format, arguments);
        }
    }

    /**
     * 警告信息
     *
     * @param msg 信息
     */
    public void warn(String msg) {
        if (logger.isWarnEnabled()) {
            logger.warn(msg);
        }
    }

    /**
     * 警告信息
     *
     * @param format    信息格式
     * @param arguments 信息
     */
    public void warn(String format, Object... arguments) {
        if (logger.isWarnEnabled()) {
            logger.warn(format, arguments);
        }
    }

    /**
     * 错误信息
     *
     * @param msg 信息
     */
    public void error(String msg) {
        if (logger.isErrorEnabled()) {
            logger.error(msg);
        }
    }

    /**
     * 错误信息
     *
     * @param format    信息格式
     * @param arguments 信息
     */
    public void error(String format, Object... arguments) {
        if (logger.isErrorEnabled()) {
            logger.error(format, arguments);
        }
    }
}
