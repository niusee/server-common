/*
 * Niusee server-common
 *
 * Copyright 2015-2017 by Niusee.inc. All rights reserved.
 */
package cn.niusee.common.route;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

import static spark.Spark.*;

/**
 * FORM方式数据的全局错误处理类
 *
 * @author Qianliang Zhang
 */
public class DefaultFromExceptionHandler implements IExceptionHandler {

    @Override
    public void handleException() {
        // 错误处理
        exception(Exception.class, (exception, request, response) -> composeResponse(response, exception));
        // 全局后处理
        after((request, response) -> response.type("application/www-form-urlencoded"));
        // 没有路由的报错
        notFound((req, res) -> {
            res.type("application/www-form-urlencoded");
            return formatErrorMsg(IRouter.NOT_FOUND_ERROR_CODE, IRouter.NOT_FOUND_ERROR_MSG);
        });
    }

    @Override
    public String formatErrorMsg(int errorCode, String errorMsg) {
        String encodeMsg = "";
        try {
            encodeMsg = URLEncoder.encode(errorMsg, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return "error_code=" + errorCode + "&error_msg=" + encodeMsg;
    }
}
