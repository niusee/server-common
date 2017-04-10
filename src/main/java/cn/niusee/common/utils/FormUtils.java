/*
 * Niusee server-common
 *
 * Copyright 2015-2016 by Niusee.inc. All rights reserved.
 */
package cn.niusee.common.utils;

import java.util.HashMap;
import java.util.Map;

/**
 * x-www-url-encoded From请求体解析工具类
 *
 * @author Qianliang Zhang
 */
public final class FormUtils {
    /**
     * 防止继承
     */
    private FormUtils() {
        throw new RuntimeException("No implements");
    }

    /**
     * 解析x-www-url-encoded From请求体
     *
     * @param body x-www-url-encoded From请求体
     * @return 参数集合
     */
    public static Map<String, String> parseForm(String body) {
        String[] paramPairs = body.split("&");
        Map<String, String> paramsMap = new HashMap<>(paramPairs.length);
        for (String paramPair : paramPairs) {
            String[] params = paramPair.split("=");
            if (params.length > 0) {
                if (params.length > 1) {
                    paramsMap.put(params[0], params[1]);
                } else {
                    paramsMap.put(params[0], null);
                }
            }
        }
        return paramsMap;
    }
}
