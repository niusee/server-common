/*
 * Niusee live-server
 *
 * Copyright 2015-2017 by Niusee.inc. All rights reserved.
 */
package cn.niusee.common.route.common;

import cn.niusee.common.route.exception.DefinedUser400Exception;

/**
 * 服务器过载的错误
 *
 * @author Qianliang Zhang
 */
public class ServerBusyException extends DefinedUser400Exception {

    public ServerBusyException() {
        super(CommonError.SERVER_BUSY);
    }
}
