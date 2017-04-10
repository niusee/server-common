/*
 * Niusee server-common
 *
 * Copyright 2015-2017 by Niusee.inc. All rights reserved.
 */
package cn.niusee.common.router;

/**
 * 错误处理结果类
 *
 * @author Qianliang Zhang
 */
public class ExceptionHandleResult {

    /**
     * 错误代码
     */
    public final int errorCode;

    /**
     * 错误信息
     */
    public final String errorMsg;

    public ExceptionHandleResult(int errorCode, String errorMsg) {
        this.errorCode = errorCode;
        this.errorMsg = errorMsg;
    }
}
