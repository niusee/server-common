/*
 * Niusee server-common
 *
 * Copyright 2015-2017 by Niusee.inc. All rights reserved.
 */
package cn.niusee.common.router;

/**
 * 服务器（程序）错误抛出类
 *
 * @author Qianliang Zhang
 */
public class Server500Exception extends RouteException {

    Server500Exception(int errorCode, String message) {
        super(errorCode, message);
    }
}
