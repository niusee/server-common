/*
 * Niusee server-common
 *
 * Copyright 2015-2017 by Niusee.inc. All rights reserved.
 */
package cn.niusee.common.route;

import static spark.Spark.after;
import static spark.Spark.exception;

/**
 * JSON方式数据的全局错误处理类
 *
 * @author Qianliang Zhang
 */
public class DefaultJsonExceptionHandler implements IExceptionHandler {

    @Override
    public void handleException() {
        // 错误处理
        exception(Exception.class, (exception, request, response) -> composeResponse(response, exception));
        // 全局后处理
        after((request, response) -> response.type("application/json"));
    }

    @Override
    public String formatErrorMsg(int errorCode, String errorMsg) {
        return "{\"error_code\":" + errorCode + ",\"error_msg\":\"" + errorMsg + "\"}";
    }
}
