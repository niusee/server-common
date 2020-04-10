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

//    /**
//     * AsyncEventBus处理
//     */
//    private final AsyncEventBus asyncEventBus;

    private GlobalEventHandler() {
        eventBus = new EventBus();
//        asyncEventBus = new AsyncEventBus(command -> new Thread(command).start());
    }

    /**
     * 单例实例化
     */
    private static GlobalEventHandler instance = new GlobalEventHandler();

    /**
     * 获取配置单例
     *
     * @return 配置单例
     */
    public static GlobalEventHandler instance() {
        return instance;
    }

    /**
     * 注册监听
     *
     * @param listener 监听类
     * @param <T>      监听类泛类限定
     */
    public <T extends IEventListener> void register(T listener) {
        eventBus.register(listener);
//        asyncEventBus.register(listener);
    }

    /**
     * 解除监听
     *
     * @param listener 监听类
     * @param <T>      监听类泛类限定
     */
    public <T extends IEventListener> void unregister(T listener) {
        eventBus.unregister(listener);
//        asyncEventBus.unregister(listener);
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

//    /**
//     * 异步发送消息
//     *
//     * @param event 消息
//     * @param <T>   消息泛类限定
//     */
//    public <T extends IEvent> void asyncPost(T event) {
//        asyncEventBus.post(event);
//    }
}
