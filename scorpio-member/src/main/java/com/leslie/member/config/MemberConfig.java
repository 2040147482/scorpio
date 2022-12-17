package com.leslie.member.config;

import com.leslie.config.MyCacheConfiguration;
import org.springframework.boot.autoconfigure.cache.CacheProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Configuration;


/**
 * @author 20110
 * @description member服务配置类
 */
@EnableConfigurationProperties(CacheProperties.class)
@Configuration
@EnableCaching
public class MemberConfig extends MyCacheConfiguration {
}
