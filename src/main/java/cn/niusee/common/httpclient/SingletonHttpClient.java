/*
 * Niusee server-common
 *
 * Copyright 2015-2017 by Niusee.inc. All rights reserved.
 */
package cn.niusee.common.httpclient;

import okhttp3.Callback;
import okhttp3.Response;

import java.io.IOException;

/**
 * 单例HTTP请求客户端
 *
 * @author Qianliang Zhang
 */
public final class SingletonHttpClient {
    /**
     * 防止集成
     */
    private SingletonHttpClient() {

    }

    /**
     * 客户端
     */
    private final static HttpClient client = new HttpClient();

    /**
     * Get请求
     *
     * @param url 请求地址
     * @return 请求结果
     * @throws IOException 请求抛出错误
     */
    public static Response get(String url) throws IOException {
        return client.get(url);
    }

    /**
     * Get异步请求
     *
     * @param url      请求地址
     * @param listener 请求结果回调
     */
    public static void getAsync(String url, IHttpListener listener) {
        client.getAsync(url, listener);
    }

    /**
     * Get异步请求
     *
     * @param url      请求地址
     * @param callback 请求结果回调
     */
    public static void getAsync(String url, Callback callback) {
        client.getAsync(url, callback);
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
        return client.postJson(url, json);
    }

    /**
     * Post异步请求，请求体为Json
     *
     * @param url      请求地址
     * @param json     Json请求体
     * @param listener 请求结果回调
     */
    public static void postJsonAsync(String url, String json, IHttpListener listener) {
        client.postJsonAsync(url, json, listener);
    }

    /**
     * Post异步请求，请求体为Json
     *
     * @param url      请求地址
     * @param json     Json请求体
     * @param callback 请求结果回调
     */
    public static void postJsonAsync(String url, String json, Callback callback) {
        client.postJsonAsync(url, json, callback);
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
        return client.postForm(url, form);
    }

    /**
     * Post异步请求，请求体为Form
     *
     * @param url      请求地址
     * @param form     Form请求体
     * @param listener 请求结果回调
     */
    public static void postFormAsync(String url, String form, IHttpListener listener) {
        client.postFormAsync(url, form, listener);
    }

    /**
     * Post异步请求，请求体为Form
     *
     * @param url      请求地址
     * @param form     Form请求体
     * @param callback 请求结果回调
     */
    public static void postFormAsync(String url, String form, Callback callback) {
        client.postFormAsync(url, form, callback);
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
        return client.postJson(url, json);
    }

    /**
     * Put异步请求，请求体为Json
     *
     * @param url      请求地址
     * @param json     Json请求体
     * @param listener 请求结果回调
     */
    public static void putJsonAsync(String url, String json, IHttpListener listener) {
        client.postJsonAsync(url, json, listener);
    }

    /**
     * Put异步请求，请求体为Json
     *
     * @param url      请求地址
     * @param json     Json请求体
     * @param callback 请求结果回调
     */
    public static void putJsonAsync(String url, String json, Callback callback) {
        client.putJsonAsync(url, json, callback);
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
        return client.putForm(url, form);
    }

    /**
     * Put异步请求，请求体为Form
     *
     * @param url      请求地址
     * @param form     Form请求体
     * @param listener 请求结果回调
     */
    public static void putFormAsync(String url, String form, IHttpListener listener) {
        client.putFormAsync(url, form, listener);
    }

    /**
     * Put异步请求，请求体为Form
     *
     * @param url      请求地址
     * @param form     Form请求体
     * @param callback 请求结果回调
     */
    public static void putFormAsync(String url, String form, Callback callback) {
        client.putFormAsync(url, form, callback);
    }

    /**
     * Delete同步请求，请求体为Json
     *
     * @param url  请求地址
     * @param json Json请求体
     * @return 请求结果
     * @throws IOException 请求抛出错误
     */
    public static Response deleteJson(String url, String json) throws IOException {
        return client.deleteJson(url, json);
    }

    /**
     * Delete异步请求，请求体为Json
     *
     * @param url      请求地址
     * @param json     Json请求体
     * @param listener 请求结果回调
     */
    public static void deleteJsonAsync(String url, String json, IHttpListener listener) {
        client.deleteJsonAsync(url, json, listener);
    }

    /**
     * Delete异步请求，请求体为Json
     *
     * @param url      请求地址
     * @param json     Json请求体
     * @param callback 请求结果回调
     */
    public static void deleteJsonAsync(String url, String json, Callback callback) {
        client.deleteJsonAsync(url, json, callback);
    }

    /**
     * Delete同步请求，请求体为Form
     *
     * @param url  请求地址
     * @param form Form请求体
     * @return 请求结果
     * @throws IOException 请求抛出错误
     */
    public static Response deleteForm(String url, String form) throws IOException {
        return client.deleteForm(url, form);
    }

    /**
     * Delete异步请求，请求体为Form
     *
     * @param url      请求地址
     * @param form     Form请求体
     * @param listener 请求结果回调
     */
    public static void deleteFormAsync(String url, String form, IHttpListener listener) {
        client.deleteFormAsync(url, form, listener);
    }

    /**
     * Delete异步请求，请求体为Form
     *
     * @param url      请求地址
     * @param form     Form请求体
     * @param callback 请求结果回调
     */
    public static void deleteFormAsync(String url, String form, Callback callback) {
        client.deleteFormAsync(url, form, callback);
    }

    /**
     * Delete同步请求
     *
     * @param url 请求地址
     * @return 请求结果
     * @throws IOException 请求抛出错误
     */
    public static Response delete(String url) throws IOException {
        return client.delete(url);
    }

    /**
     * Delete异步请求
     *
     * @param url      请求地址
     * @param listener 请求结果回调
     */
    public static void deleteAsync(String url, IHttpListener listener) {
        client.deleteAsync(url, listener);
    }

    /**
     * Delete异步请求
     *
     * @param url      请求地址
     * @param callback 请求结果回调
     */
    public static void deleteAsync(String url, Callback callback) {
        client.deleteAsync(url, callback);
    }
}
