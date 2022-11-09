/*
 * Niusee server-common
 *
 * Copyright 2015-2021 by Niusee.inc. All rights reserved.
 */
package cn.niusee.common.httpclient;

import cn.niusee.common.logger.LoggerHelper;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;
import org.jetbrains.annotations.NotNull;
import spark.utils.StringUtils;

import java.io.IOException;

/**
 * HTTP回调请求队列服务
 *
 * @author Qianliang Zhang
 */
public final class RequestQueueWorker {

    /**
     * 日记记录
     */
    private static final LoggerHelper log = new LoggerHelper(RequestQueueWorker.class);

    /**
     * 请求方法
     */
    private enum Method {
        Get,
        Put,
        Post,
        Delete
    }

    /**
     * 请求体类型
     */
    private enum BodyType {
        Json,
        Form
    }

    /**
     * 请求信息
     */
    private static class RequestInfo {
        /**
         * 当前记录请求次数
         */
        int currentTime;

        /**
         * 总的请求次数
         */
        final int maxTime;

        /**
         * 请求地址
         */
        final String url;

        /**
         * 请求数据
         */
        final String data;

        /**
         * 请求方法
         */
        final Method method;

        /**
         * 请求体类型
         */
        final BodyType bodyType;

        RequestInfo(String url, int maxTime, Method method) {
            this(url, null, maxTime, method, null);
        }

        RequestInfo(String url, String data, int maxTime, Method method, BodyType bodyType) {
            this.url = url;
            this.data = data;
            this.currentTime = 0;
            this.maxTime = maxTime;
            this.method = method;
            this.bodyType = bodyType;
        }

        @Override
        public String toString() {
            return "RequestInfo{" +
                    "currentTime=" + currentTime +
                    ", maxTime=" + maxTime +
                    ", url='" + url + '\'' +
                    ", data='" + data + '\'' +
                    ", method=" + method +
                    ", bodyType=" + bodyType +
                    '}';
        }
    }

    /**
     * 添加限定次数模式的Get请求任务
     *
     * @param url   请求地址
     * @param times 限定次数，-1代表不限定次数
     */
    public void getInSpecificTimes(String url, int times) {
        request(new RequestInfo(url, times, Method.Get));
    }

    /**
     * 添加循环模式的Get请求任务
     *
     * @param url 请求地址
     */
    public void getInCyclesMode(String url) {
        getInSpecificTimes(url, -1);
    }

    /**
     * 添加限定3次请求模式的Get请求任务
     *
     * @param url 请求地址
     */
    public void getIn3Times(String url) {
        getInSpecificTimes(url, 3);
    }

    /**
     * 添加单次的Get请求任务
     *
     * @param url 请求地址
     */
    public void getOneTime(String url) {
        getInSpecificTimes(url, 1);
    }

    /**
     * 添加限定次数模式的模式的Json Post请求任务
     *
     * @param url   请求地址
     * @param data  请求内容
     * @param times 限定次数，-1代表不限定次数
     */
    public void postJsonInSpecificTimes(String url, String data, int times) {
        request(new RequestInfo(url, data, times, Method.Post, BodyType.Json));
    }

    /**
     * 添加循环模式的Json Post请求任务
     *
     * @param url  请求地址
     * @param data 请求内容
     */
    public void postJsonInCyclesMode(String url, String data) {
        postJsonInSpecificTimes(url, data, -1);
    }

    /**
     * 添加限定3次请求模式的模式的Json Post请求任务
     *
     * @param url  请求地址
     * @param data 请求内容
     */
    public void postJsonIn3Times(String url, String data) {
        postJsonInSpecificTimes(url, data, 3);
    }

    /**
     * 添加单次的Json Post请求任务
     *
     * @param url  请求地址
     * @param data 请求内容
     */
    public void postJsonOneTime(String url, String data) {
        postJsonInSpecificTimes(url, data, 1);
    }

    /**
     * 添加限定次数模式的模式的Form Post请求任务
     *
     * @param url   请求地址
     * @param data  请求内容
     * @param times 限定次数，-1代表不限定次数
     */
    public void postFormInSpecificTimes(String url, String data, int times) {
        request(new RequestInfo(url, data, times, Method.Post, BodyType.Form));
    }

    /**
     * 添加循环模式的Form Post请求任务
     *
     * @param url  请求地址
     * @param data 请求内容
     */
    public void postFormInCyclesMode(String url, String data) {
        postFormInSpecificTimes(url, data, -1);
    }

    /**
     * 添加限定3次请求模式的Form Post请求任务
     *
     * @param url  请求地址
     * @param data 请求内容
     */
    public void postFormIn3Times(String url, String data) {
        postFormInSpecificTimes(url, data, 3);
    }

    /**
     * 添加单次的Form Post请求任务
     *
     * @param url  请求地址
     * @param data 请求内容
     */
    public void postFormOneTime(String url, String data) {
        postFormInSpecificTimes(url, data, 1);
    }

    /**
     * 添加限定次数模式的模式的Json Put请求任务
     *
     * @param url   请求地址
     * @param data  请求内容
     * @param times 限定次数，-1代表不限定次数
     */
    public void putJsonInSpecificTimes(String url, String data, int times) {
        request(new RequestInfo(url, data, times, Method.Put, BodyType.Json));
    }

    /**
     * 添加循环模式的Json Put请求任务
     *
     * @param url  请求地址
     * @param data 请求内容
     */
    public void putJsonInCyclesMode(String url, String data) {
        putJsonInSpecificTimes(url, data, -1);
    }

    /**
     * 添加限定3次请求模式的模式的Json Put请求任务
     *
     * @param url  请求地址
     * @param data 请求内容
     */
    public void putJsonIn3Times(String url, String data) {
        putJsonInSpecificTimes(url, data, 3);
    }

    /**
     * 添加单次的Json Put请求任务
     *
     * @param url  请求地址
     * @param data 请求内容
     */
    public void putJsonOneTime(String url, String data) {
        putJsonInSpecificTimes(url, data, 1);
    }

    /**
     * 添加限定次数模式的模式的Form Put请求任务
     *
     * @param url   请求地址
     * @param data  请求内容
     * @param times 限定次数，-1代表不限定次数
     */
    public void putFormInSpecificTimes(String url, String data, int times) {
        request(new RequestInfo(url, data, times, Method.Put, BodyType.Form));
    }

    /**
     * 添加循环模式的Form Put请求任务
     *
     * @param url  请求地址
     * @param data 请求内容
     */
    public void putFormInCyclesMode(String url, String data) {
        putFormInSpecificTimes(url, data, -1);
    }

    /**
     * 添加限定3次请求模式的Form Put请求任务
     *
     * @param url  请求地址
     * @param data 请求内容
     */
    public void putFormIn3Times(String url, String data) {
        putFormInSpecificTimes(url, data, 3);
    }

    /**
     * 添加单次的Form Put请求任务
     *
     * @param url  请求地址
     * @param data 请求内容
     */
    public void putFormOneTime(String url, String data) {
        putFormInSpecificTimes(url, data, 1);
    }

    /**
     * 添加限定次数模式的模式的Json Post请求任务
     *
     * @param url   请求地址
     * @param data  请求内容
     * @param times 限定次数，-1代表不限定次数
     */
    public void deleteJsonInSpecificTimes(String url, String data, int times) {
        request(new RequestInfo(url, data, times, Method.Delete, BodyType.Json));
    }

    /**
     * 添加循环模式的Json Post请求任务
     *
     * @param url  请求地址
     * @param data 请求内容
     */
    public void deleteJsonInCyclesMode(String url, String data) {
        deleteJsonInSpecificTimes(url, data, -1);
    }

    /**
     * 添加限定3次请求模式的模式的Json Post请求任务
     *
     * @param url  请求地址
     * @param data 请求内容
     */
    public void deleteJsonIn3Times(String url, String data) {
        deleteJsonInSpecificTimes(url, data, 3);
    }

    /**
     * 添加单次的Json Delete请求任务
     *
     * @param url  请求地址
     * @param data 请求内容
     */
    public void deleteJsonOneTime(String url, String data) {
        deleteJsonInSpecificTimes(url, data, 1);
    }

    /**
     * 添加限定次数模式的模式的Form Delete请求任务
     *
     * @param url   请求地址
     * @param body  请求内容
     * @param times 限定次数，-1代表不限定次数
     */
    public void deleteFormInSpecificTimes(String url, String body, int times) {
        request(new RequestInfo(url, body, times, Method.Delete, BodyType.Form));
    }

    /**
     * 添加循环模式的Form Delete请求任务
     *
     * @param url  请求地址
     * @param body 请求内容
     */
    public void deleteFormInCyclesMode(String url, String body) {
        deleteFormInSpecificTimes(url, body, -1);
    }

    /**
     * 添加限定3次请求模式的Form Delete请求任务
     *
     * @param url  请求地址
     * @param body 请求内容
     */
    public void deleteFormIn3Times(String url, String body) {
        deleteFormInSpecificTimes(url, body, 3);
    }

    /**
     * 添加单次的Form Delete请求任务
     *
     * @param url  请求地址
     * @param body 请求内容
     */
    public void deleteFormOneTime(String url, String body) {
        deleteFormInSpecificTimes(url, body, 1);
    }

    /**
     * 添加限定次数模式的Delete请求任务
     *
     * @param url   请求地址
     * @param times 限定次数，-1代表不限定次数
     */
    public void deleteInSpecificTimes(String url, int times) {
        request(new RequestInfo(url, times, Method.Delete));
    }

    /**
     * 添加循环模式的Delete请求任务
     *
     * @param url 请求地址
     */
    public void deleteInCyclesMode(String url) {
        deleteInSpecificTimes(url, -1);
    }

    /**
     * 添加限定3次请求模式的Delete请求任务
     *
     * @param url 请求地址
     */
    public void deleteIn3Times(String url) {
        deleteInSpecificTimes(url, 3);
    }

    /**
     * 添加单次的Delete请求任务
     *
     * @param url 请求地址
     */
    public void deleteOneTime(String url) {
        deleteInSpecificTimes(url, 1);
    }

    /**
     * 添加请求
     *
     * @param requestInfo 请求信息
     */
    private void request(RequestInfo requestInfo) {
        requestInfo.currentTime++;
        if (requestInfo.maxTime != -1 && requestInfo.currentTime > requestInfo.maxTime) {
            log.error("Request beyond times limit. {}", requestInfo.toString());
            return;
        }
        switch (requestInfo.method) {
            case Get:
                SingletonHttpClient.getInstance().getAsync(requestInfo.url, new RequestCallback(requestInfo));
                break;
            case Post:
                switch (requestInfo.bodyType) {
                    case Json:
                        SingletonHttpClient.getInstance().postJsonAsync(requestInfo.url, requestInfo.data, new RequestCallback(requestInfo));
                        break;
                    case Form:
                        SingletonHttpClient.getInstance().postFormAsync(requestInfo.url, requestInfo.data, new RequestCallback(requestInfo));
                        break;
                    default:
                        break;
                }
                break;
            case Put:
                switch (requestInfo.bodyType) {
                    case Json:
                        SingletonHttpClient.getInstance().putJsonAsync(requestInfo.url, requestInfo.data,
                                new RequestCallback(requestInfo));
                        break;
                    case Form:
                        SingletonHttpClient.getInstance().putFormAsync(requestInfo.url, requestInfo.data,
                                new RequestCallback(requestInfo));
                        break;
                    default:
                        break;
                }
            case Delete:
                if (StringUtils.isEmpty(requestInfo.data)) {
                    SingletonHttpClient.getInstance().deleteAsync(requestInfo.url, new RequestCallback(requestInfo));
                } else {
                    switch (requestInfo.bodyType) {
                        case Json:
                            SingletonHttpClient.getInstance().deleteJsonAsync(requestInfo.url, requestInfo.data,
                                    new RequestCallback(requestInfo));
                            break;
                        case Form:
                            SingletonHttpClient.getInstance().deleteFormAsync(requestInfo.url, requestInfo.data,
                                    new RequestCallback(requestInfo));
                            break;
                        default:
                            break;
                    }
                }
                break;
            default:
                break;
        }
    }

    /**
     * 请求回调类
     */
    private class RequestCallback implements Callback {
        /**
         * 请求信息
         */
        final RequestInfo requestInfo;

        RequestCallback(RequestInfo requestInfo) {
            this.requestInfo = requestInfo;
        }

        @Override
        public void onFailure(@NotNull Call call, @NotNull IOException e) {
            handleRequestResult(null, requestInfo);
        }

        @Override
        public void onResponse(@NotNull Call call, @NotNull Response response) {
            handleRequestResult(response, requestInfo);
        }
    }

    /**
     * 处理请求的结果
     *
     * @param response 响应结果
     * @param info     请求信息
     */
    private void handleRequestResult(Response response, RequestInfo info) {
        // 没有记录的话，发出错误日记
        if (info == null) {
            log.warn("Request fail on null request info");
            return;
        }

        // 请求失败
        if (response == null) {
            log.info("Request fail, Retry. {}", info.toString());
            // 重新放到回调队列中
            request(info);
            return;
        }
        try {
            if (response.isSuccessful()) {
                log.info("Request success. {}", info.toString());
            } else {
                // 请求成功，但返回失败
                log.info("Request not successful, Retry. {}", info.toString());
                // 重新放到回调队列中
                request(info);
            }
        } finally {
            response.close();
        }
    }
}
