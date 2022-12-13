package com.leslie.search.manager;

import com.alibaba.fastjson.JSON;
import com.leslie.pojo.Product;
import com.leslie.pojo.ProductDoc;
import org.elasticsearch.action.bulk.BulkRequest;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.common.xcontent.XContentType;

import java.io.IOException;
import java.util.List;

/**
 * @author 20110
 * @descript 批量插入数据
 */
public class SearchManager {

    public static void batchInsert(RestHighLevelClient client, List<Product> productList) throws IOException {
        BulkRequest bulkRequest = new BulkRequest();
        for (Product product : productList) {
            ProductDoc productDoc = new ProductDoc(product);
            bulkRequest.add(new IndexRequest("product")
                    .id(productDoc.getProductId().toString())
                    .source(JSON.toJSONString(productDoc), XContentType.JSON)
            );
        }
        client.bulk(bulkRequest, RequestOptions.DEFAULT);
    }
}
