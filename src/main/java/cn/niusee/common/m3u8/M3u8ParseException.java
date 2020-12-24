/*
 * Niusee server-common
 *
 * Copyright 2015-2020 by Niusee.inc. All rights reserved.
 */
package cn.niusee.common.m3u8;

/**
 * M3U8文件解析错误
 *
 * @author Qianliang Zhang
 */
public class M3u8ParseException extends Exception {
    /**
     * 解析错误出现的行信息
     */
    private final String line;

    /**
     * 解析错误出现的行数
     */
    private final int lineNumber;

    public M3u8ParseException(String line, int lineNumber, Throwable cause) {
        super(cause);
        this.line = line;
        this.lineNumber = lineNumber;
    }

    public M3u8ParseException(String line, int lineNumber, String message) {
        super(message);
        this.line = line;
        this.lineNumber = lineNumber;
    }

    /**
     * 获取解析错误出现的行信息
     *
     * @return 解析错误出现的行信息
     */
    public String getLine() {
        return line;
    }

    /**
     * 获取解析错误出现的行数
     *
     * @return 解析错误出现的行数
     */
    public int getLineNumber() {
        return lineNumber;
    }

    @Override
    public String getMessage() {
        return "Error at line " + getLineNumber() + ": " + getLine() + "\n" + super.getMessage();
    }
}