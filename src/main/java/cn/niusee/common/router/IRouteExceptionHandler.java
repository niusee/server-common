/*
 * Niusee vod-server
 *
 * Copyright 2015-2017 by Niusee.inc. All rights reserved.
 */
package cn.niusee.common.router;

/**
 * 服务抛出错误处理器
 *
 * @author Qianliang Zhang
 */
public interface IRouteExceptionHandler {
    /**
     * 错误处理结果
     *
     * @param exception 服务抛出错误
     */
    ExceptionHandleResult handle(Exception exception);

    /**
     * 组合错误消息
     *
     * @param errorCode 错误代码
     * @param errorMsg  错误信息
     * @return 组合后的错误消息
     */
    default String combineErrorMsg(int errorCode, String errorMsg) {
        return "{\"error_code\":" + errorCode + ",\"error_msg\":\"" + errorMsg + "\"}";
    }
}
