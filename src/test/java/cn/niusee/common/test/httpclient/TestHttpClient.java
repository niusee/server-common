/*
 * Niusee server-common
 *
 * Copyright 2015-2017 by Niusee.inc. All rights reserved.
 */
package cn.niusee.common.test.httpclient;

import cn.niusee.common.httpclient.HttpClient;
import junit.framework.TestCase;
import org.junit.Test;

import java.io.IOException;

/**
 * 测试Http客户端
 *
 * @author Qianliang Zhang
 */
public class TestHttpClient extends TestCase {

    @Test(expected = IOException.class)
    public void testGet() throws IOException {
        HttpClient.get("http://www.baidu.com");
    }
}
