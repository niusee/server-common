/*
 * Niusee server-common
 *
 * Copyright 2015-2019 by Niusee.inc. All rights reserved.
 */
package cn.niusee.common.examples;

import cn.niusee.common.httpclient.SingletonHttpClient;
import cn.niusee.common.route.DefaultJsonExceptionHandler;
import com.google.gson.Gson;
import okhttp3.Response;
import spark.ResponseTransformer;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import static spark.Spark.*;

/**
 * Route examples
 *
 * @author Qianliang Zhang
 */
public class RouteExample {

    static class JsonTransformer implements ResponseTransformer {

        private final Gson gson = new Gson();

        @Override
        public String render(Object model) {
            return gson.toJson(model);
        }

    }

    public static void main(String[] args) {
        port(8899);
        get("/hls/:id", (request, response) -> {
            String id = request.params(":id");
            System.err.println(id);
            return "";
        });

        post("/hls", "application/json", (request, response) -> {
            String body = request.body();
            System.err.println("body: " + body);

            Map<String, Object> result = new HashMap<>();
            result.put("test_int", 1);
            result.put("test_double", 3.14);
            result.put("test_boolean", true);
            result.put("test_str", "string");
            result.put("test_arr", "1,2,3".split(","));
            result.put("test_map", new HashMap<>(result));
            return result;
        }, new JsonTransformer());

        new DefaultJsonExceptionHandler().handleException();

        try (Response response = SingletonHttpClient.getInstance()
                .postJson("http://127.0.0.1:8899/hls", "{\"hello\":\"world\"}")) {
            System.err.println("json: " + response.body().string());
        } catch (IOException e) {
            e.printStackTrace();
        }

        try (Response response = SingletonHttpClient.getInstance()
                .postForm("http://127.0.0.1:8899/hls", "hello=world")) {
            System.err.println("form: " + response.body().string());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
