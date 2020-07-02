/*
 * Niusee server-common
 *
 * Copyright 2015-2016 by Niusee.inc. All rights reserved.
 */
package cn.niusee.common.httpclient;

import okhttp3.*;
import okhttp3.internal.annotations.EverythingIsNonNull;
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
     * Form表单请求方式
     */
    public static final MediaType FORM = MediaType.get("application/json; charset=utf-8");

    /**
     * Json请求方式
     */
    public static final MediaType JSON = MediaType.get("application/json; charset=utf-8");

    /**
     * IO请求错误的代码
     */
    private static final int IO_ERROR = 600;

    /**
     * 请求方法
     */
    enum Method {
        Head, Get, Post, Put, Patch, Delete
    }

    /**
     * 请求体封装类
     */
    private static class Body {
        /**
         * 请求体类型
         */
        private final MediaType bodyType;

        /**
         * 请求体数据
         */
        private final String body;

        Body(MediaType bodyType, String body) {
            this.bodyType = bodyType;
            this.body = body;
        }

        /**
         * 创建Form的请求体
         *
         * @param body 请求体数据
         * @return Form的请求体
         */
        static Body createFormBody(String body) {
            return new Body(FORM, body);
        }

        /**
         * 创建Json的请求体
         *
         * @param body 请求体数据
         * @return Json的请求体
         */
        static Body createJsonBody(String body) {
            return new Body(JSON, body);
        }
    }

    /**
     * 客户端
     */
    private final OkHttpClient client = new OkHttpClient();

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
    private static class CallbackWrapper implements Callback {
        /**
         * HTTP请求结果回调
         */
        private final IHttpListener listener;

        CallbackWrapper(IHttpListener listener) {
            this.listener = listener;
        }

        @Override
        @EverythingIsNonNull
        public void onFailure(Call call, IOException e) {
            if (listener != null) {
                listener.onResult(IO_ERROR, e.getMessage());
            }
        }

        @Override
        @EverythingIsNonNull
        public void onResponse(Call call, Response response) throws IOException {
            if (listener != null) {
                try {
                    int code = response.code();
                    String result = null;
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

    /**
     * 直接请求
     *
     * @param request Http3 请求
     * @return 请求响应
     * @throws IOException 请求抛出错误
     */
    public Response request(Request request) throws IOException {
        return newDirectCall(request);
    }

    /**
     * 异步请求
     *
     * @param request  Http3 请求
     * @param callback http3 Callback 请求结果回调
     */
    public void requestAsync(Request request, Callback callback) {
        newAsyncCall(request, callback);
    }

    /**
     * 异步请求
     *
     * @param request  http3 请求
     * @param listener 请求结果回调
     */
    public void requestAsync(Request request, IHttpListener listener) {
        newAsyncCall(request, listener);
    }

    /**
     * 根据请求方法、参数创建请求
     *
     * @param method    请求方法
     * @param headerMap 请求头参数集合
     * @param body      请求体
     * @param url       请求地址
     * @return 请求
     */
    private Request createRequest(Method method, Map<String, String> headerMap, Body body, String url) {
        final Request.Builder requestBuilder = new Request.Builder().url(url);
        // 请求头参数
        if (headerMap != null && !headerMap.isEmpty()) {
            headerMap.forEach((key, value) -> {
                if (StringUtils.isNotBlank(key) && StringUtils.isNotBlank(value)) {
                    requestBuilder.addHeader(key, value);
                }
            });
        }

        // 请求体
        RequestBody requestBody = null;
        if (body != null) {
            requestBody = RequestBody.create(body.bodyType, body.body);
        }
        switch (method) {
            case Head:
                requestBuilder.head();
                break;
            case Get:
                requestBuilder.get();
                break;
            case Post:
                if (requestBody != null) {
                    requestBuilder.post(requestBody);
                }
                break;
            case Put:
                if (requestBody != null) {
                    requestBuilder.put(requestBody);
                }
                break;
            case Patch:
                if (requestBody != null) {
                    requestBuilder.patch(requestBody);
                }
            case Delete:
                if (requestBody != null) {
                    requestBuilder.delete(requestBody);
                } else {
                    requestBuilder.delete();
                }
            default:
                break;

        }
        return requestBuilder.build();
    }

    /**
     * 直接请求
     *
     * @param method    请求方法
     * @param headerMap 请求头参数集合
     * @param body      请求体
     * @param url       请求地址
     * @return 请求响应
     * @throws IOException 请求出错抛出错误
     */
    private Response _request(Method method, Map<String, String> headerMap, Body body,
                              String url) throws IOException {
        return request(createRequest(method, headerMap, body, url));
    }

    /**
     * 异步请求
     *
     * @param method    请求方法
     * @param headerMap 请求头参数集合
     * @param body      请求体
     * @param url       请求地址
     * @param callback  http3 Callback 请求结果回调
     */
    private void _requestAsync(Method method, Map<String, String> headerMap, Body body,
                               String url, Callback callback) {
        requestAsync(createRequest(method, headerMap, body, url), callback);
    }

    /**
     * 异步请求
     *
     * @param method       请求方法
     * @param headerMap    请求头参数集合
     * @param body         请求体
     * @param url          请求地址
     * @param httpListener 请求结果回调
     */
    private void _requestAsync(Method method, Map<String, String> headerMap, Body body,
                               String url, IHttpListener httpListener) {
        requestAsync(createRequest(method, headerMap, body, url), httpListener);
    }

    /**
     * Head请求
     *
     * @param headerMap 请求头集合
     * @param url       请求地址
     * @return 请求响应
     * @throws IOException 请求抛出错误
     */
    public Response head(Map<String, String> headerMap, String url) throws IOException {
        return _request(Method.Head, headerMap, null, url);
    }

    /**
     * Head请求
     *
     * @param url 请求地址
     * @return 请求响应
     * @throws IOException 请求抛出错误
     */
    public Response head(String url) throws IOException {
        return head(null, url);
    }

    /**
     * Head异步请求
     *
     * @param headerMap 请求头集合
     * @param url       请求地址
     * @param callback  请求结果回调
     */
    public void headAsync(Map<String, String> headerMap, String url, Callback callback) {
        _requestAsync(Method.Head, headerMap, null, url, callback);
    }

    /**
     * Head异步请求
     *
     * @param url      请求地址
     * @param callback 请求结果回调
     */
    public void headAsync(String url, Callback callback) {
        headAsync(null, url, callback);
    }

    /**
     * Head异步请求
     *
     * @param headerMap 请求头集合
     * @param url       请求地址
     * @param listener  请求结果回调
     */
    public void headAsync(Map<String, String> headerMap, String url, IHttpListener listener) {
        _requestAsync(Method.Head, headerMap, null, url, listener);
    }

    /**
     * Head异步请求
     *
     * @param url      请求地址
     * @param listener 请求结果回调
     */
    public void headAsync(String url, IHttpListener listener) {
        headAsync(null, url, listener);
    }

    /**
     * Get请求
     *
     * @param headerMap 请求头集合
     * @param url       请求地址
     * @return 请求响应
     * @throws IOException 请求抛出错误
     */
    public Response get(Map<String, String> headerMap, String url) throws IOException {
        return _request(Method.Get, headerMap, null, url);
    }

    /**
     * Get请求
     *
     * @param url 请求地址
     * @return 请求响应
     * @throws IOException 请求抛出错误
     */
    public Response get(String url) throws IOException {
        return get(null, url);
    }

    /**
     * Get异步请求
     *
     * @param headerMap 请求头集合
     * @param url       请求地址
     * @param callback  请求结果回调
     */
    public void getAsync(Map<String, String> headerMap, String url, Callback callback) {
        _requestAsync(Method.Get, headerMap, null, url, callback);
    }

    /**
     * Get异步请求
     *
     * @param url      请求地址
     * @param callback 请求结果回调
     */
    public void getAsync(String url, Callback callback) {
        getAsync(null, url, callback);
    }

    /**
     * Get异步请求
     *
     * @param headerMap 请求头集合
     * @param url       请求地址
     * @param listener  请求结果回调
     */
    public void getAsync(Map<String, String> headerMap, String url, IHttpListener listener) {
        _requestAsync(Method.Get, headerMap, null, url, listener);
    }

    /**
     * Get异步请求
     *
     * @param url      请求地址
     * @param listener 请求结果回调
     */
    public void getAsync(String url, IHttpListener listener) {
        getAsync(null, url, listener);
    }

    /**
     * Post同步请求，请求体为Json
     *
     * @param headerMap 请求头集合
     * @param url       请求地址
     * @param json      Json请求体
     * @return 请求结果
     * @throws IOException 请求抛出错误
     */
    public Response postJson(Map<String, String> headerMap, String url, String json) throws IOException {
        return _request(Method.Post, headerMap, Body.createJsonBody(json), url);
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
        return postJson(null, url, json);
    }

    /**
     * Post异步请求，请求体为Json
     *
     * @param headerMap 请求头集合
     * @param url       请求地址
     * @param json      Json请求体
     * @param listener  请求结果回调
     */
    public void postJsonAsync(Map<String, String> headerMap, String url, String json, IHttpListener listener) {
        _requestAsync(Method.Post, headerMap, Body.createJsonBody(json), url, listener);
    }

    /**
     * Post异步请求，请求体为Json
     *
     * @param url      请求地址
     * @param json     Json请求体
     * @param listener 请求结果回调
     */
    public void postJsonAsync(String url, String json, IHttpListener listener) {
        postJsonAsync(null, url, json, listener);
    }

    /**
     * Post异步请求，请求体为Json
     *
     * @param headerMap 请求头集合
     * @param url       请求地址
     * @param json      Json请求体
     * @param callback  请求结果回调
     */
    public void postJsonAsync(Map<String, String> headerMap, String url, String json, Callback callback) {
        _requestAsync(Method.Post, headerMap, Body.createJsonBody(json), url, callback);
    }

    /**
     * Post异步请求，请求体为Json
     *
     * @param url      请求地址
     * @param json     Json请求体
     * @param callback 请求结果回调
     */
    public void postJsonAsync(String url, String json, Callback callback) {
        postJsonAsync(null, url, json, callback);
    }

    /**
     * Post同步请求，请求体为Form
     *
     * @param headerMap 请求头集合
     * @param url       请求地址
     * @param form      Form请求体
     * @return 请求结果
     * @throws IOException 请求抛出错误
     */
    public Response postForm(Map<String, String> headerMap, String url, String form) throws IOException {
        return _request(Method.Post, headerMap, Body.createFormBody(form), url);
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
        return postForm(null, url, form);
    }

    /**
     * Post异步请求，请求体为Form
     *
     * @param headerMap 请求头集合
     * @param url       请求地址
     * @param form      Form请求体
     * @param listener  请求结果回调
     */
    public void postFormAsync(Map<String, String> headerMap, String url, String form, IHttpListener listener) {
        _requestAsync(Method.Post, headerMap, Body.createFormBody(form), url, listener);
    }

    /**
     * Post异步请求，请求体为Form
     *
     * @param url      请求地址
     * @param form     Form请求体
     * @param listener 请求结果回调
     */
    public void postFormAsync(String url, String form, IHttpListener listener) {
        postFormAsync(null, url, form, listener);
    }

    /**
     * Post异步请求，请求体为Form
     *
     * @param headerMap 请求头集合
     * @param url       请求地址
     * @param form      Form请求体
     * @param callback  请求结果回调
     */
    public void postFormAsync(Map<String, String> headerMap, String url, String form, Callback callback) {
        _requestAsync(Method.Post, headerMap, Body.createFormBody(form), url, callback);
    }

    /**
     * Post异步请求，请求体为Form
     *
     * @param url      请求地址
     * @param form     Form请求体
     * @param callback 请求结果回调
     */
    public void postFormAsync(String url, String form, Callback callback) {
        postFormAsync(null, url, form, callback);
    }

    /**
     * Put同步请求，请求体为Json
     *
     * @param headerMap 请求头集合
     * @param url       请求地址
     * @param json      Json请求体
     * @return 请求结果
     * @throws IOException 请求抛出错误
     */
    public Response putJson(Map<String, String> headerMap, String url, String json) throws IOException {
        return _request(Method.Put, headerMap, Body.createJsonBody(json), url);
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
        return putJson(null, url, json);
    }

    /**
     * Put异步请求，请求体为Json
     *
     * @param headerMap 请求头集合
     * @param url       请求地址
     * @param json      Json请求体
     * @param listener  请求结果回调
     */
    public void putJsonAsync(Map<String, String> headerMap, String url, String json, IHttpListener listener) {
        _requestAsync(Method.Put, headerMap, Body.createJsonBody(json), url, listener);
    }

    /**
     * Put异步请求，请求体为Json
     *
     * @param url      请求地址
     * @param json     Json请求体
     * @param listener 请求结果回调
     */
    public void putJsonAsync(String url, String json, IHttpListener listener) {
        putJsonAsync(null, url, json, listener);
    }

    /**
     * Put异步请求，请求体为Json
     *
     * @param headerMap 请求头集合
     * @param url       请求地址
     * @param json      Json请求体
     * @param callback  请求结果回调
     */
    public void putJsonAsync(Map<String, String> headerMap, String url, String json, Callback callback) {
        _requestAsync(Method.Put, headerMap, Body.createJsonBody(json), url, callback);
    }

    /**
     * Put异步请求，请求体为Json
     *
     * @param url      请求地址
     * @param json     Json请求体
     * @param callback 请求结果回调
     */
    public void putJsonAsync(String url, String json, Callback callback) {
        putJsonAsync(null, url, json, callback);
    }

    /**
     * Put同步请求，请求体为Form
     *
     * @param headerMap 请求头集合
     * @param url       请求地址
     * @param form      Form请求体
     * @return 请求结果
     * @throws IOException 请求抛出错误
     */
    public Response putForm(Map<String, String> headerMap, String url, String form) throws IOException {
        return _request(Method.Put, headerMap, Body.createFormBody(form), url);
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
        return putForm(null, url, form);
    }

    /**
     * Put异步请求，请求体为Form
     *
     * @param headerMap 请求头集合
     * @param url       请求地址
     * @param form      Form请求体
     * @param listener  请求结果回调
     */
    public void putFormAsync(Map<String, String> headerMap, String url, String form, IHttpListener listener) {
        _requestAsync(Method.Put, headerMap, Body.createFormBody(form), url, listener);
    }

    /**
     * Put异步请求，请求体为Form
     *
     * @param url      请求地址
     * @param form     Form请求体
     * @param listener 请求结果回调
     */
    public void putFormAsync(String url, String form, IHttpListener listener) {
        putFormAsync(null, url, form, listener);
    }

    /**
     * Put异步请求，请求体为Form
     *
     * @param headerMap 请求头集合
     * @param url       请求地址
     * @param form      Form请求体
     * @param callback  请求结果回调
     */
    public void putFormAsync(Map<String, String> headerMap, String url, String form, Callback callback) {
        _requestAsync(Method.Put, headerMap, Body.createFormBody(form), url, callback);
    }

    /**
     * Put异步请求，请求体为Form
     *
     * @param url      请求地址
     * @param form     Form请求体
     * @param callback 请求结果回调
     */
    public void putFormAsync(String url, String form, Callback callback) {
        putFormAsync(null, url, form, callback);
    }

    /**
     * Delete同步请求，请求体为Json
     *
     * @param headerMap 请求头集合
     * @param url       请求地址
     * @param json      Json请求体
     * @return 请求结果
     * @throws IOException 请求抛出错误
     */
    public Response deleteJson(Map<String, String> headerMap, String url, String json) throws IOException {
        return _request(Method.Delete, headerMap, Body.createJsonBody(json), url);
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
        return deleteJson(null, url, json);
    }

    /**
     * Delete异步请求，请求体为Json
     *
     * @param headerMap 请求头集合
     * @param url       请求地址
     * @param json      Json请求体
     * @param listener  请求结果回调
     */
    public void deleteJsonAsync(Map<String, String> headerMap, String url, String json, IHttpListener listener) {
        _requestAsync(Method.Delete, headerMap, Body.createJsonBody(json), url, listener);
    }

    /**
     * Delete异步请求，请求体为Json
     *
     * @param url      请求地址
     * @param json     Json请求体
     * @param listener 请求结果回调
     */
    public void deleteJsonAsync(String url, String json, IHttpListener listener) {
        deleteJsonAsync(null, url, json, listener);
    }

    /**
     * Delete异步请求，请求体为Json
     *
     * @param headerMap 请求头集合
     * @param url       请求地址
     * @param json      Json请求体
     * @param callback  请求结果回调
     */
    public void deleteJsonAsync(Map<String, String> headerMap, String url, String json, Callback callback) {
        _requestAsync(Method.Delete, headerMap, Body.createJsonBody(json), url, callback);
    }

    /**
     * Delete异步请求，请求体为Json
     *
     * @param url      请求地址
     * @param json     Json请求体
     * @param callback 请求结果回调
     */
    public void deleteJsonAsync(String url, String json, Callback callback) {
        deleteJsonAsync(null, url, json, callback);
    }

    /**
     * Delete同步请求，请求体为Form
     *
     * @param headerMap 请求头集合
     * @param url       请求地址
     * @param form      Form请求体
     * @return 请求结果
     * @throws IOException 请求抛出错误
     */
    public Response deleteForm(Map<String, String> headerMap, String url, String form) throws IOException {
        return _request(Method.Delete, headerMap, Body.createFormBody(form), url);
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
        return deleteForm(null, url, form);
    }

    /**
     * Delete异步请求，请求体为Form
     *
     * @param headerMap 请求头集合
     * @param url       请求地址
     * @param form      Form请求体
     * @param listener  请求结果回调
     */
    public void deleteFormAsync(Map<String, String> headerMap, String url, String form, IHttpListener listener) {
        _requestAsync(Method.Delete, headerMap, Body.createFormBody(form), url, listener);
    }

    /**
     * Delete异步请求，请求体为Form
     *
     * @param url      请求地址
     * @param form     Form请求体
     * @param listener 请求结果回调
     */
    public void deleteFormAsync(String url, String form, IHttpListener listener) {
        deleteFormAsync(null, url, form, listener);
    }

    /**
     * Delete异步请求，请求体为Form
     *
     * @param headerMap 请求头集合
     * @param url       请求地址
     * @param form      Form请求体
     * @param callback  请求结果回调
     */
    public void deleteFormAsync(Map<String, String> headerMap, String url, String form, Callback callback) {
        _requestAsync(Method.Delete, headerMap, Body.createFormBody(form), url, callback);
    }

    /**
     * Delete异步请求，请求体为Form
     *
     * @param url      请求地址
     * @param form     Form请求体
     * @param callback 请求结果回调
     */
    public void deleteFormAsync(String url, String form, Callback callback) {
        deleteFormAsync(null, url, form, callback);
    }

    /**
     * Delete同步请求
     *
     * @param headerMap 请求头集合
     * @param url       请求地址
     * @return 请求结果
     * @throws IOException 请求抛出错误
     */
    public Response delete(Map<String, String> headerMap, String url) throws IOException {
        return _request(Method.Delete, headerMap, null, url);
    }

    /**
     * Delete同步请求
     *
     * @param url 请求地址
     * @return 请求结果
     * @throws IOException 请求抛出错误
     */
    public Response delete(String url) throws IOException {
        return delete(null, url);
    }

    /**
     * Delete异步请求
     *
     * @param headerMap 请求头集合
     * @param url       请求地址
     * @param listener  请求结果回调
     */
    public void deleteAsync(Map<String, String> headerMap, String url, IHttpListener listener) {
        _requestAsync(Method.Delete, headerMap, null, url, listener);
    }

    /**
     * Delete异步请求
     *
     * @param url      请求地址
     * @param listener 请求结果回调
     */
    public void deleteAsync(String url, IHttpListener listener) {
        deleteAsync(null, url, listener);
    }

    /**
     * Delete异步请求
     *
     * @param headerMap 请求头集合
     * @param url       请求地址
     * @param callback  请求结果回调
     */
    public void deleteAsync(Map<String, String> headerMap, String url, Callback callback) {
        _requestAsync(Method.Delete, headerMap, null, url, callback);
    }

    /**
     * Delete异步请求
     *
     * @param url      请求地址
     * @param callback 请求结果回调
     */
    public void deleteAsync(String url, Callback callback) {
        deleteAsync(null, url, callback);
    }
}
