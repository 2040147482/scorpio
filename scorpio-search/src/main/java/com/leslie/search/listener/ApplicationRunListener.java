package com.leslie.search.listener;

import com.alibaba.fastjson.JSON;
import com.leslie.clients.ProductClient;
import com.leslie.pojo.Product;
import com.leslie.pojo.ProductDoc;
import lombok.extern.slf4j.Slf4j;
import org.elasticsearch.action.bulk.BulkRequest;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.client.indices.CreateIndexRequest;
import org.elasticsearch.client.indices.GetIndexRequest;
import org.elasticsearch.common.xcontent.XContentType;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.index.reindex.DeleteByQueryRequest;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.List;

import static com.leslie.search.constants.SearchConstant.CREATE_INDEX;

/**
 * @author 20110
 * description: 监控程序启动,初始化es索引库数据
 */
@Slf4j
@Component
public class ApplicationRunListener implements ApplicationRunner {

    @Resource
    private RestHighLevelClient client;

    @Resource
    private ProductClient productClient;

    @Override
    public void run(ApplicationArguments args) throws Exception {
        //1.查询全部商品数据
        List<Product> productList = productClient.list();

        //2.判断product索引库是否存在
        GetIndexRequest getIndexRequest = new GetIndexRequest("product");
        boolean exists = client.indices().exists(getIndexRequest, RequestOptions.DEFAULT);
        if (!exists) {
            //不存在，创建索引库
            CreateIndexRequest createIndexRequest = new CreateIndexRequest("product");
            createIndexRequest.source(CREATE_INDEX, XContentType.JSON);
            client.indices().create(createIndexRequest, RequestOptions.DEFAULT);
        }

        //存在，删除全部数据
        DeleteByQueryRequest deleteByQueryRequest = new DeleteByQueryRequest("product");
        deleteByQueryRequest.setQuery(QueryBuilders.matchAllQuery());
        client.deleteByQuery(deleteByQueryRequest, RequestOptions.DEFAULT);

        //3.批量插入数据
        BulkRequest bulkRequest = new BulkRequest();
        for (Product product : productList) {
            ProductDoc productDoc = new ProductDoc(product);
            bulkRequest.add(new IndexRequest("product")
                    .id(productDoc.getProductId().toString())
                    .source(JSON.toJSONString(productDoc), XContentType.JSON)
            );
        }
        client.bulk(bulkRequest, RequestOptions.DEFAULT);

        log.info("成功完成es索引库数据初始化");
    }

}
