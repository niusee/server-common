/*
 * Niusee server-common
 *
 * Copyright 2015-2017 by Niusee.inc. All rights reserved.
 */
package cn.niusee.common.httpclient;

/**
 * 单例HTTP请求客户端
 *
 * @author Qianliang Zhang
 */
public final class SingletonHttpClient extends HttpClient {
    /**
     * 实例
     */
    private static SingletonHttpClient ourInstance = new SingletonHttpClient();

    /**
     * HTTP请求单例
     *
     * @return HTTP请求单例
     */
    public static SingletonHttpClient getInstance() {
        return ourInstance;
    }
}
