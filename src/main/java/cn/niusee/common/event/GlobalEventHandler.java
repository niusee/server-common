/*
 * Niusee vod-server
 *
 * Copyright 2015-2020 by Niusee.inc. All rights reserved.
 */
package cn.niusee.common.event;

import com.google.common.eventbus.EventBus;

/**
 * 全局消息事件处理类
 *
 * @author Qianliang Zhang
 */
@SuppressWarnings("UnstableApiUsage")
public class GlobalEventHandler {

    /**
     * EventBus处理
     */
    private final EventBus eventBus;

    public GlobalEventHandler() {
        eventBus = new EventBus();
    }

    /**
     * 注册监听
     *
     * @param listener 监听类
     * @param <T>      监听类泛类限定
     */
    public <T extends IEventListener> void register(T listener) {
        eventBus.register(listener);
    }

    /**
     * 解除监听
     *
     * @param listener 监听类
     * @param <T>      监听类泛类限定
     */
    public <T extends IEventListener> void unregister(T listener) {
        eventBus.unregister(listener);
    }

    /**
     * 发送消息
     *
     * @param event 消息
     * @param <T>   消息泛类限定
     */
    public <T extends IEvent> void post(T event) {
        eventBus.post(event);
    }
}
