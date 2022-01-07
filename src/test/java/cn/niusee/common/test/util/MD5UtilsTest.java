/*
 * Niusee server-common
 *
 * Copyright 2015-2017 by Niusee.inc. All rights reserved.
 */
package cn.niusee.common.test.util;

import cn.niusee.common.util.MD5Utils;
import junit.framework.TestCase;

import java.io.File;
import java.io.IOException;

/**
 * 测试MD5工具类
 *
 * @author Qianliang Zhang
 */
public class MD5UtilsTest extends TestCase {

    public void testMd5() {
        String test = "1=1&a=a&b=b&c=c";
        assertEquals(MD5Utils.md5(test), MD5Utils.md5(test.getBytes()));

        File file = new File("/usr/local/var/www/vod/file/demo.mp4");
        try {
            assertEquals(MD5Utils.md5File(file), MD5Utils.md5BigFile(file));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
