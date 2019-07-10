/*
 * Niusee live-server
 *
 * Copyright 2015-2018 by Niusee.inc. All rights reserved.
 */
package cn.niusee.common.route.exception;

/**
 * 自定义的用户请求参数错误类
 *
 * @author Qianliang Zhang
 */
public class DefinedUser400Exception extends RouteException {

    public DefinedUser400Exception(ErrorMessage error) {
        this(error, null);
    }

    public DefinedUser400Exception(ErrorMessage error, String extraMessage) {
        super(400, error.getErrorCode(), error.getErrorMsg() + (extraMessage == null ? "" : extraMessage));
    }
}
