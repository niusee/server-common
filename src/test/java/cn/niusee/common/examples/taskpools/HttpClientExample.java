/*
 * Niusee server-common
 *
 * Copyright 2015-2020 by Niusee.inc. All rights reserved.
 */
package cn.niusee.common.examples.taskpools;

import cn.niusee.common.httpclient.SingletonHttpClient;
import cn.niusee.common.taskpools.ITask;
import cn.niusee.common.taskpools.OnTaskCallback;
import cn.niusee.common.taskpools.SimpleTaskExecutors;
import okhttp3.Response;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * HttpClientExample.java
 *
 * @author Qianliang Zhang
 */
public class HttpClientExample {

    private static class GetTask implements ITask {

        private final String url;

        GetTask(String url) {
            this.url = url;
        }

        @Override
        public boolean run() {
            try (Response response = SingletonHttpClient.getInstance().get(url)) {
                System.out.println("GetTask: " + response.body().string());
                return response.isSuccessful();
            } catch (IOException e) {
                e.printStackTrace();
                return false;
            }
        }

        @Override
        public void cancel() {

        }
    }

    private static class PostTask implements ITask {

        private final String url;

        PostTask(String url) {
            this.url = url;
        }

        @Override
        public boolean run() {
            try (Response response = SingletonHttpClient.getInstance().postForm(url, "")) {
                System.out.println("PostTask: " + response.body().string());
                return response.isSuccessful();
            } catch (IOException e) {
                e.printStackTrace();
                return false;
            }
        }

        @Override
        public void cancel() {

        }
    }

    public static void main(String[] args) {
        SimpleTaskExecutors taskExecutors = new SimpleTaskExecutors("HttpClient", 5);
        for (int i = 0; i < 1000; i++) {
            taskExecutors.executeTaskWithoutCallback(new GetTask(
                            "http://campusno1.niusee.cn/game2/server/game_calc.php?do=get"));
            taskExecutors.executeTaskWithoutCallback(new PostTask(
                    "http://campusno1.niusee.cn/game2/server/game_calc.php?do=add"));
        }
    }
}
