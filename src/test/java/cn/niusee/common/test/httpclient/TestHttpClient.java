/*
 * Niusee server-common
 *
 * Copyright 2015-2017 by Niusee.inc. All rights reserved.
 */
package cn.niusee.common.test.httpclient;

import cn.niusee.common.httpclient.HttpClient;
import junit.framework.TestCase;
import okhttp3.Response;

import java.io.IOException;

/**
 * 测试Http客户端
 *
 * @author Qianliang Zhang
 */
public class TestHttpClient extends TestCase {

    public void testGet() throws IOException {
        Response response = HttpClient.get("http://www.baidu.com");
        assertEquals(200, response.code());
    }

    public void testGetNotFound() throws IOException {
        Response response = HttpClient.get("http://www.baiduuu.com");
        assertEquals(404, response.code());
    }
}
