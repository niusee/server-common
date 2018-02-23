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
        assertEquals(true, NumberUtils.isInteger("6"));
        assertEquals(false, NumberUtils.isInteger("6L"));
        assertEquals(false, NumberUtils.isInteger("abc"));

        assertEquals(true, NumberUtils.isDouble("6"));
        assertEquals(true, NumberUtils.isDouble("6.25"));
        assertEquals(false, NumberUtils.isDouble("6L"));
        assertEquals(false, NumberUtils.isDouble("6.25D"));
        assertEquals(false, NumberUtils.isDouble("6.25F"));
        assertEquals(false, NumberUtils.isDouble("abc"));

    }
}
