/*
 * Niusee server-common
 *
 * Copyright 2015-2017 by Niusee.inc. All rights reserved.
 */
package cn.niusee.common.test.utils;

import com.google.gson.*;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

/**
 * 添加描述:
 *
 * @author Qianliang Zhang
 */
public class TestGson {

    static class Container<T> {
        public Class<T> clazz;

        Container(Class<T> clazz) {
            this.clazz = clazz;
        }
    }

    static class MyJsonDeserializer<T> implements JsonDeserializer {

        private Class<T> clazz;

        MyJsonDeserializer(Class<T> clazz) {
            this.clazz = clazz;
        }

        @Override
        public T deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
            return new Gson().fromJson(json, typeOfT);
        }
    }

    public static void main(String[] args) {
        GsonBuilder gsonBuilder = new GsonBuilder();
        List<Container> dataList = new ArrayList<>();
        for (Container container : dataList) {
            gsonBuilder.registerTypeHierarchyAdapter(container.clazz, new MyJsonDeserializer<>(container.clazz));
        }
    }
}
