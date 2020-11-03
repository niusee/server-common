/*
 * Niusee server-common
 *
 * Copyright 2015-2017 by Niusee.inc. All rights reserved.
 */
package cn.niusee.common.test.httpclient;

import cn.niusee.common.httpclient.HttpClient;
import okhttp3.Response;
import org.junit.Assert;
import org.junit.Test;

import java.io.IOException;
import java.io.InterruptedIOException;
import java.util.Objects;

/**
 * 测试Http客户端
 *
 * @author Qianliang Zhang
 */
public class HttpClientTest {

    @Test
    public void testGet() throws IOException {
        HttpClient httpClient = new HttpClient();
        Response response = httpClient.get("http://api.jirengu.com/getWeather.php?city=深圳");
        Assert.assertEquals(200, response.code());
        System.out.println("testGet - " + Objects.requireNonNull(response.body()).string());
    }

    @Test
    public void testGetNotFound() throws IOException {
        HttpClient httpClient = new HttpClient();
        Response response = httpClient.get("http://api.jirengu.com/notFound.php");
        Assert.assertEquals(404, response.code());
    }

    @Test
    public void testPost() throws IOException {
        HttpClient httpClient = new HttpClient();
        Response response = httpClient.postJson("http://api.jirengu.com/fm/v2/getLyric.php",
                "{\"sid\":\"758918\",\"ssid\":\"0ea3\"}");
        Assert.assertEquals(200, response.code());
        System.out.println("testPost - " + Objects.requireNonNull(response.body()).string());
    }

    @Test(expected = InterruptedIOException.class)
    public void testCallTimeout() throws IOException {
        HttpClient.CALL_TIMEOUT = 5;
        HttpClient httpClient = new HttpClient();
        httpClient.get("http://dev1.niusee.cn/live2/api/v2/box");
    }

    @Test(expected = InterruptedIOException.class)
    public void testConnectTimeout() throws IOException {
        HttpClient.CONNECT_TIMEOUT = 5;
        HttpClient httpClient = new HttpClient();
        httpClient.get("http://dev1.niusee.cn/live2/api/v2/box");
    }

    @Test(expected = InterruptedIOException.class)
    public void testReadTimeout() throws IOException {
        HttpClient.READ_TIMEOUT = 5;
        HttpClient httpClient = new HttpClient();
        httpClient.get("http://dev1.niusee.cn/live2/api/v2/box");
    }
}
