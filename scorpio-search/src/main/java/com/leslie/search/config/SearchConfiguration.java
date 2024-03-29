package com.leslie.search.config;

import com.leslie.config.MyCacheConfiguration;
import org.apache.http.HttpHost;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestHighLevelClient;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.cache.CacheProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author 20110
 */
@EnableConfigurationProperties(CacheProperties.class)
@Configuration
@EnableCaching
public class SearchConfiguration extends MyCacheConfiguration {


    @Value("${elasticsearch-server-url}")
    public String elasticsearchServerUrl;

    /**
     * es客户端添加到ioc容器
     * @return RestHighLevelClient连接对象
     */
    @Bean
    public RestHighLevelClient restHighLevelClient() {
        RestHighLevelClient client = new RestHighLevelClient(
                RestClient.builder(HttpHost.create(elasticsearchServerUrl)));
        return client;
    }

    /**
     * 配置消息转换器
     */
    @Bean
    public MessageConverter jsonMessageConverter() {
        return new Jackson2JsonMessageConverter();
    }
}
