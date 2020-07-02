/*
 * Niusee server-common
 *
 * Copyright 2015-2017 by Niusee.inc. All rights reserved.
 */
package cn.niusee.common.test.config;

import cn.niusee.common.config.ConfigLoader;
import cn.niusee.common.config.ConfigLoaderFactory;
import junit.framework.TestCase;

/**
 * 测试配置读取类
 *
 * @author Qianliang Zhang
 */
public class ConfigLoaderTest extends TestCase {

    public void testConfig() {
        ConfigLoader configLoader = ConfigLoaderFactory.openConfigLoader("test.properties");
        assertEquals("hello world", configLoader.get("test_string"));
        assertEquals(1, configLoader.getInt("test_int", 0));
        assertEquals(0, configLoader.getInt("test_int1", 0));
        assertEquals(10000, configLoader.getLong("test_long", 0));
        assertEquals(0, configLoader.getLong("test_long1", 0));
        assertTrue(configLoader.getBoolean("test_bool1", false));
        assertFalse(configLoader.getBoolean("test_bool2", false));
        assertFalse(configLoader.getBoolean("test_bool3", false));
        assertFalse(configLoader.getBoolean("test_bool4", false));

        assertEquals(6.5F, configLoader.getFloat("test_float", 0F), 0F);
        assertEquals(0F, configLoader.getFloat("test_float1", 0F), 0F);
        assertEquals(7.2D, configLoader.getDouble("test_double", 0D), 0D);
        assertEquals(0D, configLoader.getDouble("test_double1", 0D), 0D);
    }
}
