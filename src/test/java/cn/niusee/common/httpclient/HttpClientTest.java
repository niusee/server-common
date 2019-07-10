/*
 * Niusee server-common
 *
 * Copyright 2015-2017 by Niusee.inc. All rights reserved.
 */
package cn.niusee.common.httpclient;

import junit.framework.TestCase;
import okhttp3.Response;

import java.io.IOException;

/**
 * 测试Http客户端
 *
 * @author Qianliang Zhang
 */
public class HttpClientTest extends TestCase {

    private final HttpClient httpClient = new HttpClient();

    public void testGet() throws IOException {
        Response response = httpClient.get("http://www.alibaba.com");
        assertEquals(200, response.code());
    }

    public void testGetNotFound() throws IOException {
        Response response = httpClient.get("http://alibaba.com/fuck");
        assertEquals(404, response.code());
    }
}
