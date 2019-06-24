/*
 * Niusee server-common
 *
 * Copyright 2015-2017 by Niusee.inc. All rights reserved.
 */
package cn.niusee.common.logger;

import junit.framework.TestCase;

/**
 * 测试日记
 *
 * @author Qianliang Zhang
 */
public class LoggerTest extends TestCase {

    private LoggerHelper logger = new LoggerHelper(LoggerTest.class);

    public void testLog() {
        logger.trace("test");
        logger.debug("test");
        logger.info("test");
        logger.warn("test");
        logger.error("test");
    }
}
