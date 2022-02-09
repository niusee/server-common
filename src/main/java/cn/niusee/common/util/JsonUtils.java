/*
 * Niusee server-common
 *
 * Copyright 2015-2022 by Niusee.inc. All rights reserved.
 */
package cn.niusee.common.util;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonSyntaxException;

/**
 * Json工具类
 *
 * @author Qianliang Zhang
 */
public class JsonUtils {

    // 防止继承
    private JsonUtils() {
        throw new RuntimeException("No implements");
    }

    public static String toJson(Object obj) {
        GsonBuilder builder = new GsonBuilder();
        builder.excludeFieldsWithoutExposeAnnotation();
        Gson gson = builder.create();
        return gson.toJson(obj);
    }

    public static <T> T fromJson(String json, Class<T> classOfT) throws JsonSyntaxException {
        GsonBuilder builder = new GsonBuilder();
        builder.excludeFieldsWithoutExposeAnnotation();
        Gson gson = builder.create();
        return gson.fromJson(json, classOfT);
    }
}
