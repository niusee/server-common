/*
 * Niusee server-common
 *
 * Copyright 2015-2017 by Niusee.inc. All rights reserved.
 */
package cn.niusee.common.test.logger;

import cn.niusee.common.logger.LoggerHelper;
import junit.framework.TestCase;

/**
 * 测试日记
 *
 * @author Qianliang Zhang
 */
public class TestLogger extends TestCase {

    private LoggerHelper logger = new LoggerHelper(TestLogger.class);

    public void testDebug() {
        logger.debug("test debug");
    }
}
