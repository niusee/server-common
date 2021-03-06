/*
 * Niusee live-server
 *
 * Copyright 2015-2018 by Niusee.inc. All rights reserved.
 */
package cn.niusee.common.route.exception;

/**
 * 自定义的服务器繁忙错误类
 *
 * @author Qianliang Zhang
 */
public class DefinedServer503Exception extends Server503Exception {

    public DefinedServer503Exception(ErrorMessage error) {
        this(error, null);
    }

    public DefinedServer503Exception(ErrorMessage error, String extraMessage) {
        super(error.getErrorCode(), error.getErrorMsg() + (extraMessage == null ? "" : extraMessage));
    }
}
