package com.leslie.order.config;

import com.leslie.config.MyCacheConfiguration;
import org.springframework.boot.autoconfigure.cache.CacheProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Configuration;

/**
 * @author 20110
 * @description 缓存配置类
 */
@EnableConfigurationProperties(CacheProperties.class)
@Configuration
@EnableCaching
public class OrderCacheConfig extends MyCacheConfiguration {
}
