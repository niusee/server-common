/*
 * Niusee server-common
 *
 * Copyright 2015-2017 by Niusee.inc. All rights reserved.
 */
package cn.niusee.common.test.httpclient;

import cn.niusee.common.httpclient.HttpClient;
import cn.niusee.common.route.IRouter;
import okhttp3.Response;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

import java.io.IOException;
import java.io.InterruptedIOException;
import java.util.Objects;

import static cn.niusee.common.route.IRouter.NOT_FOUND_ERROR_CODE;
import static cn.niusee.common.route.IRouter.NOT_FOUND_ERROR_MSG;
import static spark.Spark.*;

/**
 * 测试Http客户端
 *
 * @author Qianliang Zhang
 */
public class HttpClientTest {

    @BeforeClass
    public static void init() {
        class TestRouter implements IRouter {
            @Override
            public void route() {

                get("/get", (request, response) -> {
                    System.err.println("Get request: " + request.queryString());
                    return "{\"success\":true}";
                });

                post("/post", (request, response) -> {
                    System.err.println("post request: " + request.body());
                    return "{\"success\":true}";
                });

                put("/put", (request, response) -> {
                    System.err.println("put request: " + request.body());
                    return "{\"success\":true}";
                });

                delete("/delete", (request, response) -> {
                    System.err.println("delete request: " + request.body());
                    return "{\"success\":true}";
                });
            }
        }

        new TestRouter().route();

        // 没有路由的处理
        notFound((req, res) -> {
            res.type("application/json");
            return "{\"error_code\":" + NOT_FOUND_ERROR_CODE + ",\"error_msg\":\"" + NOT_FOUND_ERROR_MSG + "\"}";
        });

        // 等待启动完成
        awaitInitialization();
    }

    @Test
    public void testGet() throws IOException {
        HttpClient httpClient = new HttpClient();
        Response response = httpClient.get("http://127.0.0.1:4567/get?a=1&b=2&c=3");
        Assert.assertEquals(200, response.code());
        System.err.println("testGet - " + Objects.requireNonNull(response.body()).string());
    }

    @Test
    public void testGetNotFound() throws IOException {
        HttpClient httpClient = new HttpClient();
        Response response = httpClient.get("http://127.0.0.1:4567/notFound");
        Assert.assertEquals(404, response.code());
    }

    @Test
    public void testPost() throws IOException {
        HttpClient httpClient = new HttpClient();
        Response response = httpClient.postJson("http://127.0.0.1:4567/post",
                "{\"sid\":\"758918\",\"ssid\":\"0ea3\"}");
        Assert.assertEquals(200, response.code());
        System.err.println("testPost - " + Objects.requireNonNull(response.body()).string());
    }

    @Test
    public void testPut() throws IOException {
        HttpClient httpClient = new HttpClient();
        Response response = httpClient.putJson("http://127.0.0.1:4567/put",
                "{\"sid\":\"758918\",\"ssid\":\"0ea3\"}");
        Assert.assertEquals(200, response.code());
        System.err.println("testPut - " + Objects.requireNonNull(response.body()).string());
    }

    @Test
    public void testDelete() throws IOException {
        HttpClient httpClient = new HttpClient();
        Response response = httpClient.deleteJson("http://127.0.0.1:4567/delete",
                "{\"sid\":\"758918\",\"ssid\":\"0ea3\"}");
        Assert.assertEquals(200, response.code());
        System.err.println("testDelete - " + Objects.requireNonNull(response.body()).string());
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
