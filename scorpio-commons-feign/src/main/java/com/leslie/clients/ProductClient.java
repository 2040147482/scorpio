package com.leslie.clients;

import com.leslie.pojo.Product;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

/**
 * @author 20110
 */
@FeignClient("product-service")
public interface ProductClient {

    /**
     * 搜索服务查询商品全部数据
     * @return 商品全部数据
     */
    @GetMapping("/product/list")
    List<Product> list();
}
