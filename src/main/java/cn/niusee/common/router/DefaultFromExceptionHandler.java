/*
 * Niusee server-common
 *
 * Copyright 2015-2017 by Niusee.inc. All rights reserved.
 */
package cn.niusee.common.router;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

import static cn.niusee.common.router.IRouter.UNKNOWN_ERROR_CODE;
import static cn.niusee.common.router.IRouter.UNKNOWN_ERROR_MSG;
import static spark.Spark.after;
import static spark.Spark.exception;

/**
 * FORM方式数据的全局错误处理类
 *
 * @author Qianliang Zhang
 */
public class DefaultFromExceptionHandler implements IExceptionHandler {

    @Override
    public void handleException() {
        // 错误处理
        exception(Exception.class, (exception, request, response) -> {
            response.type("application//www-form-urlencoded");
            if (exception instanceof RouteException) {
                RouteException routeException = (RouteException) exception;
                response.status(routeException.getResponseCode());
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
    private String combineErrorMsg(int errorCode, String errorMsg) {
        String encodeMsg = "";
        try {
            encodeMsg = URLEncoder.encode(errorMsg, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return "error_code=" + errorCode + "&error_msg=" + encodeMsg;
    }
}
