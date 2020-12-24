/*
 * Niusee server-common
 *
 * Copyright 2015-2020 by Niusee.inc. All rights reserved.
 */
package cn.niusee.common.m3u8.util;

import cn.niusee.common.httpclient.SingletonHttpClient;
import cn.niusee.common.m3u8.*;
import okhttp3.Response;
import okhttp3.ResponseBody;

import java.io.IOException;
import java.io.StringReader;
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
    private static String fetchM3u8Content(String url) throws IOException {
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
     * 根据URL分析前缀地址
     *
     * @param url 录制地址
     * @return 前缀地址
     */
    private static String analyzePrefixUrl(String url) {
        return url.substring(0, url.lastIndexOf("/") + 1);
    }

    /**
     * 获取TS切片完整的播放地址
     *
     * @param parentUrl m3u8播放地址前缀
     * @param tsClipUrl ts切片的地址
     * @return TS切片完整的播放地址
     */
    private static String fixTsClipUrl(String parentUrl, String tsClipUrl) {
        if (tsClipUrl.startsWith("http://") || tsClipUrl.startsWith("https://")) {
            return tsClipUrl;
        }
        return parentUrl + tsClipUrl;
    }

    /**
     * 拷贝ts切片信息
     *
     * @param element  ts切片信息
     * @param fixedUrl 前缀Url
     * @return 拷贝后的ts切片信息
     */
    private static Element copyElement(Element element, String fixedUrl) {
        ElementBuilder builder = new ElementBuilder()
                .discontinuity(element.isDiscontinuity())
                .encrypted(element.getEncryptionInfo())
                .duration(element.getExactDuration())
                .title(element.getTitle())
                // 源地址+切片地址
                .uri(M3u8Utils.fixTsClipUrl(analyzePrefixUrl(fixedUrl), element.getURI()));
        return builder.create();
    }

    /**
     * 拷贝全部ts切片信息
     *
     * @param elements ts切片信息
     * @param fixedUrl 前缀Url
     * @return 拷贝后的ts切片信息
     */
    private static List<Element> copyAllElement(List<Element> elements, String fixedUrl) {
        List<Element> resultElements = new ArrayList<>(elements.size());
        for (Element element : elements) {
            resultElements.add(copyElement(element, fixedUrl));
        }
        // 添加断点标记
        if (!resultElements.isEmpty() && !resultElements.get(0).isDiscontinuity()) {
            resultElements.get(0).setDiscontinuity(true);
        }
        return resultElements;
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
     * 播放列表修改添加前缀地址
     *
     * @param prefixUrl 前缀地址
     * @param playlist  原播放列表
     * @return 修复好的播放列表
     */
    public static Playlist fixPlaylistWithPrefixUrl(String prefixUrl, Playlist playlist) {
        List<Element> fixedElements = copyAllElement(playlist.getElements(), prefixUrl);
        return new Playlist(Collections.unmodifiableList(fixedElements), playlist.isEndSet(),
                playlist.getVersion(), playlist.getTargetDuration(), playlist.getMediaSequenceNumber());
    }

    /**
     * 合并所有切片
     *
     * @param isFixedUrl 是否修复url地址
     * @param urls       合并的m3u8地址
     * @return 合并结果列表
     * @throws IOException        获取m3u8内容的请求错误
     * @throws M3u8ParseException m3u8解析错误
     */
    public static Playlist merge(boolean isFixedUrl, String... urls) throws IOException, M3u8ParseException {
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
            // 是否添加修复地址
            if (isFixedUrl) {
                resultElements.addAll(copyAllElement(playlist.getElements(), url));
            } else {
                resultElements.addAll(playlist.getElements());
            }
        }
        return new Playlist(Collections.unmodifiableList(resultElements), true,
                version, targetDuration, mediaSequenceNumber);
    }
}