/*
 * Niusee live-server
 *
 * Copyright 2015-2018 by Niusee.inc. All rights reserved.
 */
package cn.niusee.common.route.exception;

/**
 * 自定义的资源不存在错误类
 *
 * @author Qianliang Zhang
 */
public class DefinedUser404Exception extends RouteException {

    public DefinedUser404Exception(ErrorMessage error) {
        this(error, null);
    }

    public DefinedUser404Exception(ErrorMessage error, String extraMessage) {
        super(404, error.getErrorCode(), error.getErrorMsg() + (extraMessage == null ? "" : extraMessage));
    }
}
