/*
 * Niusee server-common
 *
 * Copyright 2015-2017 by Niusee.inc. All rights reserved.
 */
package cn.niusee.common.test.utils;

import cn.niusee.common.utils.IDUtils;
import junit.framework.TestCase;

/**
 * 测试ID工具类
 *
 * @author Qianliang Zhang
 */
public class TestIDUtils extends TestCase {

    public void test8BitsIds() {
        String id1 = IDUtils.randomIdIn8Bits();
        System.out.println(id1);
        assertEquals(8, id1.length());
        String id2 = IDUtils.randomIdIn8Bits2();
        System.out.println(id2);
        assertEquals(8, id2.length());

        assertNotSame(id1, id2);
    }

    public void test16BitsIds() {
        String id1 = IDUtils.randomIdIn16Bits();
        System.out.println(id1);
        assertEquals(16, id1.length());
        String id2 = IDUtils.randomIdIn16Bits2();
        System.out.println(id2);
        assertEquals(16, id2.length());

        assertNotSame(id1, id2);
    }

    public void test32BitsIds() {
        String id1 = IDUtils.randomIdIn32Bits();
        System.out.println(id1);
        assertEquals(32, id1.length());
        String id2 = IDUtils.randomIdIn32Bits2();
        System.out.println(id2);
        assertEquals(32, id2.length());

        assertNotSame(id1, id2);
    }
}
