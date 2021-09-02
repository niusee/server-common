/*
 * Niusee server-common
 *
 * Copyright 2015-2017 by Niusee.inc. All rights reserved.
 */
package cn.niusee.common.utils;

import java.text.DecimalFormat;
import java.util.regex.Pattern;

/**
 * 数字工具类
 *
 * @author Qianliang Zhang
 */
public final class NumberUtils {

    // 防止继承
    private NumberUtils() {
        throw new RuntimeException("No implements");
    }

    /**
     * 判断是否为整数
     *
     * @param str 传入的字符串
     * @return 是整数返回true, 否则返回false
     */
    public static boolean isInteger(String str) {
        Pattern pattern = Pattern.compile("^[-+]?[\\d]*$");
        return pattern.matcher(str).matches();
    }

    /**
     * 判断是否为浮点数，包括double和float
     *
     * @param str 传入的字符串
     * @return 是浮点数返回true, 否则返回false
     */
    public static boolean isDouble(String str) {
        Pattern pattern = Pattern.compile("^[-+]?[.\\d]*$");
        return pattern.matcher(str).matches();
    }

    /**
     * 判断输入的数字是否是奇数
     *
     * @param number 输入的数字
     * @return 是否是奇数
     */
    public static boolean isOdd(int number) {
        return (number & 1) == 1;
    }

    /**
     * 判断在误差允许范围，a是否大于b
     *
     * @param a     double类型的数字A
     * @param b     double类型的数字B
     * @param delta 误差允许范围
     * @return 是否大于
     */
    public static boolean isBiggerThan(double a, double b, double delta) {
        double realDelta = a - b;
        return realDelta > delta;
    }

    /**
     * 格式化浮点数的小数点为下划线
     *
     * @param number 浮点数
     * @return 格式化输出后的字符串
     */
    public static String formatDoubleDotWithUnderline(double number) {
        return Double.toString(number).replace(".", "_");
    }

    /**
     * 格式化浮点数的小数点为空
     *
     * @param number 浮点数
     * @return 格式化输出后的字符串
     */
    public static String formatDoubleDotWithBlank(double number) {
        return Double.toString(number).replace(".", "");
    }

    /**
     * 浮点数保留三位小数点
     *
     * @param num 输入浮点数数据
     * @return 三位小数点浮点数
     */
    public static double roundDoubleWith3Decimal(double num) {
        DecimalFormat df = new DecimalFormat("0.000");
        return Double.parseDouble(df.format(num));
    }
}
