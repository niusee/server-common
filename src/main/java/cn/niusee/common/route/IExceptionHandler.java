/*
 * Niusee server-common
 *
 * Copyright 2015-2017 by Niusee.inc. All rights reserved.
 */
package cn.niusee.common.route;

import cn.niusee.common.route.exception.RouteException;
import spark.Response;

/**
 * 全局错误信息处理接口定义类
 *
 * @author Qianliang Zhang
 */
public interface IExceptionHandler {

    /**
     * 处理错误信息
     */
    void handleException();

    /**
     * 组合输出响应的错误消息
     *
     * @param response  响应
     * @param exception 错误
     */
    default void composeResponse(Response response, Exception exception) {
        if (exception instanceof RouteException) {
            RouteException routeException = (RouteException) exception;
            response.status(routeException.getResponseCode());
            response.body(formatErrorMsg(routeException.getErrorCode(), routeException.getErrorMessage()));
        } else {
            response.status(400);
            response.body(formatErrorMsg(IRouter.UNKNOWN_ERROR_CODE, IRouter.UNKNOWN_ERROR_MSG + exception.getMessage()));
        }
    }

    /**
     * 组合错误消息
     *
     * @param errorCode 错误代码
     * @param errorMsg  错误信息
     * @return 组合后的错误消息
     */
    String formatErrorMsg(int errorCode, String errorMsg);
}
