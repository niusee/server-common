/*
 * Niusee server-common
 *
 * Copyright 2015-2021 by Niusee.inc. All rights reserved.
 */
package cn.niusee.common.test.m3u8.util;

import cn.niusee.common.m3u8.M3u8ParseException;
import cn.niusee.common.m3u8.Playlist;
import cn.niusee.common.m3u8.util.M3u8Utils;
import junit.framework.TestCase;

import java.io.IOException;

/**
 * M3u8UtilsTest.java
 *
 * @author Qianliang Zhang
 */
public class M3u8UtilsTest extends TestCase {

    public void testGetElementRelativePath() {
        assertEquals("3.ts",
                M3u8Utils.getElementRelativePath("https://media.niusee.cn/1/2.m3u8", "3.ts"));
        assertEquals("3.ts",
                M3u8Utils.getElementRelativePath("https://media.niusee.cn/1/2.m3u8", "/1/3.ts"));
        assertEquals("/2/3.ts",
                M3u8Utils.getElementRelativePath("https://media.niusee.cn/1/2.m3u8", "/2/3.ts"));
        assertEquals("3.ts",
                M3u8Utils.getElementRelativePath("https://media.niusee.cn/1/2.m3u8", "https://media.niusee.cn/1/3.ts"));
        assertEquals("/2/3.ts",
                M3u8Utils.getElementRelativePath("https://media.niusee.cn/1/2.m3u8", "https://media.niusee.cn/2/3.ts"));
        assertEquals("https://m.niusee.cn/2/3.ts",
                M3u8Utils.getElementRelativePath("https://media.niusee.cn/1/2.m3u8", "https://m.niusee.cn/2/3.ts"));
        assertEquals("3.ts",
                M3u8Utils.getElementRelativePath("https://media.niusee.cn/1/2.m3u8", "https://media.niusee.cn:80/1/3.ts"));
        assertEquals("/2/3.ts",
                M3u8Utils.getElementRelativePath("https://media.niusee.cn/1/2.m3u8", "https://media.niusee.cn:80/2/3.ts"));
    }

    public void testGetElementAbsolutePath() {
        assertEquals("/1/3.ts",
                M3u8Utils.getElementAbsolutePath("https://media.niusee.cn/1/2.m3u8", "3.ts"));
        assertEquals("/1/2/3/5/6/7/8/9.ts",
                M3u8Utils.getElementAbsolutePath("https://media.niusee.cn/1/2/3/4.m3u8", "5/6/7/8/9.ts"));
        assertEquals("/1/3.ts",
                M3u8Utils.getElementAbsolutePath("https://media.niusee.cn/1/2.m3u8", "/1/3.ts"));
        assertEquals("/2/3.ts",
                M3u8Utils.getElementAbsolutePath("https://media.niusee.cn/1/2.m3u8", "/2/3.ts"));
        assertEquals("/1/3.ts",
                M3u8Utils.getElementAbsolutePath("https://media.niusee.cn/1/2.m3u8", "https://media.niusee.cn/1/3.ts"));
        assertEquals("/2/3.ts",
                M3u8Utils.getElementAbsolutePath("https://media.niusee.cn/1/2.m3u8", "https://media.niusee.cn/2/3.ts"));
        assertEquals("https://m.niusee.cn/2/3.ts",
                M3u8Utils.getElementAbsolutePath("https://media.niusee.cn/1/2.m3u8", "https://m.niusee.cn/2/3.ts"));
        assertEquals("/2/3.ts",
                M3u8Utils.getElementRelativePath("https://media.niusee.cn/1/2.m3u8", "https://media.niusee.cn:80/2/3.ts"));
    }

    public void testGetElementRequestUri() {
        assertEquals("https://media.niusee.cn/1/3.ts",
                M3u8Utils.getElementRequestUri("https://media.niusee.cn/1/2.m3u8", "3.ts"));
        assertEquals("https://media.niusee.cn/1/3.ts",
                M3u8Utils.getElementRequestUri("https://media.niusee.cn/1/2.m3u8", "/1/3.ts"));
        assertEquals("https://media.niusee.cn/2/3.ts",
                M3u8Utils.getElementRequestUri("https://media.niusee.cn/1/2.m3u8", "/2/3.ts"));
        assertEquals("https://media.niusee.cn/1/3.ts",
                M3u8Utils.getElementRequestUri("https://media.niusee.cn/1/2.m3u8", "https://media.niusee.cn/1/3.ts"));
        assertEquals("https://media.niusee.cn/2/3.ts",
                M3u8Utils.getElementRequestUri("https://media.niusee.cn/1/2.m3u8", "https://media.niusee.cn/2/3.ts"));
        assertEquals("https://m.niusee.cn/2/3.ts",
                M3u8Utils.getElementRequestUri("https://media.niusee.cn/1/2.m3u8", "https://m.niusee.cn/2/3.ts"));
    }

    public void testMerge() throws IOException, M3u8ParseException {
        String[] urls = new String[] {
                "http://web.z.me:8080/1/2.m3u8",
                "http://web.z.me:8080/1/2.m3u8",
                "http://web.z.me:8080/1/2.m3u8"
        };
        Playlist playlist = M3u8Utils.merge("http://web.z.me:8080/2/5.m3u8", urls);
        System.out.println(playlist.toM3u8());
    }
}
