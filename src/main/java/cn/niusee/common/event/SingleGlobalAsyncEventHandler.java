/*
 * Niusee server-common
 *
 * Copyright 2015-2020 by Niusee.inc. All rights reserved.
 */
package cn.niusee.common.event;

/**
 * 单例全局异步消息事件处理类
 *
 * @author Qianliang Zhang
 */
public class SingleGlobalAsyncEventHandler extends GlobalAsyncEventHandler {

    private SingleGlobalAsyncEventHandler() {
        super();
    }

    /**
     * 单例实例化
     */
    private final static SingleGlobalAsyncEventHandler instance = new SingleGlobalAsyncEventHandler();

    /**
     * 获取配置单例
     *
     * @return 配置单例
     */
    public static SingleGlobalAsyncEventHandler instance() {
        return instance;
    }
}
