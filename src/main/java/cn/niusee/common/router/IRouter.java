/*
 * Niusee server-common
 *
 * Copyright 2015-2017 by Niusee.inc. All rights reserved.
 */
package cn.niusee.common.router;

/**
 * 服务路由接口定义类
 *
 * @author Qianliang Zhang
 */
public interface IRouter {
    /**
     * 没控制的错误代码
     */
    int UNKNOWN_ERROR_CODE = -4000;

    /**
     * 没控制的错误信息
     */
    String UNKNOWN_ERROR_MSG = "Unknown error, detail: ";

    /**
     * 路由启动方法
     */
    void route();
}
