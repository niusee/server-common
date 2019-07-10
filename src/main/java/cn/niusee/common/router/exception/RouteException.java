/*
 * Niusee server-common
 *
 * Copyright 2015-2017 by Niusee.inc. All rights reserved.
 */
package cn.niusee.common.router.exception;

/**
 * 路由错误抛出类
 *
 * @author Qianliang Zhang
 */
public class RouteException extends RuntimeException {

    /**
     * 服务响应代码
     */
    private final int responseCode;

    /**
     * 错误代码
     */
    private final int errorCode;

    /**
     * 错误信息
     */
    private final String errorMessage;

    public RouteException(int responseCode, int errorCode, String message) {
        super(message);
        this.responseCode = responseCode;
        this.errorCode = errorCode;
        this.errorMessage = message;
    }

    /**
     * 获取服务响应代码
     *
     * @return 服务响应代码
     */
    public int getResponseCode() {
        return responseCode;
    }

    /**
     * 获取错误代码
     *
     * @return 错误代码
     */
    public int getErrorCode() {
        return errorCode;
    }

    /**
     * 获取错误信息
     *
     * @return 错误信息
     */
    public String getErrorMessage() {
        return errorMessage;
    }

    @Override
    public Throwable fillInStackTrace() {
        return null;
    }
}
