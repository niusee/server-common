/*
 * Niusee server-common
 *
 * Copyright 2015-2016 by Niusee.inc. All rights reserved.
 */
package cn.niusee.common.utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;
import java.security.DigestInputStream;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * MD5算法类。
 *
 * @author Qianliang Zhang
 */
public final class MD5Utils {

    // 防止继承
    private MD5Utils() {
        throw new RuntimeException("No implements");
    }

    /**
     * 默认的密码字符串组合，Apache 校验下载的文件的正确性用的就是默认的这个组合.
     */
    private final static char[] hexDigits = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9',
            'a', 'b', 'c', 'd', 'e', 'f'};

    /**
     * 建立MD5算法
     *
     * @return MD5算法
     * @throws NoSuchAlgorithmException 没有MD5算法这种类型时出错
     */
    private static MessageDigest setupMD5Digest() throws NoSuchAlgorithmException {
        return MessageDigest.getInstance("MD5");
    }

    /**
     * 适用于上G大的文件（没有限制）.
     *
     * @param file 进行MD5操作的文件
     * @return MD5输出
     * @throws IOException 文件操作的错误（包括不存在等）
     */
    public static String md5BigFile(File file) throws IOException {
        // 缓冲区大小.
        int bufferSize = 256 * 1024;
        FileInputStream fileInputStream = null;
        DigestInputStream digestInputStream = null;
        try {
            MessageDigest messageDigest = setupMD5Digest();
            fileInputStream = new FileInputStream(file);
            digestInputStream = new DigestInputStream(fileInputStream, messageDigest);

            // Read的过程中进行MD5处理，直到读完文件.
            byte[] buffer = new byte[bufferSize];
            while (digestInputStream.read(buffer) > 0)
                ;

            // 获取最终的MessageDigest.
            messageDigest = digestInputStream.getMessageDigest();
            // 拿到结果，也是字节数组，包含16个元素.
            byte[] resultByteArray = messageDigest.digest();
            // 把字节数组转换成字符串.
            return bufferToHex(resultByteArray);
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
            return null;
        } finally {
            if (fileInputStream != null) {
                fileInputStream.close();
            }

            if (digestInputStream != null) {
                digestInputStream.close();
            }
        }

    }

    /**
     * 适用于上G大的文件（2G以下的文件）
     *
     * @param file 进行MD5操作的文件
     * @return MD5输出
     * @throws IOException 文件操作的错误（包括不存在等）
     */
    public static String md5File(File file) throws IOException {
        try {
            FileInputStream in = new FileInputStream(file);
            FileChannel ch = in.getChannel();
            MappedByteBuffer byteBuffer = ch.map(FileChannel.MapMode.READ_ONLY, 0, file.length());
            MessageDigest messageDigest = setupMD5Digest();
            messageDigest.update(byteBuffer);
            return bufferToHex(messageDigest.digest());
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 对字符串MD5加密.
     *
     * @param s 需要加密的字符串
     * @return 加密后的结果
     */
    public static String md5(String s) {
        return md5(s.getBytes());
    }

    /**
     * 对数组数据MD5加密.
     *
     * @param bytes 需要加密的数组数据
     * @return 加密后的结果
     */
    public static String md5(byte[] bytes) {
        try {
            MessageDigest messageDigest = setupMD5Digest();
            messageDigest.update(bytes);
            return bufferToHex(messageDigest.digest());
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 字节数组转换成十六进制字符.
     *
     * @param bytes 字节数组
     * @return 十六进制字符
     */
    private static String bufferToHex(byte[] bytes) {
        return bufferToHex(bytes, 0, bytes.length);
    }

    /**
     * 指定位置的字节数组转换成十六进制字符.
     *
     * @param bytes 字节数组
     * @param m     开始位置
     * @param n     结束位置
     * @return 十六进制字符
     */
    private static String bufferToHex(byte[] bytes, int m, int n) {
        StringBuffer stringBuffer = new StringBuffer(2 * n);
        int k = m + n;
        for (int l = m; l < k; l++) {
            appendHexPair(bytes[l], stringBuffer);
        }
        return stringBuffer.toString();
    }

    /**
     * 后置十六进制字符.
     *
     * @param bt           字节数组
     * @param stringBuffer 组合字符
     */
    private static void appendHexPair(byte bt, StringBuffer stringBuffer) {
        char c0 = hexDigits[(bt & 0xf0) >> 4];
        char c1 = hexDigits[bt & 0xf];
        stringBuffer.append(c0);
        stringBuffer.append(c1);
    }
}
