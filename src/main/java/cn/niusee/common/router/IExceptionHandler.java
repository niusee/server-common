/*
 * Niusee server-common
 *
 * Copyright 2015-2017 by Niusee.inc. All rights reserved.
 */
package cn.niusee.common.router;

/**
 * 全局错误信息处理接口定义类
 *
 * @author Qianliang Zhang
 */
public interface IExceptionHandler {

    /**
     * 处理错误信息
     */
    void handleException();
}
