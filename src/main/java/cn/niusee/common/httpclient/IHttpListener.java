/*
 * Niusee server-common
 *
 * Copyright 2015-2017 by Niusee.inc. All rights reserved.
 */
package cn.niusee.common.httpclient;

/**
 * HTTP请求结果回调
 *
 * @author Qianliang Zhang
 */
public interface IHttpListener {
    /**
     * 请求结果
     *
     * @param code     结果状态码
     * @param response 返回结果字符
     */
    void onResult(int code, String response);
}
