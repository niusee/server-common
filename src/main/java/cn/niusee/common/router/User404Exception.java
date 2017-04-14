/*
 * Niusee server-common
 *
 * Copyright 2015-2017 by Niusee.inc. All rights reserved.
 */
package cn.niusee.common.router;

/**
 * 用户请求不存在的错误抛出类
 *
 * @author Qianliang Zhang
 */
public class User404Exception extends RouteException {

    public User404Exception(int errorCode, String message) {
        super(404, errorCode, message);
    }
}
