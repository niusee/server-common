/*
 * Niusee server-common
 *
 * Copyright 2015-2020 by Niusee.inc. All rights reserved.
 */
package cn.niusee.common.event;

/**
 * 单例全局消息事件处理类
 *
 * @author Qianliang Zhang
 */
public class SingleGlobalEventHandler extends GlobalEventHandler {

    private SingleGlobalEventHandler() {
        super();
    }

    /**
     * 单例实例化
     */
    private static SingleGlobalEventHandler instance = new SingleGlobalEventHandler();

    /**
     * 获取配置单例
     *
     * @return 配置单例
     */
    public static SingleGlobalEventHandler instance() {
        return instance;
    }
}
