/*
 * Niusee server-common
 *
 * Copyright 2015-2017 by Niusee.inc. All rights reserved.
 */
package cn.niusee.common.config;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.URL;
import java.util.Properties;

/**
 * 配置文件读取类
 *
 * @author Qianliang Zhang
 */
public class ConfigLoader {

    /**
     * 配置文件路径
     */
    private final String configName;

    /**
     * 读取配置文件
     */
    private Properties prop = new Properties();

    public ConfigLoader(String configName) {
        this.configName = configName;
    }

    /**
     * 初始化配置
     */
    public void initConfig() {
        // 读取配置文件
        try {
            ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
            URL path = classLoader.getResource(configName);
            if (path != null) {
                prop.load(new FileInputStream(new File(path.getFile())));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 获取配置项的值
     *
     * @param key 配置项
     * @return 配置项的值
     */
    public String get(String key) {
        return prop.getProperty(key);
    }

    /**
     * 获取配置项的值，没有的话，就给定默认值
     *
     * @param key          配置项
     * @param defaultValue 默认值
     * @return 配置项的值
     */
    public String get(String key, String defaultValue) {
        return prop.getProperty(key, defaultValue);
    }

    /**
     * 获取配置项为布尔的值，没有的话，就给定默认值
     *
     * @param key          配置项
     * @param defaultValue 默认值
     * @return 配置项的值
     */
    public boolean getBoolean(String key, boolean defaultValue) {
        try {
            return Boolean.parseBoolean(get(key));
        } catch (Exception e) {
            // 防止解析出错
        }
        return defaultValue;
    }

    /**
     * 获取配置项为整数的值，没有的话，就给定默认值
     *
     * @param key          配置项
     * @param defaultValue 默认值
     * @return 配置项的值
     */
    public int getInt(String key, int defaultValue) {
        try {
            return Integer.parseInt(get(key));
        } catch (Exception e) {
            // 防止解析出错
        }
        return defaultValue;
    }

    /**
     * 获取配置项为长整数的值，没有的话，就给定默认值
     *
     * @param key          配置项
     * @param defaultValue 默认值
     * @return 配置项的值
     */
    public long getLong(String key, long defaultValue) {
        try {
            return Long.parseLong(get(key));
        } catch (Exception e) {
            // 防止解析出错
        }
        return defaultValue;
    }

    /**
     * 获取配置项为Float的值，没有的话，就给定默认值
     *
     * @param key          配置项
     * @param defaultValue 默认值
     * @return 配置项的值
     */
    public float getFloat(String key, float defaultValue) {
        try {
            return Float.parseFloat(get(key));
        } catch (Exception e) {
            // 防止解析出错
        }
        return defaultValue;
    }

    /**
     * 获取配置项为Double的值，没有的话，就给定默认值
     *
     * @param key          配置项
     * @param defaultValue 默认值
     * @return 配置项的值
     */
    public double getDouble(String key, double defaultValue) {
        try {
            return Double.parseDouble(get(key));
        } catch (Exception e) {
            // 防止解析出错
        }
        return defaultValue;
    }
}
