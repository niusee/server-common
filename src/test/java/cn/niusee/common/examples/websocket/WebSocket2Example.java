/*
 * Niusee server-common
 *
 * Copyright 2015-2021 by Niusee.inc. All rights reserved.
 */
package cn.niusee.common.examples.websocket;

import okhttp3.*;
import okio.ByteString;
import org.jetbrains.annotations.NotNull;

import java.util.concurrent.TimeUnit;

/**
 * WebSocketExample.java
 *
 * @author Qianliang Zhang
 */
public class WebSocket2Example extends WebSocketListener {

    private void run() {

        String streamId = "1839_box_A860B6219AB7";

        OkHttpClient client = new OkHttpClient.Builder()
                .pingInterval(10, TimeUnit.SECONDS)
                .readTimeout(0, TimeUnit.MILLISECONDS)
                .build();

        Request request = new Request.Builder()
                .header("Connection", "Upgrade")
                .header("Upgrade", "Websocket")
                .url("ws://live.z.me:8080/ws/interact?stream_id=" + streamId)
                .build();
        client.newWebSocket(request, this);

        // Trigger shutdown of the dispatcher's executor so this process can exit cleanly.
        client.dispatcher().executorService().shutdown();
    }

    @Override
    public void onOpen(@NotNull WebSocket webSocket, @NotNull Response response) {
        System.out.println("Opened");
    }

    @Override
    public void onMessage(@NotNull WebSocket webSocket, @NotNull String text) {
        System.out.println("MESSAGE: " + text);
    }

    @Override
    public void onMessage(@NotNull WebSocket webSocket, ByteString bytes) {
        System.out.println("MESSAGE: " + bytes.hex());
    }

    @Override
    public void onClosing(@NotNull WebSocket webSocket, int code, @NotNull String reason) {
        System.out.println("CLOSE: " + code + " " + reason);
    }

    @Override
    public void onFailure(@NotNull WebSocket webSocket, Throwable t, Response response) {
        System.out.println("Fail");
        t.printStackTrace();
    }

    public static void main(String... args) {
        new WebSocket2Example().run();
    }
}