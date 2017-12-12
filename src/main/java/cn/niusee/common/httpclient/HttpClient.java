/*
 * Niusee server-common
 *
 * Copyright 2015-2016 by Niusee.inc. All rights reserved.
 */
package cn.niusee.common.httpclient;

import okhttp3.*;
import spark.utils.StringUtils;

import java.io.IOException;
import java.util.Map;

/**
 * HTTP请求客户端
 *
 * @author Qianliang Zhang
 */
public class HttpClient {

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
    private OkHttpClient client = new OkHttpClient();

    /**
     * Get请求
     *
     * @param url 请求地址
     * @return 请求结果
     * @throws IOException 请求抛出错误
     */
    public Response get(String url) throws IOException {
        Request request = new Request.Builder()
                .url(url)
                .get()
                .build();
        return newDirectCall(request);
    }

    /**
     * Get请求
     *
     * @param headerMap 请求头
     * @param url       请求地址
     * @return 请求结果
     * @throws IOException 请求抛出错误
     */
    public Response get(Map<String, String> headerMap, String url) throws IOException {
        Request.Builder requestBuilder = new Request.Builder();
        // 请求头部
        if (headerMap != null && !headerMap.isEmpty()) {
            headerMap.forEach((k, v) -> {
                if (StringUtils.isNotBlank(k)) {
                    requestBuilder.addHeader(k, headerMap.get(k));
                }
            });
        }
        // 请求地址
        requestBuilder.url(url);
        Request request = requestBuilder.get()
                .build();
        return newDirectCall(request);
    }

    /**
     * Get异步请求
     *
     * @param url      请求地址
     * @param listener 请求结果回调
     */
    public void getAsync(String url, IHttpListener listener) {
        Request request = new Request.Builder()
                .url(url)
                .get()
                .build();
        newAsyncCall(request, listener);
    }

    /**
     * Get异步请求
     *
     * @param url      请求地址
     * @param callback 请求结果回调
     */
    public void getAsync(String url, Callback callback) {
        Request request = new Request.Builder()
                .url(url)
                .get()
                .build();
        newAsyncCall(request, callback);
    }

    /**
     * Post同步请求，请求体为Json
     *
     * @param url  请求地址
     * @param json Json请求体
     * @return 请求结果
     * @throws IOException 请求抛出错误
     */
    public Response postJson(String url, String json) throws IOException {
        RequestBody body = RequestBody.create(JSON, json);
        Request request = new Request.Builder()
                .url(url)
                .post(body)
                .build();
        return newDirectCall(request);
    }

    /**
     * Post异步请求，请求体为Json
     *
     * @param url      请求地址
     * @param json     Json请求体
     * @param listener 请求结果回调
     */
    public void postJsonAsync(String url, String json, IHttpListener listener) {
        RequestBody body = RequestBody.create(JSON, json);
        Request request = new Request.Builder()
                .url(url)
                .post(body)
                .build();
        newAsyncCall(request, listener);
    }

    /**
     * Post异步请求，请求体为Json
     *
     * @param url      请求地址
     * @param json     Json请求体
     * @param callback 请求结果回调
     */
    public void postJsonAsync(String url, String json, Callback callback) {
        RequestBody body = RequestBody.create(JSON, json);
        Request request = new Request.Builder()
                .url(url)
                .post(body)
                .build();
        newAsyncCall(request, callback);
    }

    /**
     * Post同步请求，请求体为Form
     *
     * @param url  请求地址
     * @param form Form请求体
     * @return 请求结果
     * @throws IOException 请求抛出错误
     */
    public Response postForm(String url, String form) throws IOException {
        RequestBody body = RequestBody.create(FORM, form);
        Request request = new Request.Builder()
                .url(url)
                .post(body)
                .build();
        return newDirectCall(request);
    }

    /**
     * Post异步请求，请求体为Form
     *
     * @param url      请求地址
     * @param form     Form请求体
     * @param listener 请求结果回调
     */
    public void postFormAsync(String url, String form, IHttpListener listener) {
        RequestBody body = RequestBody.create(FORM, form);
        Request request = new Request.Builder()
                .url(url)
                .post(body)
                .build();
        newAsyncCall(request, listener);
    }

    /**
     * Post异步请求，请求体为Form
     *
     * @param url      请求地址
     * @param form     Form请求体
     * @param callback 请求结果回调
     */
    public void postFormAsync(String url, String form, Callback callback) {
        RequestBody body = RequestBody.create(FORM, form);
        Request request = new Request.Builder()
                .url(url)
                .post(body)
                .build();
        newAsyncCall(request, callback);
    }

    /**
     * Put同步请求，请求体为Json
     *
     * @param url  请求地址
     * @param json Json请求体
     * @return 请求结果
     * @throws IOException 请求抛出错误
     */
    public Response putJson(String url, String json) throws IOException {
        RequestBody body = RequestBody.create(JSON, json);
        Request request = new Request.Builder()
                .url(url)
                .put(body)
                .build();
        return newDirectCall(request);
    }

    /**
     * Put异步请求，请求体为Json
     *
     * @param url      请求地址
     * @param json     Json请求体
     * @param listener 请求结果回调
     */
    public void putJsonAsync(String url, String json, IHttpListener listener) {
        RequestBody body = RequestBody.create(JSON, json);
        Request request = new Request.Builder()
                .url(url)
                .put(body)
                .build();
        newAsyncCall(request, listener);
    }

    /**
     * Put异步请求，请求体为Json
     *
     * @param url      请求地址
     * @param json     Json请求体
     * @param callback 请求结果回调
     */
    public void putJsonAsync(String url, String json, Callback callback) {
        RequestBody body = RequestBody.create(JSON, json);
        Request request = new Request.Builder()
                .url(url)
                .put(body)
                .build();
        newAsyncCall(request, callback);
    }

    /**
     * Put同步请求，请求体为Form
     *
     * @param url  请求地址
     * @param form Form请求体
     * @return 请求结果
     * @throws IOException 请求抛出错误
     */
    public Response putForm(String url, String form) throws IOException {
        RequestBody body = RequestBody.create(FORM, form);
        Request request = new Request.Builder()
                .url(url)
                .put(body)
                .build();
        return newDirectCall(request);
    }

    /**
     * Put异步请求，请求体为Form
     *
     * @param url      请求地址
     * @param form     Form请求体
     * @param listener 请求结果回调
     */
    public void putFormAsync(String url, String form, IHttpListener listener) {
        RequestBody body = RequestBody.create(FORM, form);
        Request request = new Request.Builder()
                .url(url)
                .put(body)
                .build();
        newAsyncCall(request, listener);
    }

    /**
     * Put异步请求，请求体为Form
     *
     * @param url      请求地址
     * @param form     Form请求体
     * @param callback 请求结果回调
     */
    public void putFormAsync(String url, String form, Callback callback) {
        RequestBody body = RequestBody.create(FORM, form);
        Request request = new Request.Builder()
                .url(url)
                .put(body)
                .build();
        newAsyncCall(request, callback);
    }

    /**
     * Delete同步请求，请求体为Json
     *
     * @param url  请求地址
     * @param json Json请求体
     * @return 请求结果
     * @throws IOException 请求抛出错误
     */
    public Response deleteJson(String url, String json) throws IOException {
        RequestBody body = RequestBody.create(JSON, json);
        Request request = new Request.Builder()
                .url(url)
                .delete(body)
                .build();
        return newDirectCall(request);
    }

    /**
     * Delete异步请求，请求体为Json
     *
     * @param url      请求地址
     * @param json     Json请求体
     * @param listener 请求结果回调
     */
    public void deleteJsonAsync(String url, String json, IHttpListener listener) {
        RequestBody body = RequestBody.create(JSON, json);
        Request request = new Request.Builder()
                .url(url)
                .delete(body)
                .build();
        newAsyncCall(request, listener);
    }

    /**
     * Delete异步请求，请求体为Json
     *
     * @param url      请求地址
     * @param json     Json请求体
     * @param callback 请求结果回调
     */
    public void deleteJsonAsync(String url, String json, Callback callback) {
        RequestBody body = RequestBody.create(JSON, json);
        Request request = new Request.Builder()
                .url(url)
                .delete(body)
                .build();
        newAsyncCall(request, callback);
    }

    /**
     * Delete同步请求，请求体为Form
     *
     * @param url  请求地址
     * @param form Form请求体
     * @return 请求结果
     * @throws IOException 请求抛出错误
     */
    public Response deleteForm(String url, String form) throws IOException {
        RequestBody body = RequestBody.create(FORM, form);
        Request request = new Request.Builder()
                .url(url)
                .delete(body)
                .build();
        return newDirectCall(request);
    }

    /**
     * Delete异步请求，请求体为Form
     *
     * @param url      请求地址
     * @param form     Form请求体
     * @param listener 请求结果回调
     */
    public void deleteFormAsync(String url, String form, IHttpListener listener) {
        RequestBody body = RequestBody.create(FORM, form);
        Request request = new Request.Builder()
                .url(url)
                .delete(body)
                .build();
        newAsyncCall(request, listener);
    }

    /**
     * Delete异步请求，请求体为Form
     *
     * @param url      请求地址
     * @param form     Form请求体
     * @param callback 请求结果回调
     */
    public void deleteFormAsync(String url, String form, Callback callback) {
        RequestBody body = RequestBody.create(FORM, form);
        Request request = new Request.Builder()
                .url(url)
                .delete(body)
                .build();
        newAsyncCall(request, callback);
    }

    /**
     * Delete同步请求
     *
     * @param url 请求地址
     * @return 请求结果
     * @throws IOException 请求抛出错误
     */
    public Response delete(String url) throws IOException {
        Request request = new Request.Builder()
                .url(url)
                .delete()
                .build();
        return newDirectCall(request);
    }

    /**
     * Delete异步请求
     *
     * @param url      请求地址
     * @param listener 请求结果回调
     */
    public void deleteAsync(String url, IHttpListener listener) {
        Request request = new Request.Builder()
                .url(url)
                .delete()
                .build();
        newAsyncCall(request, listener);
    }

    /**
     * Delete异步请求
     *
     * @param url      请求地址
     * @param callback 请求结果回调
     */
    public void deleteAsync(String url, Callback callback) {
        Request request = new Request.Builder()
                .url(url)
                .delete()
                .build();
        newAsyncCall(request, callback);
    }

    /**
     * 新建同步请求
     *
     * @param request 请求
     * @return 响应
     * @throws IOException 请求出错抛出错误
     */
    private Response newDirectCall(Request request) throws IOException {
        return client.newCall(request).execute();
    }

    /**
     * 新建异步请求
     *
     * @param request  请求
     * @param callback 结果回调
     */
    private void newAsyncCall(Request request, Callback callback) {
        client.newCall(request).enqueue(callback);
    }

    /**
     * 新建异步请求
     *
     * @param request  请求
     * @param listener 结果回调
     */
    private void newAsyncCall(Request request, IHttpListener listener) {
        client.newCall(request).enqueue(new CallbackWrapper(listener));
    }

    /**
     * 回调封装
     */
    private class CallbackWrapper implements Callback {
        /**
         * HTTP请求结果回调
         */
        IHttpListener listener;

        CallbackWrapper(IHttpListener listener) {
            this.listener = listener;
        }

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
                    String result = "";
                    ResponseBody body = response.body();
                    // 防止返回为空的现象
                    if (body != null) {
                        result = body.string();
                    }
                    listener.onResult(code, result);
                } finally {
                    response.close();
                }
            }
        }
    }
}
