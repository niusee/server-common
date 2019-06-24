package cn.niusee.common.examples.config;

import cn.niusee.common.config.ConfigLoader;
import cn.niusee.common.config.ConfigLoaderFactory;
import cn.niusee.common.config.IServerConfig;
import cn.niusee.common.config.IllegalConfigException;
import spark.utils.StringUtils;

/**
 * Server config sample
 *
 * @author Zhang Qianliang
 */
public class SampleServerConfig implements IServerConfig {

    private String host;

    private int port;

    private String username;

    private String password;

    @Override
    public void initConfig() {
        ConfigLoader configLoader = ConfigLoaderFactory.openConfigLoader("sample.properties");
        configLoader.initConfig();

        host = configLoader.get("sample_host");
        port = configLoader.getInt("sample_port", 6375);
        username = configLoader.get("sample_username");
        password = configLoader.get("sample_password");
    }

    @Override
    public void checkConfig() throws IllegalConfigException {
        if (StringUtils.isBlank(host)) {
            throw new IllegalConfigException("sample_host can not be blank");
        }
        if (port < 80) {
            throw new IllegalConfigException("sample_port can not be less than 80");
        }
        if (StringUtils.isBlank(username)) {
            throw new IllegalConfigException("sample_username can not be blank");
        }
        if (StringUtils.isBlank(password)) {
            throw new IllegalConfigException("sample_password can not be blank");
        }
    }

    public static void main(String[] args) throws IllegalConfigException {
        SampleServerConfig sampleServerConfig = new SampleServerConfig();
        sampleServerConfig.initConfig();
        sampleServerConfig.checkConfig();

        System.out.println("sample_host: " + sampleServerConfig.host);
        System.out.println("sample_port: " + sampleServerConfig.port);
        System.out.println("sample_username: " + sampleServerConfig.username);
        System.out.println("sample_password: " + sampleServerConfig.password);
    }
}
