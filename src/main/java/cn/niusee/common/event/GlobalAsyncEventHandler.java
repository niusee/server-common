/*
 * Niusee vod-server
 *
 * Copyright 2015-2020 by Niusee.inc. All rights reserved.
 */
package cn.niusee.common.event;

import com.google.common.eventbus.AsyncEventBus;

/**
 * 全局异步消息事件处理类
 *
 * @author Qianliang Zhang
 */
@SuppressWarnings("UnstableApiUsage")
public class GlobalAsyncEventHandler {

    /**
     * AsyncEventBus处理
     */
    private final AsyncEventBus asyncEventBus;

    private GlobalAsyncEventHandler() {
        asyncEventBus = new AsyncEventBus(command -> new Thread(command).start());
    }

    /**
     * 单例实例化
     */
    private static GlobalAsyncEventHandler instance = new GlobalAsyncEventHandler();

    /**
     * 获取配置单例
     *
     * @return 配置单例
     */
    public static GlobalAsyncEventHandler instance() {
        return instance;
    }

    /**
     * 注册监听
     *
     * @param listener 监听类
     * @param <T>      监听类泛类限定
     */
    public <T extends IEventListener> void register(T listener) {
        asyncEventBus.register(listener);
    }

    /**
     * 解除监听
     *
     * @param listener 监听类
     * @param <T>      监听类泛类限定
     */
    public <T extends IEventListener> void unregister(T listener) {
        asyncEventBus.unregister(listener);
    }

    /**
     * 异步发送消息
     *
     * @param event 消息
     * @param <T>   消息泛类限定
     */
    public <T extends IEvent> void asyncPost(T event) {
        asyncEventBus.post(event);
    }
}
