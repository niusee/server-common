/*
 * Niusee server-common
 *
 * Copyright 2015-2017 by Niusee.inc. All rights reserved.
 */
package cn.niusee.common.router;

/**
 * 服务错误抛出
 *
 * @author Qianliang Zhang
 */
public class RouteException extends Exception {

    public RouteException() {
        super();
    }

    public RouteException(String message) {
        super(message);
    }
}
