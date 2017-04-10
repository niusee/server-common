/*
 * Niusee server-common
 *
 * Copyright 2015-2017 by Niusee.inc. All rights reserved.
 */
package cn.niusee.common.config;

/**
 * 服务配置接口定义类
 *
 * @author Qianliang Zhang
 */
public interface IServerConfig {
    /**
     * 初始化配置
     */
    void initConfig();

    /**
     * 检查配置
     *
     * @throws IllegalConfigException 配置抛出错误
     */
    void checkConfig() throws IllegalConfigException;
}
