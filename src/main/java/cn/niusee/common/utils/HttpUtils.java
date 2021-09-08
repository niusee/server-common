/*
 * Niusee server-common
 *
 * Copyright 2015-2021 by Niusee.inc. All rights reserved.
 */
package cn.niusee.common.utils;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.ResponseBody;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * URL工具类
 *
 * @author Qianliang Zhang
 */
public class HttpUtils {

    // 防止继承
    private HttpUtils() {

    }

    /**
     * 获取给定URL的Authority
     *
     * @param url 给定URL
     * @return Authority地址
     */
    public static String getAuthority(String url) {
        return url.replace("http://", "").replace("https://", "")
                .split("/")[0];
    }

    /**
     * 获取给定URL的Authority
     *
     * @param url 给定URL
     * @return Authority地址
     */
    public static String getAuthority2(String url) {
        String returnVal = "";
        try {
            URL requestUrl = new URL(url);
            returnVal = requestUrl.getAuthority();
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        return returnVal;
    }

    /**
     * 获取给定URL的Host
     *
     * @param url 给定URL
     * @return HOST地址
     */
    public static String getHost(String url) {
        return url.replace("http://", "").replace("https://", "")
                .split("/")[0].split(":")[0];
    }

    /**
     * 获取给定URL的Host
     *
     * @param url 给定URL
     * @return HOST地址
     */
    public static String getHost2(String url) {
        String returnVal = "";
        try {
            URL requestUrl = new URL(url);
            returnVal = requestUrl.getHost();
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        return returnVal;
    }

    /**
     * 获取给定URL的Port
     *
     * @param url 给定URL
     * @return URL的Port
     */
    public static int getPort(String url) {
        String authority = getAuthority(url);
        if (authority.contains(":")) {
            return Integer.parseInt(authority.split(":")[1]);
        }
        return 80;
    }

    /**
     * 获取给定URL的Port
     *
     * @param url 给定URL
     * @return URL的Port
     */
    public static int getPort2(String url) {
        try {
            URL requestUrl = new URL(url);
            int port = requestUrl.getPort();
            return port <= 0 ? 80 : port;
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        return 80;
    }

    /**
     * 获取给定URL的Path
     *
     * @param url 给定URL
     * @return URL的Path
     */
    public static String getPath(String url) {
        String authority = getAuthority(url);
        return url.replace("http://", "").replace("https://", "")
                .replace(authority, "");
    }

    /**
     * 获取给定URL的Path
     *
     * @param url 给定URL
     * @return URL的Path
     */
    public static String getPath2(String url) {
        String returnVal = "";
        try {
            URL requestUrl = new URL(url);
            returnVal = requestUrl.getPath();
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        return returnVal;
    }

    /**
     * 获取给定URL的父Path
     *
     * @param url 给定URL
     * @return URL的父Path
     */
    public static String getParentPath(String url) {
        String path = getPath(url);
        return new File(path).getParent() + "/";
    }

    /**
     * 获取给定URL的父Path
     *
     * @param url 给定URL
     * @return URL的父Path
     */
    public static String getParentPath2(String url) {
        String path = getPath2(url);
        return new File(path).getParent() + "/";
    }

    /**
     * 地址的Host和Port是否相同
     *
     * @param urls 地址的url集合
     * @return true相同，false不相同
     */
    public static boolean isSameHostAndPort(String[] urls) {
        if (urls == null || urls.length < 2) {
            throw new IllegalArgumentException("url length must > 1");
        }
        String standardHost = getHost(urls[0]);
        int standardPort = getPort(urls[0]);
        for (int i = 1, len = urls.length; i < len; i++) {
            if (!(standardHost.equals(getHost(urls[i])) && standardPort == getPort(urls[i]))) {
                return false;
            }
        }
        return true;
    }

    /**
     * 地址的Host、Port、Parent path是否相同
     *
     * @param urls 地址的url集合
     * @return true相同，false不相同
     */
    public static boolean isSameHostAndPortAndParentPath(String[] urls) {
        if (urls == null || urls.length < 2) {
            throw new IllegalArgumentException("url length must > 1");
        }
        String standardHost = getHost(urls[0]);
        int standardPort = getPort(urls[0]);
        String standardParentPath = getParentPath(urls[0]);
        for (int i = 1, len = urls.length; i < len; i++) {
            if (!(standardHost.equals(getHost(urls[i])) && standardPort == getPort(urls[i])
                    && standardParentPath.equals(getParentPath(urls[i])))) {
                return false;
            }
        }
        return true;
    }

    /**
     * 下载文件
     *
     * @param fileUrl  文件的地址
     * @param savePath 文件保存路径
     * @throws IOException 下载IO错误
     */
    public static void downloadFile(String fileUrl, String savePath) throws IOException {
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder().url(fileUrl)
                .build();
        Response response = client.newCall(request).execute();
        if (response.isSuccessful()) {
            ResponseBody body = response.body();
            byte[] buf = new byte[4096];
            int len;
            if (body != null) {
                try (FileOutputStream fos = new FileOutputStream(savePath);
                     InputStream in = body.byteStream()) {
                    while ((len = in.read(buf)) >= 0) {
                        fos.write(buf, 0, len);
                    }
                }
            }
        }
    }
}
