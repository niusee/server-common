/*
 * Niusee server-common
 *
 * Copyright 2015-2017 by Niusee.inc. All rights reserved.
 */
package cn.niusee.common.route.exception;

/**
 * 服务器（程序）内部出错的错误抛出类
 *
 * @author Qianliang Zhang
 */
public class Server500Exception extends RouteException {

    public Server500Exception(int errorCode, String message) {
        super(500, errorCode, message);
    }
}
