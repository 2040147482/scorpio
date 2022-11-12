package com.leslie.search.config;

import org.apache.http.HttpHost;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestHighLevelClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author 20110
 */
@Configuration
public class SearchConfiguration {

    /**
     * es客户端添加到ioc容器
     * @return RestHighLevelClient连接对象
     */
    @Bean
    public RestHighLevelClient restHighLevelClient() {
        RestHighLevelClient client = new RestHighLevelClient(
                RestClient.builder(HttpHost.create("http://127.0.0.1:9200")));
        return client;
    }

}
