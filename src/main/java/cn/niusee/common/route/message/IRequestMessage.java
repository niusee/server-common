/*
 * Niusee server-common
 *
 * Copyright 2015-2017 by Niusee.inc. All rights reserved.
 */
package cn.niusee.common.route.message;

import cn.niusee.common.route.common.IllegalRequestParamException;

/**
 * 请求消息体定义类
 *
 * @author Qianliang Zhang
 */
public interface IRequestMessage {

    /**
     * 检查消息是否有效。如果无效，会抛出{@link IllegalRequestParamException}
     */
    void checkValid();
}
