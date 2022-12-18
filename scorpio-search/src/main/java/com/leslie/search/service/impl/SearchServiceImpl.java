package com.leslie.search.service.impl;

import com.alibaba.fastjson.JSON;
import com.leslie.pojo.Product;
import com.leslie.search.service.SearchService;
import com.leslie.search.vo.SearchParams;
import com.leslie.utils.Result;
import lombok.extern.slf4j.Slf4j;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.SearchHits;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * @author 20110
 */
@Slf4j
@Service
public class SearchServiceImpl implements SearchService {

    @Resource
    private RestHighLevelClient client;

    @Cacheable(cacheNames = "search", key = "'search:'+#searchParams.search")
    @Override
    public Result search(SearchParams searchParams) {

        String search = searchParams.getSearch();
        SearchRequest request = new SearchRequest("product");

        if (StringUtils.isEmpty(search)) {
            //如果search为null或空串，查询所有
            request.source().query(QueryBuilders.matchAllQuery());
        } else {
            request.source().query(QueryBuilders.matchQuery("all", search));
        }

        //设置分页
        int currentPage = searchParams.getCurrentPage();
        int pageSize = searchParams.getPageSize();
        request.source().from((currentPage - 1) * pageSize).size(pageSize);

        SearchResponse response;
        try {
            response = client.search(request, RequestOptions.DEFAULT);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        //解析响应
        SearchHits searchHits = response.getHits();
        //获取总条数
        long total = searchHits.getTotalHits().value;
        //文档数据
        SearchHit[] hits = searchHits.getHits();
        List<Product> productList = new ArrayList<>();
        for (SearchHit hit : hits) {
            //获取文档source
            String json = hit.getSourceAsString();
            //反序列化
            Product product = JSON.parseObject(json, Product.class);
            productList.add(product);
        }
        log.info("查询：{} 商品", search);
        return Result.ok(productList, total);
    }
}
