/*
 * Niusee server-common
 *
 * Copyright 2015-2017 by Niusee.inc. All rights reserved.
 */
package cn.niusee.common.httpclient;

import junit.framework.TestCase;
import okhttp3.Response;

import java.io.IOException;
import java.util.Objects;

/**
 * 测试Http客户端
 *
 * @author Qianliang Zhang
 */
public class HttpClientTest extends TestCase {

    private final HttpClient httpClient = new HttpClient();

    public void testGet() throws IOException {
        Response response = httpClient.get("http://api.jirengu.com/getWeather.php?city=深圳");
        assertEquals(200, response.code());
        System.out.println(Objects.requireNonNull(response.body()).string());
    }

    public void testGetNotFound() throws IOException {
        Response response = httpClient.get("http://api.jirengu.com/notFound.php");
        assertEquals(404, response.code());
    }

    public void testPost() throws IOException {
        Response response = httpClient.postJson("http://api.jirengu.com/fm/getLyric.php",
                "{\"sid\":\"758918\"}");
        assertEquals(200, response.code());
        System.out.println(Objects.requireNonNull(response.body()).string());
    }
}
