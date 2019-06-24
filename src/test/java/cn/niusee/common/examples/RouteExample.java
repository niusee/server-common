/*
 * Niusee server-common
 *
 * Copyright 2015-2019 by Niusee.inc. All rights reserved.
 */
package cn.niusee.common.examples;

import cn.niusee.common.logger.LoggerHelper;
import spark.Spark;
import spark.utils.StringUtils;

import java.util.Arrays;

/**
 * Route examples
 *
 * @author Qianliang Zhang
 */
public class RouteExample {

    private final static LoggerHelper log = new LoggerHelper(RouteExample.class);

    public static void main(String[] args) {
        Spark.get("/*", (request, response) -> {
            log.debug("path: {}", request.pathInfo());
            String[] pathInfo = request.pathInfo().split("/");
            pathInfo = Arrays.stream(pathInfo).filter(StringUtils::isNotEmpty).toArray(String[]::new);
            String school = pathInfo.length > 1 ? pathInfo[1] : null;
            log.debug("load school: {}", school);
            if (StringUtils.isEmpty(school)) {
                response.redirect("http://www.niusee.cn", 301);
                return null;
            }
            response.header("Cache-Control", "no-cache");
            response.header("X-Frame-Options", "DENY");
            response.type("text/html");
            return "<!DOCTYPE html>" +
                    "<html>" +
                    "<head>" +
                    "<link rel=\"stylesheet\" href=\"http://static.z.me/frontend/umi.337d45d0.css\">" +
                    "<meta charset=\"utf-8\">" +
                    "<meta name=\"viewport\" content=\"width=device-width,initial-scale=1,maximum-scale=1,minimum-scale=1,user-scalable=no\">" +
                    "<script>window.routerBase = \"/frontend/" + school + "/\";</script>" +
                    "</head>" +
                    "<body>" +
                    "<div id=\"root\"></div>" +
                    "<script src=\"http://static.z.me/frontend/umi.b4620b1d.js\"></script>" +
                    "</body>" +
                    "</html>";
        });
    }
}
