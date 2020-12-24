/*
 * Niusee server-common
 *
 * Copyright 2015-2016 by Niusee.inc. All rights reserved.
 */
package cn.niusee.common.media.util;

import cn.niusee.common.media.Media;
import cn.niusee.common.media.SimpleMediaInfo;
import com.google.gson.Gson;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

/**
 * 简易媒体文件信息读取工具类
 *
 * @author Qianliang Zhang
 */
public final class SimpleMediaInfoUtils {

    // 防止继承
    private SimpleMediaInfoUtils() {
        throw new RuntimeException("No implements");
    }

    /**
     * ffprobe的路径
     */
    public static String FFPROBE_PATH = "ffprobe";

    /**
     * 使用Ffprobe分析出指定视频的简易媒体信息
     *
     * @param url 指定的视频地址
     * @return 简易的媒体信息
     */
    public static SimpleMediaInfo analyzeStreamInfo(String url) {
        return new SimpleMediaInfo(analyzeMedia(url));
    }

    /**
     * 使用FFprobe分析指定视频的媒体信息
     *
     * @param url 指定的视频地址
     * @return 媒体信息
     */
    public static Media analyzeMedia(String url) {
        // Ffprobe 运行命令
        List<String> command = new ArrayList<>();
        command.add(FFPROBE_PATH);
        command.add("-loglevel");
        command.add("fatal");
        command.add("-print_format");
        command.add("json");
        command.add("-show_format");
        command.add("-show_streams");
        command.add(url);

        ProcessBuilder pb = new ProcessBuilder(command).directory(null);
        pb.redirectErrorStream(true);
        Process p = null;

        // 是否成功
        boolean success = false;
        // 收集Ffprobe输出的信息
        StringBuilder sb = new StringBuilder();
        // 记录获取错误的时候的信息
        String errorMessage = null;
        try {
            p = pb.start();
            BufferedReader br = new BufferedReader(new InputStreamReader(p.getInputStream()));
            String message;
            try {
                while ((message = br.readLine()) != null) {
                    // 收集命令返回信息
                    sb.append(message);
                    errorMessage = message;
                }
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                try {
                    br.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            try {
                success = p.waitFor() == 0;
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (p != null) {
                p.destroy();
            }
            command.clear();
        }

        // 成功
        if (success) {
            try {
                return parseMedia(sb.toString());
            } catch (Exception e) {
                e.printStackTrace();
                throw new RuntimeException("invalid stream data");
            }
        } else {
            throw new RuntimeException("open: " + url + " error: " + errorMessage);
        }
    }

    /**
     * 把Ffprobe输出的文件信息解析出媒体信息类
     *
     * @param meta Ffprobe输出的文件信息
     * @return 媒体信息类
     */
    private static Media parseMedia(String meta) {
        Gson gson = new Gson();
        return gson.fromJson(meta, Media.class);
    }
}
