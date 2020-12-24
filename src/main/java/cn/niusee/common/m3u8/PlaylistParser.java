/*
 * Niusee server-common
 *
 * Copyright 2015-2020 by Niusee.inc. All rights reserved.
 */
package cn.niusee.common.m3u8;

import cn.niusee.common.logger.LoggerHelper;

import java.io.File;
import java.io.StringReader;
import java.net.URI;
import java.nio.channels.Channels;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static cn.niusee.common.m3u8.M3uConstants.*;

/**
 * M3U8文件格式的解析器。基于http://tools.ietf.org/html/draft-pantos-http-live-streaming-02#section-3.1
 *
 * @author Qianliang Zhang
 */
public final class PlaylistParser {

    /**
     * 日记记录
     */
    private final static LoggerHelper log = new LoggerHelper(PlaylistParser.class);

    /**
     * 创建M3U8列表解析类
     *
     * @param type 列表类型
     * @return M3U8列表解析类
     */
    static PlaylistParser create(PlaylistType type) {
        return new PlaylistParser(type);
    }

    /**
     * 列表类型
     */
    private final PlaylistType type;

    /**
     * M3U8列表解析类构造函数
     *
     * @param type 列表类型
     */
    public PlaylistParser(PlaylistType type) {
        if (type == null) {
            throw new NullPointerException("type");
        }
        this.type = type;
    }

    /**
     * See {@link Channels#newReader(java.nio.channels.ReadableByteChannel, String)}
     * See {@link StringReader}
     *
     * @param source the source.
     * @return a playlist.
     * @throws M3u8ParseException parsing fails.
     */
    public Playlist parse(Readable source) throws M3u8ParseException {
        // 信息扫描
        final Scanner scanner = new Scanner(source);
        // 是否获取到标签开始标记
        boolean firstLine = true;
        // 信息行数记录
        int lineNumber = 0;
        // 版本信息
        int version = -1;
        // 是否设定结束标记
        boolean endListSet = false;
        // 媒体最大时间
        int targetDuration = -1;
        // 媒体唯一标示码
        int mediaSequenceNumber = -1;
        // 加密信息
        EncryptionInfo currentEncryption = null;

        final List<Element> elements = new ArrayList<>(10);
        final ElementBuilder builder = new ElementBuilder();
        // 循环解析
        while (scanner.hasNextLine()) {
            String line = scanner.nextLine().trim();
            if (line.length() > 0) {
                // 有效标签
                if (line.startsWith(EXT_PREFIX)) {
                    if (firstLine) {
                        // 检查第一行
                        checkFirstLine(lineNumber, line);
                        firstLine = false;
                    } else if (line.startsWith(EXT_INF)) {
                        // 媒体信息标签
                        parseExtInf(line, lineNumber, builder);
                    } else if (line.startsWith(EXT_X_VERSION)) {
                        // 媒体时长标记标签
                        if (version != -1) {
                            throw new M3u8ParseException(line, lineNumber, EXT_X_VERSION + " duplicated");
                        }
                        // 版本标签
                        version = parseVersion(line, lineNumber);
                    } else if (line.startsWith(EXT_X_ENDLIST)) {
                        // 结束标签
                        endListSet = true;
                    } else if (line.startsWith(EXT_X_TARGET_DURATION)) {
                        // 媒体时长标记标签
                        if (targetDuration != -1) {
                            throw new M3u8ParseException(line, lineNumber, EXT_X_TARGET_DURATION + " duplicated");
                        }
                        targetDuration = parseTargetDuration(line, lineNumber);
                    } else if (line.startsWith(EXT_X_MEDIA_SEQUENCE)) {
                        // 列表媒体唯一标示码标签
                        if (mediaSequenceNumber != -1) {
                            throw new M3u8ParseException(line, lineNumber, EXT_X_MEDIA_SEQUENCE + " duplicated");
                        }
                        mediaSequenceNumber = parseMediaSequence(line, lineNumber);
                    } else if (line.startsWith(EXT_X_DISCONTINUITY)) {
                        // 中断标签
                        builder.discontinuity(true);
                    } else if (line.startsWith(EXT_X_KEY)) {
                        // 加密标签
                        currentEncryption = parseEncryption(line, lineNumber);
                    } else {
                        // 其他暂不解析标签
                        log.warn("unknown: '" + line + "'");
                    }
                } else if (line.startsWith(COMMENT_PREFIX)) {
                    // 注释标签，直接忽略
                    log.info("comment: " + line);
                } else {
                    // FIXME 这里不精确的认为是媒体的地址
                    if (firstLine) {
                        checkFirstLine(lineNumber, line);
                    }
                    builder.encrypted(currentEncryption);
                    builder.uri(line);
                    elements.add(builder.create());
                    builder.reset();
                }
            }
            lineNumber++;
        }
        // 返回列表
        return new Playlist(Collections.unmodifiableList(elements), endListSet, version, targetDuration,
                mediaSequenceNumber);
    }

    /**
     * 字符串转换成URI
     *
     * @param line 字符串
     * @return URI
     */
    private static URI toURI(String line) {
        try {
            return (URI.create(line));
        } catch (IllegalArgumentException e) {
            return new File(line).toURI();
        }
    }

    /**
     * 解析列表的节目时间
     *
     * @param line       解析信息
     * @param lineNumber 解析信息所在行数
     * @return 列表的节目时间
     * @throws M3u8ParseException 解析错误
     */
    private long parseProgramDateTime(String line, int lineNumber) throws M3u8ParseException {
        return Patterns.toDate(line, lineNumber);
    }

    /**
     * 解析列表的唯一标示码
     *
     * @param line       解析信息
     * @param lineNumber 解析信息所在行数
     * @return 列表的唯一标示码
     * @throws M3u8ParseException 解析错误
     */
    private int parseVersion(String line, int lineNumber) throws M3u8ParseException {
        return (int) parseNumberTag(line, lineNumber, Patterns.EXT_X_VERSION, EXT_X_VERSION);
    }

    /**
     * 解析列表节目的最大时间
     *
     * @param line       解析信息
     * @param lineNumber 解析信息所在行数
     * @return 列表节目的最大时间
     * @throws M3u8ParseException 解析错误
     */
    private int parseTargetDuration(String line, int lineNumber) throws M3u8ParseException {
        return (int) parseNumberTag(line, lineNumber, Patterns.EXT_X_TARGET_DURATION, EXT_X_TARGET_DURATION);
    }

    /**
     * 解析列表的唯一标示码
     *
     * @param line       解析信息
     * @param lineNumber 解析信息所在行数
     * @return 列表的唯一标示码
     * @throws M3u8ParseException 解析错误
     */
    private int parseMediaSequence(String line, int lineNumber) throws M3u8ParseException {
        return (int) parseNumberTag(line, lineNumber, Patterns.EXT_X_MEDIA_SEQUENCE, EXT_X_MEDIA_SEQUENCE);
    }

    /**
     * 根据解析方式，将信息解析出结果
     *
     * @param line       解析信息
     * @param lineNumber 解析信息所在行数
     * @param pattern    解析正则表达式
     * @param property   解析标签
     * @return 解析结果
     * @throws M3u8ParseException 解析错误
     */
    private long parseNumberTag(String line, int lineNumber, Pattern pattern, String property) throws
            M3u8ParseException {
        Matcher matcher = pattern.matcher(line);
        if (!matcher.find() && !matcher.matches() && matcher.groupCount() < 1) {
            throw new M3u8ParseException(line, lineNumber, property + " must specify duration");
        }
        try {
            return Long.parseLong(matcher.group(1));
        } catch (NumberFormatException e) {
            throw new M3u8ParseException(line, lineNumber, e);
        }
    }

    /**
     * 检查M3U8的第一行标记
     *
     * @param lineNumber 信息行数
     * @param line       信息
     * @throws M3u8ParseException 解析错误
     */
    private void checkFirstLine(int lineNumber, String line) throws M3u8ParseException {
        if (type == PlaylistType.M3U8 && !line.startsWith(EXT_M3U)) {
            throw new M3u8ParseException(line, lineNumber, "Playlist type '" + PlaylistType.M3U8
                    + "' must start with " + EXT_M3U);
        }
    }

    /**
     * 解析媒体信息
     *
     * @param line       信息
     * @param lineNumber 信息行数
     * @param builder    媒体构建类
     * @throws M3u8ParseException 解析错误
     */
    private void parseExtInf(String line, int lineNumber, ElementBuilder builder) throws M3u8ParseException {
        // EXTINF:200,Title
        final Matcher matcher = Patterns.EXTINF.matcher(line);
        if (!matcher.find() && !matcher.matches() && matcher.groupCount() < 1) {
            throw new M3u8ParseException(line, lineNumber, "EXTINF must specify at least the duration");
        }
        String duration = matcher.group(1);
        double num = Double.parseDouble(duration);
        String title = matcher.groupCount() > 1 ? matcher.group(2) : "";
        try {
            builder.duration(Double.parseDouble(duration)).title(title);
        } catch (NumberFormatException e) {
            throw new M3u8ParseException(line, lineNumber, e);
        }
    }

    /**
     * 解析加密信息
     *
     * @param line       信息
     * @param lineNumber 信息行数
     * @return 加密信息
     * @throws M3u8ParseException 解析错误
     */
    private EncryptionInfo parseEncryption(String line, int lineNumber) throws M3u8ParseException {
        Matcher matcher = Patterns.EXT_X_KEY.matcher(line);
        if (!matcher.find() || !matcher.matches() || matcher.groupCount() < 1) {
            throw new M3u8ParseException(line, lineNumber, "illegal input: " + line);
        }

        String method = matcher.group(1);
        String uri = matcher.group(3);
        if (method.equalsIgnoreCase("none")) {
            return null;
        }
        return new EncryptionInfoImpl(uri != null ? toURI(uri) : null, method);
    }
}
