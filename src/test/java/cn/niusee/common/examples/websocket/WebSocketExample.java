/*
 * Niusee server-common
 *
 * Copyright 2015-2021 by Niusee.inc. All rights reserved.
 */
package cn.niusee.common.examples.websocket;

import org.eclipse.jetty.websocket.api.Session;
import org.eclipse.jetty.websocket.api.annotations.*;
import org.eclipse.jetty.websocket.client.WebSocketClient;

import java.net.URI;

/**
 * WebSocketExample.java
 *
 * @author Qianliang Zhang
 */
public class WebSocketExample {

    @WebSocket(maxTextMessageSize = 64 * 1024)
    public static class SimpleEchoSocket
    {
        @SuppressWarnings("unused")
        private Session session;

        public SimpleEchoSocket()
        {

        }

        @OnWebSocketClose
        public void onClose(int statusCode, String reason)
        {
            System.err.printf("Connection closed: %d - %s%n", statusCode, reason);
            this.session = null;
        }

        @OnWebSocketConnect
        public void onConnect(Session session)
        {
            System.err.printf("Got connect: %s%n", session);
            this.session = session;
        }

        @OnWebSocketError
        public void onError(Throwable cause)
        {
            System.err.println("WebSocket Error: " + cause.getMessage());
            cause.printStackTrace();
        }

        @OnWebSocketMessage
        public void onMessage(String msg)
        {
            System.err.printf("Got msg: %s%n", msg);
        }
    }

    public static void main(String... args) throws Exception {
        WebSocketClient wsClient = new WebSocketClient();
        wsClient.start();
        wsClient.connect(new SimpleEchoSocket(), new URI("ws://live.z.me:8080/ws/interact?stream_id=2030_box_A860B6219AB7"));
        System.out.println("Connecting");
    }
}