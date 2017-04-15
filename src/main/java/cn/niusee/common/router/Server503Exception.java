/*
 * Niusee server-common
 *
 * Copyright 2015-2017 by Niusee.inc. All rights reserved.
 */
package cn.niusee.common.router;

/**
 * 服务器（程序）繁忙的错误抛出类
 *
 * @author Qianliang Zhang
 */
public class Server503Exception extends RouteException {

    public Server503Exception(int errorCode, String message) {
        super(503, errorCode, message);
    }
}
