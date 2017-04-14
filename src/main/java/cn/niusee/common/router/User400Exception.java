/*
 * Niusee server-common
 *
 * Copyright 2015-2017 by Niusee.inc. All rights reserved.
 */
package cn.niusee.common.router;

/**
 * 用户操作错误的错误抛出类
 *
 * @author Qianliang Zhang
 */
public class User400Exception extends RouteException {

    public User400Exception(int errorCode, String message) {
        super(errorCode, message);
    }
}
