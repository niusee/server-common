/*
 * Niusee server-common
 *
 * Copyright 2015-2021 by Niusee.inc. All rights reserved.
 */
package cn.niusee.common.test.m3u8.util;

import cn.niusee.common.util.HttpUtils;
import junit.framework.TestCase;

/**
 * HttpUtilsTest.java
 *
 * @author Qianliang Zhang
 */
public class HttpUtilsTest extends TestCase {

    public void testGetAuthority() {
        assertEquals("media.niusee.cn", HttpUtils.getAuthority("https://media.niusee.cn/1/2.m3u8"));
        assertEquals("media.niusee.cn", HttpUtils.getAuthority2("https://media.niusee.cn/1/2.m3u8"));
        assertEquals("media.niusee.cn:80",
                HttpUtils.getAuthority("https://media.niusee.cn:80/2/3.m3u8"));
        assertEquals("media.niusee.cn:80",
                HttpUtils.getAuthority2("https://media.niusee.cn:80/2/3.m3u8"));
        assertEquals("media.niusee.cn:8080",
                HttpUtils.getAuthority("https://media.niusee.cn:8080/3/4.m3u8"));
        assertEquals("media.niusee.cn:8080",
                HttpUtils.getAuthority2("https://media.niusee.cn:8080/3/4.m3u8"));
    }

    public void testGetHost() {
        assertEquals("media.niusee.cn", HttpUtils.getHost("https://media.niusee.cn/1/2.m3u8"));
        assertEquals("media.niusee.cn", HttpUtils.getHost2("https://media.niusee.cn/1/2.m3u8"));
        assertEquals("media.niusee.cn", HttpUtils.getHost("https://media.niusee.cn:80/2/3.m3u8"));
        assertEquals("media.niusee.cn", HttpUtils.getHost2("https://media.niusee.cn:80/2/3.m3u8"));
        assertEquals("media.niusee.cn", HttpUtils.getHost("https://media.niusee.cn:8080/3/4.m3u8"));
        assertEquals("media.niusee.cn", HttpUtils.getHost2("https://media.niusee.cn:8080/3/4.m3u8"));
    }

    public void testGetPort() {
        assertEquals(80, HttpUtils.getPort("https://media.niusee.cn/1/2.m3u8"));
        assertEquals(80, HttpUtils.getPort2("https://media.niusee.cn/1/2.m3u8"));
        assertEquals(80, HttpUtils.getPort("https://media.niusee.cn:80/2/3.m3u8"));
        assertEquals(80, HttpUtils.getPort2("https://media.niusee.cn:80/2/3.m3u8"));
        assertEquals(8080, HttpUtils.getPort("https://media.niusee.cn:8080/3/4.m3u8"));
        assertEquals(8080, HttpUtils.getPort2("https://media.niusee.cn:8080/3/4.m3u8"));
    }

    public void testGetPath() {
        assertEquals("/1/2.m3u8", HttpUtils.getPath("https://media.niusee.cn/1/2.m3u8"));
        assertEquals("/1/2.m3u8", HttpUtils.getPath2("https://media.niusee.cn/1/2.m3u8"));
        assertEquals("/2/3.m3u8", HttpUtils.getPath("https://media.niusee.cn:80/2/3.m3u8"));
        assertEquals("/2/3.m3u8", HttpUtils.getPath2("https://media.niusee.cn:80/2/3.m3u8"));
        assertEquals("/3/4.m3u8", HttpUtils.getPath("https://media.niusee.cn:8080/3/4.m3u8"));
        assertEquals("/3/4.m3u8", HttpUtils.getPath2("https://media.niusee.cn:8080/3/4.m3u8"));
    }

    public void testGetParentPath() {
        assertEquals("/1/", HttpUtils.getParentPath("https://media.niusee.cn/1/2.m3u8"));
        assertEquals("/1/", HttpUtils.getParentPath2("https://media.niusee.cn/1/2.m3u8"));
        assertEquals("/2/", HttpUtils.getParentPath("https://media.niusee.cn:80/2/3.m3u8"));
        assertEquals("/2/", HttpUtils.getParentPath2("https://media.niusee.cn:80/2/3.m3u8"));
        assertEquals("/3/", HttpUtils.getParentPath("https://media.niusee.cn:8080/3/4.m3u8"));
        assertEquals("/3/", HttpUtils.getParentPath2("https://media.niusee.cn:8080/3/4.m3u8"));
        assertEquals("/3/4/5/6/", HttpUtils.getParentPath("https://media.niusee.cn/3/4/5/6/7.m3u8"));
        assertEquals("/3/4/5/6/7/8/9/",
                HttpUtils.getParentPath2("https://media.niusee.cn/3/4/5/6/7/8/9/0.m3u8"));
    }

    public void testSameHostAndPort() {
        assertTrue(HttpUtils.isSameHostAndPort(new String[]{
                "https://media.niusee.cn/1/2.m3u8",
                "https://media.niusee.cn/2/3.m3u8",
                "https://media.niusee.cn:80/3/4.m3u8"}));

        assertFalse(HttpUtils.isSameHostAndPort(new String[]{
                "https://media.niusee.cn/1/2.m3u8",
                "https://media.niusee.cn/2/3.m3u8",
                "https://media.niusee.cn:8080/3/4.m3u8"}));

        assertFalse(HttpUtils.isSameHostAndPort(new String[]{
                "https://media.niusee.cn/1/2.m3u8",
                "https://media.niusee.cn:80/2/3.m3u8",
                "https://media.niusee.cn:8080/3/4.m3u8"}));
    }

    public void testSameHostAndPortAndParentPath() {
        assertTrue(HttpUtils.isSameHostAndPortAndParentPath(new String[]{
                "https://media.niusee.cn/1/2.m3u8",
                "https://media.niusee.cn/1/3.m3u8",
                "https://media.niusee.cn/1/4.m3u8"}));

        assertTrue(HttpUtils.isSameHostAndPortAndParentPath(new String[]{
                "https://media.niusee.cn/1/2.m3u8",
                "https://media.niusee.cn/1/3.m3u8",
                "https://media.niusee.cn:80/1/4.m3u8"}));

        assertFalse(HttpUtils.isSameHostAndPortAndParentPath(new String[]{
                "https://media.niusee.cn/1/2.m3u8",
                "https://media.niusee.cn:80/1/3.m3u8",
                "https://media.niusee.cn:8080/1/4.m3u8"}));

        assertFalse(HttpUtils.isSameHostAndPortAndParentPath(new String[]{
                "https://media.niusee.cn/1/2.m3u8",
                "https://media.niusee.cn/2/3.m3u8",
                "https://media.niusee.cn/3/4.m3u8"}));

        assertFalse(HttpUtils.isSameHostAndPortAndParentPath(new String[]{
                "https://media.niusee.cn/1/2.m3u8",
                "https://media.niusee.cn:80/2/3.m3u8",
                "https://media.niusee.cn:8080/3/4.m3u8"}));
    }
}
