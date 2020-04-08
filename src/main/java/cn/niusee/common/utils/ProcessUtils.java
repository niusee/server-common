/*
 * Niusee server-common
 *
 * Copyright 2015-2019 by Niusee.inc. All rights reserved.
 */
package cn.niusee.common.utils;

import java.lang.management.ManagementFactory;

/**
 * 进程工具类
 *
 * @author Qianliang Zhang
 */
public final class ProcessUtils {

    /**
     * 获取进程的ID
     *
     * @return 进程的ID
     */
    public static String getPid() {
        return ManagementFactory.getRuntimeMXBean().getName().split("@")[0];
    }

    /**
     * 获取系统名称。有：win、mac、linux
     *
     * @return 系统名称
     */
    public static String getOs() {
        String osName = System.getProperty("os.name").toLowerCase();
        if (isWindows(osName)) {
            return "win";
        } else if (isMac(osName)) {
            return "mac";
        } else {
            return "linux";
        }
    }

    /**
     * 获取Shell运行环境命令
     *
     * @return Shell运行环境命令
     */
    public static String[] getShellCommands() {
        String os = getOs();
        if ("win".equals(os)) {
            return new String[]{"cmd.exe", "/c"};
        }
        return new String[]{"sh", "-c"};
    }

    /**
     * 是否是window系统
     *
     * @param os 系统名称
     * @return 是否是window系统
     */
    public static boolean isWindows(String os) {
        return (os.contains("win"));
    }

    /**
     * 是否是mac系统
     *
     * @param os 系统名称
     * @return 是否是mac系统
     */
    public static boolean isMac(String os) {
        return (os.contains("mac"));
    }

    /**
     * 是否是unix系统
     *
     * @param os 系统名称
     * @return 是否是unix系统
     */
    public static boolean isUnix(String os) {
        return (os.contains("nix") || os.contains("nux") || os.contains("aix"));
    }

}
