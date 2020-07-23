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
        assertEquals("", configLoader.get("test_string1"));
        assertNull(configLoader.get("test_string2"));

        assertEquals(1, configLoader.getInt("test_int", 0));
        assertEquals(0, configLoader.getInt("test_int1", 0));
        assertEquals(2, configLoader.getInt("test_int1", 2));
        assertEquals(0, configLoader.getInt("test_int2", 0));
        assertEquals(2, configLoader.getInt("test_int2", 2));
        assertEquals(0, configLoader.getInt("test_int3", 0));
        assertEquals(3456, configLoader.getInt("test_int3", 3456));

        assertEquals(10000, configLoader.getLong("test_long", 0));
        assertEquals(0, configLoader.getLong("test_long1", 0));
        assertEquals(1000, configLoader.getLong("test_long1", 1000));
        assertEquals(0, configLoader.getLong("test_long2", 0));
        assertEquals(1000, configLoader.getLong("test_long2", 1000));
        assertEquals(0, configLoader.getLong("test_long3", 0));
        assertEquals(4567L, configLoader.getLong("test_long3", 4567L));

        assertFalse(configLoader.getBoolean("test_bool", false));
        assertTrue(configLoader.getBoolean("test_bool1", false));
        assertFalse(configLoader.getBoolean("test_bool2", false));
        assertTrue(configLoader.getBoolean("test_bool2", true));
        assertFalse(configLoader.getBoolean("test_bool3", false));
        assertTrue(configLoader.getBoolean("test_bool3", true));
        assertFalse(configLoader.getBoolean("test_bool4", false));
        assertTrue(configLoader.getBoolean("test_bool4", true));
        assertFalse(configLoader.getBoolean("test_bool5", false));
        assertTrue(configLoader.getBoolean("test_bool5", true));

        assertEquals(6.5F, configLoader.getFloat("test_float", 0F), 0F);
        assertEquals(0F, configLoader.getFloat("test_float1", 0F), 0F);
        assertEquals(100F, configLoader.getFloat("test_float1", 100F), 0F);
        assertEquals(0F, configLoader.getFloat("test_float2", 0F), 0F);
        assertEquals(100F, configLoader.getFloat("test_float2", 100F), 0F);
        assertEquals(0F, configLoader.getFloat("test_float3", 0F), 0F);
        assertEquals(100F, configLoader.getFloat("test_float3", 100F), 0F);

        assertEquals(7.2D, configLoader.getDouble("test_double", 0D), 0D);
        assertEquals(0D, configLoader.getDouble("test_double1", 0D), 0D);
        assertEquals(100D, configLoader.getDouble("test_double1", 100D), 0D);
        assertEquals(0D, configLoader.getDouble("test_double2", 0D), 0D);
        assertEquals(100D, configLoader.getDouble("test_double2", 100D), 0D);
        assertEquals(0D, configLoader.getDouble("test_double3", 0D), 0D);
        assertEquals(100D, configLoader.getDouble("test_double3", 100D), 0D);
    }
}
