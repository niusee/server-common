/*
 * Niusee server-common
 *
 * Copyright 2015-2017 by Niusee.inc. All rights reserved.
 */
package cn.niusee.common.test.utils;

import cn.niusee.common.utils.NumberUtils;
import junit.framework.TestCase;

/**
 * 测试数字工具类
 *
 * @author Qianliang Zhang
 */
public class NumberUtilsTest extends TestCase {

    public void testNumber() {
        assertTrue(NumberUtils.isInteger("6"));
        assertFalse(NumberUtils.isInteger("6L"));
        assertFalse(NumberUtils.isInteger("abc"));

        assertTrue(NumberUtils.isDouble("6"));
        assertTrue(NumberUtils.isDouble("6.25"));
        assertFalse(NumberUtils.isDouble("6L"));
        assertFalse(NumberUtils.isDouble("6.25D"));
        assertFalse(NumberUtils.isDouble("6.25F"));
        assertFalse(NumberUtils.isDouble("abc"));

    }
}
