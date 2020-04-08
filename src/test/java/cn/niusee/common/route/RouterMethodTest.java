/*
 * Niusee server-common
 *
 * Copyright 2015-2018 by Niusee.inc. All rights reserved.
 */
package cn.niusee.common.route;

import static spark.Spark.options;
import static spark.Spark.post;

/**
 * 路由方法测试
 *
 * @author Qianliang Zhang
 */
public class RouterMethodTest {

    public static void main(String[] args) {
        options("book/:bookId", (request, response) -> {
            String bookId = request.params(":bookId");
            System.err.println("book id: " + bookId);
            System.err.println(request.body());
            return "";
        });

        post("book/:bookId", (request, response) -> {
            String bookId = request.params(":bookId");
            System.err.println("book id: " + bookId);
            System.err.println(request.body());
            return "{\"success\":true}";
        });
    }
}
