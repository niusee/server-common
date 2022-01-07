/*
 * Niusee server-common
 *
 * Copyright 2015-2021 by Niusee.inc. All rights reserved.
 */
package cn.niusee.common.test.httpclient;

import cn.niusee.common.httpclient.RequestQueueWorker;
import cn.niusee.common.route.IRouter;
import org.junit.BeforeClass;
import org.junit.Test;

import static cn.niusee.common.route.IRouter.NOT_FOUND_ERROR_CODE;
import static cn.niusee.common.route.IRouter.NOT_FOUND_ERROR_MSG;
import static spark.Spark.*;
import static spark.Spark.awaitInitialization;

/**
 * RequestQueueWorkerTest.java
 *
 * @author Qianliang Zhang
 */
public class RequestQueueWorkerTest {

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
    public void testOneTime() {
        RequestQueueWorker requestQueueWorker = new RequestQueueWorker();
        String data = "{\"notify\":\"stream\",\"stream_id\":\"test\",\"status\":" + 1 + "}";
        requestQueueWorker.postJsonOneTime("http://127.0.0.1:4567/post", data);
    }

    @Test
    public void test3Time() {
        RequestQueueWorker requestQueueWorker = new RequestQueueWorker();
        String data = "{\"notify\":\"stream\",\"stream_id\":\"test\",\"status\":" + 1 + "}";
        requestQueueWorker.postJsonIn3Times("http://127.0.0.1:4567/post", data);
    }
}
