/*
 * Niusee server-common
 *
 * Copyright 2015-2017 by Niusee.inc. All rights reserved.
 */
package cn.niusee.common.route;

import static cn.niusee.common.route.IRouter.NOT_FOUND_ERROR_CODE;
import static cn.niusee.common.route.IRouter.NOT_FOUND_ERROR_MSG;
import static spark.Spark.*;

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
        // 没有路由的报错
        notFound((req, res) -> {
            res.type("application/json");
            return formatErrorMsg(NOT_FOUND_ERROR_CODE, NOT_FOUND_ERROR_MSG);
        });
    }

    @Override
    public String formatErrorMsg(int errorCode, String errorMsg) {
        return "{\"error_code\":" + errorCode + ",\"error_msg\":\"" + errorMsg + "\"}";
    }
}
