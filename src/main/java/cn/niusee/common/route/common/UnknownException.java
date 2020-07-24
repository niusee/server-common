/*
 * Niusee live-server
 *
 * Copyright 2015-2017 by Niusee.inc. All rights reserved.
 */
package cn.niusee.common.route.common;

import cn.niusee.common.route.exception.DefinedUser400Exception;

/**
 * 位置错误的错误
 *
 * @author Qianliang Zhang
 */
public class UnknownException extends DefinedUser400Exception {

    public UnknownException() {
        super(CommonError.UNKNOWN_ERROR);
    }
}
