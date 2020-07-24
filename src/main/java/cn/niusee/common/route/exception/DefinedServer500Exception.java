/*
 * Niusee live-server
 *
 * Copyright 2015-2018 by Niusee.inc. All rights reserved.
 */
package cn.niusee.common.route.exception;

/**
 * 自定义的服务器错误类
 *
 * @author Qianliang Zhang
 */
public class DefinedServer500Exception extends Server500Exception {

    public DefinedServer500Exception(ErrorMessage error) {
        this(error, null);
    }

    public DefinedServer500Exception(ErrorMessage error, String extraMessage) {
        super(error.getErrorCode(), error.getErrorMsg() + (extraMessage == null ? "" : extraMessage));
    }
}
