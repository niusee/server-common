/*
 * Niusee server-common
 *
 * Copyright 2015-2020 by Niusee.inc. All rights reserved.
 */
package cn.niusee.common.m3u8.util;

import cn.niusee.common.httpclient.SingletonHttpClient;
import cn.niusee.common.m3u8.*;
import cn.niusee.common.utils.HttpUtils;
import okhttp3.Response;
import okhttp3.ResponseBody;

import java.io.*;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * M3u8视频工具类
 *
 * @author Qianliang Zhang
 */
public final class M3u8Utils {

    /**
     * 获取M3U8的列表内容
     *
     * @param url m3u8地址
     * @return M3U8的列表内容
     * @throws IOException m3u8地址请求错误
     */
    public static String fetchM3u8Content(String url) throws IOException {
        // 为了减少https的超时时间的损耗，使用http
        String httpUrl = url;
        if (httpUrl.startsWith("https://")) {
            httpUrl = url.replace("https://", "http://");
        }
        try (Response response = SingletonHttpClient.getInstance().get(httpUrl)) {
            ResponseBody body = response.body();
            String content = body != null ? body.string() : null;
            if (!response.isSuccessful()) {
                throw new IOException(url + " fetch error: " + content);
            }

            if (content == null) {
                throw new IOException(url + " content blank");
            }
            return content;
        }
    }

    /**
     * 解析M3U8列表
     *
     * @param url m3u8地址
     * @return M3U8列表
     * @throws IOException        m3u8地址请求错误
     * @throws M3u8ParseException m3u8解析错误
     */
    public static Playlist parsePlaylistFromUrl(String url) throws IOException, M3u8ParseException {
        // 请求M3U8的列表内容
        String content = fetchM3u8Content(url);
        // 源m3u8列表
        return new PlaylistParser(PlaylistType.M3U8).parse(new StringReader(content));
    }

    /**
     * 获取TS切片的绝对路径
     *
     * @param m3u8Url    m3u8播放地址
     * @param elementUrl ts切片的地址
     * @return TS切片的绝对路径
     */
    public static String getElementAbsolutePath(String m3u8Url, String elementUrl) {
        if (elementUrl.startsWith("http://") || elementUrl.startsWith("https://")) {
            if (HttpUtils.isUrlHostAndPortSame(new String[]{m3u8Url, elementUrl})) {
                return HttpUtils.getPath(elementUrl);
            }
            return elementUrl;
        }

        if (elementUrl.startsWith("/")) {
            return elementUrl;
        }

        return new File(new File(HttpUtils.getPath2(m3u8Url)).getParent(), elementUrl).getAbsolutePath();
    }

    /**
     * 获取TS切片完整访问地址
     *
     * @param m3u8Url    m3u8播放地址
     * @param elementUrl ts切片的地址
     * @return TS切片完整访问地址
     */
    public static String getElementRequestUrl(String m3u8Url, String elementUrl) {
        if (elementUrl.startsWith("http://") || elementUrl.startsWith("https://")) {
            return elementUrl;
        }

        if (elementUrl.startsWith("/")) {
            try {
                URL requestUrl = new URL(m3u8Url);
                return requestUrl.getProtocol() + "://" + requestUrl.getAuthority() + elementUrl;
            } catch (MalformedURLException e) {
                e.printStackTrace();
            }
        }
        return m3u8Url.substring(0, m3u8Url.lastIndexOf("/") + 1) + elementUrl;
    }

    /**
     * 拷贝ts切片信息，地址为绝对路径地址
     *
     * @param element ts切片信息
     * @param m3u8Url m3u8地址
     * @return 拷贝后的ts切片信息
     */
    private static Element copyElement2AbsolutePath(Element element, String m3u8Url) {
        ElementBuilder builder = new ElementBuilder()
                .discontinuity(element.isDiscontinuity())
                .encrypted(element.getEncryptionInfo())
                .duration(element.getExactDuration())
                .title(element.getTitle())
                // 更换成绝对路径地址
                .uri(getElementAbsolutePath(m3u8Url, element.getURI()));
        return builder.create();
    }

    /**
     * 拷贝全部ts切片信息，地址为绝对路径地址
     *
     * @param elements ts切片信息
     * @param m3u8Url  m3u8播放地址
     * @return 拷贝后的ts切片信息
     */
    private static List<Element> copyAllElement2AbsolutePath(List<Element> elements, String m3u8Url) {
        List<Element> resultElements = new ArrayList<>(elements.size());
        for (Element element : elements) {
            resultElements.add(copyElement2AbsolutePath(element, m3u8Url));
        }
        // 添加断点标记
        if (!resultElements.isEmpty() && !resultElements.get(0).isDiscontinuity()) {
            resultElements.get(0).setDiscontinuity(true);
        }
        return resultElements;
    }

    /**
     * 播放列表切片地址改成绝对路径
     *
     * @param m3u8Url  m3u8播放地址
     * @param playlist 原播放列表
     * @return 修复好的播放列表
     */
    public static Playlist copyPlaylist2AbsolutePath(String m3u8Url, Playlist playlist) {
        List<Element> fixedElements = copyAllElement2AbsolutePath(playlist.getElements(), m3u8Url);
        return new Playlist(Collections.unmodifiableList(fixedElements), playlist.isEndSet(),
                playlist.getVersion(), playlist.getTargetDuration(), playlist.getMediaSequenceNumber());
    }

    /**
     * 合并所有切片
     *
     * @param is2AbsolutePath 是否转为绝对路径
     * @param urls            合并的m3u8地址
     * @return 合并结果列表
     * @throws IOException        获取m3u8内容的请求错误
     * @throws M3u8ParseException m3u8解析错误
     */
    public static Playlist merge(boolean is2AbsolutePath, String... urls) throws IOException, M3u8ParseException {
        // 版本信息
        int version = -1;
        // 最大切片长度信息
        int targetDuration = -1;
        // 序号
        int mediaSequenceNumber = -1;
        // 剪切的切片
        List<Element> resultElements = new ArrayList<>();
        for (String url : urls) {
            // m3u8列表
            Playlist playlist = parsePlaylistFromUrl(url);
            if (playlist.getElements().isEmpty()) {
                continue;
            }
            // 版本的等信息
            if (version == -1) {
                version = playlist.getVersion();
            }
            if (mediaSequenceNumber == -1) {
                mediaSequenceNumber = playlist.getMediaSequenceNumber();
            }
            if (playlist.getTargetDuration() > targetDuration) {
                targetDuration = playlist.getTargetDuration();
            }
            // 第一个切片设置变化
            playlist.getElements().get(0).setDiscontinuity(true);
            // 是否转为绝对路径
            if (is2AbsolutePath) {
                resultElements.addAll(copyAllElement2AbsolutePath(playlist.getElements(), url));
            } else {
                resultElements.addAll(playlist.getElements());
            }
        }
        return new Playlist(Collections.unmodifiableList(resultElements), true,
                version, targetDuration, mediaSequenceNumber);
    }

    /**
     * 剪切ts切片输出到流数据
     *
     * @param elementUri Ts切片地址
     * @param start      截取开始时间点
     * @param end        截取结束时间点
     * @return 剪切结果
     */
    public static InputStream cutElement2Stream(String elementUri, double start, double end) {
        // Ffmpeg 运行命令
        List<String> command = new ArrayList<>();
        command.add("ffmpeg");
        command.add("-loglevel");
        command.add("fatal");
        // 剪辑时间参数
        command.add("-y");
        if (start > 0) {
            command.add("-ss");
            command.add(Double.toString(start));
        }
        if (end > 0) {
            command.add("-t");
            command.add(Double.toString(end - start));
        }
        command.add("-i");
        command.add(elementUri);
        command.add("-vsync");
        command.add("0");
        command.add("-copyts");
        command.add("-c");
        command.add("copy");
        command.add("-f");
        command.add("mpegts");
        command.add("-");

        ProcessBuilder pb = new ProcessBuilder(command).directory(null);
        pb.redirectErrorStream(true);
        Process p = null;

        try {
            p = pb.start();
            // 读取输出数据
            InputStream inputStream = p.getInputStream();
            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();

            byte[] buffer = new byte[1024];
            int len;
            while ((len = inputStream.read(buffer)) > -1) {
                byteArrayOutputStream.write(buffer, 0, len);
            }
            byteArrayOutputStream.flush();
            try {
                p.waitFor();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            return new ByteArrayInputStream(byteArrayOutputStream.toByteArray());
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (p != null) {
                p.destroy();
            }
        }
        return null;
    }
}