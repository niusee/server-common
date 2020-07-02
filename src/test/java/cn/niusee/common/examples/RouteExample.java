/*
 * Niusee server-common
 *
 * Copyright 2015-2019 by Niusee.inc. All rights reserved.
 */
package cn.niusee.common.examples;

import spark.Spark;

/**
 * Route examples
 *
 * @author Qianliang Zhang
 */
public class RouteExample {

    public static void main(String[] args) {
        Spark.port(8899);
        Spark.get("/hls/:id", (request, response) -> {
            String id = request.params(":id");
            System.err.println(id);
            return "";
        });
    }
}
