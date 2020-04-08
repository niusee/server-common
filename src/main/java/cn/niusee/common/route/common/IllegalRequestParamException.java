/*
 * Niusee live-server
 *
 * Copyright 2015-2017 by Niusee.inc. All rights reserved.
 */
package cn.niusee.common.route.common;

import cn.niusee.common.route.exception.DefinedUser400Exception;

/**
 * 参数的值类型不对错误
 *
 * @author Qianliang Zhang
 */
public class IllegalRequestParamException extends DefinedUser400Exception {

    public IllegalRequestParamException() {
        super(CommonError.ILLEGAL_REQUEST_PARAM);
    }

    public IllegalRequestParamException(String extraMessage) {
        super(CommonError.ILLEGAL_REQUEST_PARAM, extraMessage != null ? ": " + extraMessage : null);
    }
}
