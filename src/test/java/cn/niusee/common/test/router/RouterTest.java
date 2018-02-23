/*
 * Niusee server-common
 *
 * Copyright 2015-2018 by Niusee.inc. All rights reserved.
 */
package cn.niusee.common.test.router;

import cn.niusee.common.httpclient.SingletonHttpClient;
import cn.niusee.common.router.DefaultJsonExceptionHandler;
import cn.niusee.common.router.User400Exception;
import cn.niusee.common.router.User404Exception;
import okhttp3.Response;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import spark.Spark;

import java.io.IOException;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * 路由测试
 *
 * @author Qianliang Zhang
 */
public class RouterTest {

    @Before
    public void startRouter() {
        Spark.get("test/:id", (request, response) -> {
            String id = request.params(":id");
            if (id.length() < 4) {
                throw new User400Exception(1001, "id not right");
            }
            List<String> idList = Arrays.asList("1234", "2345", "3456");
            if (!idList.contains(id)) {
                throw new User404Exception(1002, "id not found");
            }
            return "{\"success\":true}";
        });

        new DefaultJsonExceptionHandler().handleException();
    }

    @Test
    public void testRoute() throws IOException {
        Response response = SingletonHttpClient.get("http://127.0.0.1:4567/test/123");
        Assert.assertEquals(400, response.code());
        Assert.assertEquals("{\"error_code\":1001,\"error_msg\":\"id not right\"}", response.body().string());

        response = SingletonHttpClient.get("http://127.0.0.1:4567/test/4567");
        Assert.assertEquals(404, response.code());
        Assert.assertEquals("{\"error_code\":1002,\"error_msg\":\"id not found\"}", response.body().string());

        response = SingletonHttpClient.get("http://127.0.0.1:4567/test/1234");
        Assert.assertEquals(200, response.code());
        Assert.assertEquals("{\"success\":true}", response.body().string());
    }
}
