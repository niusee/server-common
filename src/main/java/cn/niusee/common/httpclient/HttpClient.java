/*
 * Niusee server-common
 *
 * Copyright 2015-2016 by Niusee.inc. All rights reserved.
 */
package cn.niusee.common.httpclient;

import okhttp3.*;

import java.io.IOException;

/**
 * HTTP请求客户端
 *
 * @author Qianliang Zhang
 */
public final class HttpClient {
    /**
     * 防止集成
     */
    private HttpClient() {

    }

    /**
     * IO请求错误的代码
     */
    private static final int IO_ERROR = 600;

    /**
     * Form表单请求方式
     */
    private static final MediaType FORM = MediaType.parse("application/x-www-form-urlencoded;charset=utf-8");

    /**
     * Json请求方式
     */
    private static final MediaType JSON = MediaType.parse("application/json;charset=utf-8");

    /**
     * 客户端
     */
    private final static OkHttpClient client = new OkHttpClient();

    /**
     * Get请求
     *
     * @param url 请求地址
     * @return 请求结果
     * @throws IOException 请求抛出错误
     */
    public static Response get(String url) throws IOException {
        Request request = new Request.Builder()
                .url(url)
                .build();
        return client.newCall(request).execute();
    }

    /**
     * Get异步请求
     *
     * @param url      请求地址
     * @param listener 请求结果回调
     */
    public static void getAsync(String url, IHttpListener listener) {
        Request request = new Request.Builder()
                .url(url)
                .build();
        newCallAsync(request, listener);
    }

    /**
     * Get异步请求
     *
     * @param url      请求地址
     * @param callback 请求结果回调
     */
    public static void getAsync(String url, Callback callback) {
        Request request = new Request.Builder()
                .url(url)
                .build();
        client.newCall(request).enqueue(callback);
    }

    /**
     * Post同步请求，请求体为Json
     *
     * @param url  请求地址
     * @param json Json请求体
     * @return 请求结果
     * @throws IOException 请求抛出错误
     */
    public static Response postJson(String url, String json) throws IOException {
        RequestBody body = RequestBody.create(JSON, json);
        Request request = new Request.Builder()
                .url(url)
                .post(body)
                .build();
        return client.newCall(request).execute();
    }

    /**
     * Post异步请求，请求体为Json
     *
     * @param url      请求地址
     * @param json     Json请求体
     * @param listener 请求结果回调
     */
    public static void postJsonAsync(String url, String json, IHttpListener listener) {
        RequestBody body = RequestBody.create(JSON, json);
        Request request = new Request.Builder()
                .url(url)
                .post(body)
                .build();
        newCallAsync(request, listener);
    }

    /**
     * Post异步请求，请求体为Json
     *
     * @param url      请求地址
     * @param json     Json请求体
     * @param callback 请求结果回调
     */
    public static void postJsonAsync(String url, String json, Callback callback) {
        RequestBody body = RequestBody.create(JSON, json);
        Request request = new Request.Builder()
                .url(url)
                .post(body)
                .build();
        client.newCall(request).enqueue(callback);
    }

    /**
     * Post同步请求，请求体为Form
     *
     * @param url  请求地址
     * @param form Form请求体
     * @return 请求结果
     * @throws IOException 请求抛出错误
     */
    public static Response postForm(String url, String form) throws IOException {
        RequestBody body = RequestBody.create(FORM, form);
        Request request = new Request.Builder()
                .url(url)
                .post(body)
                .build();
        return client.newCall(request).execute();
    }

    /**
     * Post异步请求，请求体为Form
     *
     * @param url      请求地址
     * @param form     Form请求体
     * @param listener 请求结果回调
     */
    public static void postFormAsync(String url, String form, IHttpListener listener) {
        RequestBody body = RequestBody.create(FORM, form);
        Request request = new Request.Builder()
                .url(url)
                .post(body)
                .build();
        newCallAsync(request, listener);
    }

    /**
     * Post异步请求，请求体为Form
     *
     * @param url      请求地址
     * @param form     Form请求体
     * @param callback 请求结果回调
     */
    public static void postFormAsync(String url, String form, Callback callback) {
        RequestBody body = RequestBody.create(FORM, form);
        Request request = new Request.Builder()
                .url(url)
                .post(body)
                .build();
        client.newCall(request).enqueue(callback);
    }

    /**
     * Put同步请求，请求体为Json
     *
     * @param url  请求地址
     * @param json Json请求体
     * @return 请求结果
     * @throws IOException 请求抛出错误
     */
    public static Response putJson(String url, String json) throws IOException {
        RequestBody body = RequestBody.create(JSON, json);
        Request request = new Request.Builder()
                .url(url)
                .put(body)
                .build();
        return client.newCall(request).execute();
    }

    /**
     * Put异步请求，请求体为Json
     *
     * @param url      请求地址
     * @param json     Json请求体
     * @param listener 请求结果回调
     */
    public static void putJsonAsync(String url, String json, IHttpListener listener) {
        RequestBody body = RequestBody.create(JSON, json);
        Request request = new Request.Builder()
                .url(url)
                .put(body)
                .build();
        newCallAsync(request, listener);
    }

    /**
     * Put异步请求，请求体为Json
     *
     * @param url      请求地址
     * @param json     Json请求体
     * @param callback 请求结果回调
     */
    public static void putJsonAsync(String url, String json, Callback callback) {
        RequestBody body = RequestBody.create(JSON, json);
        Request request = new Request.Builder()
                .url(url)
                .put(body)
                .build();
        client.newCall(request).enqueue(callback);
    }

    /**
     * Put同步请求，请求体为Form
     *
     * @param url  请求地址
     * @param form Form请求体
     * @return 请求结果
     * @throws IOException 请求抛出错误
     */
    public static Response putForm(String url, String form) throws IOException {
        RequestBody body = RequestBody.create(FORM, form);
        Request request = new Request.Builder()
                .url(url)
                .put(body)
                .build();
        return client.newCall(request).execute();
    }

    /**
     * Put异步请求，请求体为Form
     *
     * @param url      请求地址
     * @param form     Form请求体
     * @param listener 请求结果回调
     */
    public static void putFormAsync(String url, String form, IHttpListener listener) {
        RequestBody body = RequestBody.create(FORM, form);
        Request request = new Request.Builder()
                .url(url)
                .put(body)
                .build();
        newCallAsync(request, listener);
    }

    /**
     * Put异步请求，请求体为Form
     *
     * @param url      请求地址
     * @param form     Form请求体
     * @param callback 请求结果回调
     */
    public static void putFormAsync(String url, String form, Callback callback) {
        RequestBody body = RequestBody.create(FORM, form);
        Request request = new Request.Builder()
                .url(url)
                .put(body)
                .build();
        client.newCall(request).enqueue(callback);
    }

    /**
     * Delete同步请求
     *
     * @param url 请求地址
     * @return 请求结果
     * @throws IOException 请求抛出错误
     */
    public static Response delete(String url) throws IOException {
        Request request = new Request.Builder()
                .url(url)
                .delete()
                .build();
        return client.newCall(request).execute();
    }

    /**
     * Delete异步请求
     *
     * @param url      请求地址
     * @param listener 请求结果回调
     */
    public static void deleteAsync(String url, IHttpListener listener) {
        Request request = new Request.Builder()
                .url(url)
                .delete()
                .build();
        newCallAsync(request, listener);
    }

    /**
     * Delete异步请求
     *
     * @param url      请求地址
     * @param callback 请求结果回调
     */
    public static void deleteAsync(String url, Callback callback) {
        Request request = new Request.Builder()
                .url(url)
                .delete()
                .build();
        client.newCall(request).enqueue(callback);
    }

    /**
     * 新建异步请求
     *
     * @param request  请求
     * @param listener 结果回调
     */
    private static void newCallAsync(Request request, IHttpListener listener) {
        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                if (listener != null) {
                    listener.onResult(IO_ERROR, e.getMessage());
                }
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if (listener != null) {
                    try {
                        int code = response.code();
                        String result = response.body().string();
                        listener.onResult(code, result);
                    } finally {
                        response.close();
                    }
                }
            }
        });
    }
}
