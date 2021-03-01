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
 * HttpPoolTest.java
 *
 * @author Qianliang Zhang
 */
public class HttpPoolExample {

    private static class TaskHttpTask implements ITask {

        private final String contentUrl;

        TaskHttpTask(String contentUrl) {
            this.contentUrl = contentUrl;
        }

        @Override
        public boolean run() {
//            try {
//                Thread.sleep(2000);
//            } catch (InterruptedException e) {
//                e.printStackTrace();
//            }
            Map<String, String> headers = new HashMap<>(2);
            headers.put("x-school-domain", "nsjky");
            headers.put("x-user-type", "portal_user");
            try (Response response = SingletonHttpClient.getInstance().get(headers, contentUrl)) {
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
        String[] ids = new String[]{"11682", "11691"};
        String cate = "live"; //micro-lesson
        SimpleTaskExecutors taskExecutors = new SimpleTaskExecutors("Test1", 1);
        for (int i = 0; i < ids.length * 8000; i++) {
            taskExecutors.executeTask(new TaskHttpTask(
                            "https://v3.portal.niusee.cn/api/recommend/content/" + ids[i % ids.length]
                                    + "?self=niusee2020&category=" + cate + "&count=4"),
                    new OnTaskCallback() {
                        @Override
                        public void onTaskStart(ITask task) {

                        }

                        @Override
                        public void onTaskSuccess(ITask task) {
                            System.out.println("onTaskSuccess");
                        }

                        @Override
                        public void onTaskFail(ITask task) {
                            System.err.println("onTaskFail");
                        }
                    });
        }
    }
}
