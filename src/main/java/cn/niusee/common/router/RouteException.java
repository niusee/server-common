/*
 * Niusee server-common
 *
 * Copyright 2015-2017 by Niusee.inc. All rights reserved.
 */
package cn.niusee.common.router;

/**
 * 路由错误抛出类
 *
 * @author Qianliang Zhang
 */
class RouteException extends Exception {
    /**
     * 错误代码
     */
    private int errorCode;

    /**
     * 错误信息
     */
    private String errorMessage;

    RouteException(int errorCode, String message) {
        super(message);
        this.errorCode = errorCode;
        this.errorMessage = message;
    }

    /**
     * 获取错误代码
     *
     * @return 错误代码
     */
    int getErrorCode() {
        return errorCode;
    }

    /**
     * 获取错误信息
     *
     * @return 错误信息
     */
    String getErrorMessage() {
        return errorMessage;
    }
}
