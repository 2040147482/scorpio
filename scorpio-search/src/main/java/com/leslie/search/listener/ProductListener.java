package com.leslie.search.listener;

import com.alibaba.fastjson.JSON;
import com.leslie.constants.MqConstants;
import com.leslie.pojo.Product;
import com.leslie.pojo.ProductDoc;
import com.leslie.search.manager.SearchManager;
import org.elasticsearch.action.bulk.BulkRequest;
import org.elasticsearch.action.delete.DeleteRequest;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.update.UpdateRequest;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.common.xcontent.XContentType;
import org.elasticsearch.index.mapper.SearchAsYouTypeFieldMapper;
import org.springframework.amqp.rabbit.annotation.Exchange;
import org.springframework.amqp.rabbit.annotation.Queue;
import org.springframework.amqp.rabbit.annotation.QueueBinding;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.io.IOException;
import java.util.List;

/**
 * @author 20110
 */
@Component
public class ProductListener {

    @Resource
    private RestHighLevelClient client;

    /**
     * 监听商品新增或修改的业务
     */
    @RabbitListener(bindings = @QueueBinding(
            value = @Queue(name = MqConstants.PRODUCT_INSERT_QUEUE),
            exchange = @Exchange(MqConstants.PRODUCT_EXCHANGE),
            key = MqConstants.PRODUCT_INSERT_KEY)
    )
    public void productInsertOrUpdate(Product product) {
        try {
            ProductDoc productDoc = new ProductDoc(product);

            IndexRequest request = new IndexRequest("product").id(product.getProductId().toString());
            request.source(JSON.toJSONString(productDoc), XContentType.JSON);
            client.index(request, RequestOptions.DEFAULT);
        } catch (IOException e) {
            throw new RuntimeException("索引库更新失败！");
        }
    }

    /**
     * 监听商品删除的业务
     */
    @RabbitListener(bindings = @QueueBinding(
            value = @Queue(name = MqConstants.PRODUCT_DELETE_QUEUE),
            exchange = @Exchange(MqConstants.PRODUCT_EXCHANGE),
            key = MqConstants.PRODUCT_DELETE_KEY)
    )
    public void productDelete(Long id) {
        try {
            DeleteRequest request = new DeleteRequest("product").id(id.toString());
            client.delete(request, RequestOptions.DEFAULT);
        } catch (IOException e) {
            throw new RuntimeException("索引库更新失败！", e);
        }
    }


    /**
     * 监听商品库存增加和销量减少的业务
     */
    @RabbitListener(bindings = @QueueBinding(
            value = @Queue(name = MqConstants.PRODUCT_UPDATE_QUEUE),
            exchange = @Exchange(MqConstants.PRODUCT_EXCHANGE),
            key = MqConstants.PRODUCT_UPDATE_KEY)
    )
    public void pubProductStockIncr(List<Product> productList) throws IOException {

        SearchManager.batchInsert(client, productList);
    }

}
