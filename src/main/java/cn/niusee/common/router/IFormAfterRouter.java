/*
 * Niusee server-common
 *
 * Copyright 2015-2017 by Niusee.inc. All rights reserved.
 */
package cn.niusee.common.router;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

import static spark.Spark.after;
import static spark.Spark.exception;

/**
 * 返回FORM消息的全局After控制的Router
 *
 * @author Qianliang Zhang
 */
public interface IFormAfterRouter extends IRouter {
    /**
     * 全局服务的后处理
     */
    default void afterHandler() {
        // 错误处理
        exception(Exception.class, (exception, request, response) -> {
            response.type("application//www-form-urlencoded");
            if (exception instanceof RouteException) {
                if (exception instanceof User400Exception) {
                    response.status(400);
                } else if (exception instanceof Server500Exception) {
                    response.status(500);
                }
                RouteException routeException = (RouteException) exception;
                response.body(combineErrorMsg(routeException.getErrorCode(), routeException.getErrorMessage()));
            } else {
                response.status(400);
                response.body(combineErrorMsg(UNKNOWN_ERROR_CODE, UNKNOWN_ERROR_MSG + exception.getMessage()));
            }
        });

        // 全局后处理
        after((request, response) -> response.type("application//www-form-urlencoded"));
    }

    /**
     * 组合错误消息
     *
     * @param errorCode 错误代码
     * @param errorMsg  错误信息
     * @return 组合后的错误消息
     */
    default String combineErrorMsg(int errorCode, String errorMsg) {
        String encodeMsg = "";
        try {
            encodeMsg = URLEncoder.encode(errorMsg, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return "error_code=" + errorCode + "&error_msg=" + encodeMsg;
    }
}
