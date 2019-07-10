/*
 * Niusee server-common
 *
 * Copyright 2015-2017 by Niusee.inc. All rights reserved.
 */
package cn.niusee.common.route.util;

import cn.niusee.common.route.common.IllegalRequestParamException;
import cn.niusee.common.route.message.IRequestMessage;
import com.google.gson.Gson;

/**
 * 请求解释工具类
 *
 * @author Qianliang Zhang
 */
public final class RequestParseUtils {

    /**
     * 解释请求体到指定的信息对象类
     *
     * @param requestBody 请求体字符串
     * @param classOfT    信息对象类类型
     * @param <T>         信息对象类
     * @return 解释后的信息对象类
     */
    public static <T extends IRequestMessage> T parse(String requestBody, Class<T> classOfT) {
        T message;
        try {
            message = new Gson().fromJson(requestBody, classOfT);
        } catch (Exception e) {
            throw new IllegalRequestParamException();
        }
        if (message == null) {
            throw new IllegalRequestParamException();
        }
        message.checkValid();
        return message;
    }
}
