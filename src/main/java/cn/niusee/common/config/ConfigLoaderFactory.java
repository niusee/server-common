/*
 * Niusee vod-server
 *
 * Copyright 2015-2017 by Niusee.inc. All rights reserved.
 */
package cn.niusee.common.config;

/**
 * 配置文件读取类工厂类
 *
 * @author Qianliang Zhang
 */
public class ConfigLoaderFactory {
    private ConfigLoaderFactory() {
    }

    /**
     * 建立一个配置文件读取类
     *
     * @param configName 配置文件名称
     * @return 配置文件读取类
     */
    public static ConfigLoader openConfigLoader(String configName) {
        ConfigLoader configLoader = new ConfigLoader(configName);
        configLoader.initConfig();
        return configLoader;
    }
}
