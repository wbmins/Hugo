package com.github.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

/**
 * SpringBoot 读取配置文件四种方式
 * 1. @Component + @value
 * 2. @Component + @ConfigurationProperties
 * 3. @Component + @PropertySource + @value
 * 4. @Component + @ConfigurationProperties + @PropertySource
 * 5. 注入 Environment 通过 getProper() 获取
 * 注意：@PropertySource 无法解析 yml 可以通过重新实现 PropertySourceFactory
 * 加载 yml
 */

@Component
@Data
@ConfigurationProperties(prefix = "blog")
@PropertySource(value = "classpath:config.yml", factory = YamlPropertySourceFactory.class)
public class MyConfig {
    private String userName;
    private String passWord;
}
