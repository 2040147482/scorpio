package com.leslie.clients;

import com.leslie.pojo.Product;
import com.leslie.vo.ProductIdsParam;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

/**
 * @author 20110
 */
@FeignClient("product-service")
public interface ProductClient {

    /**
     * 搜索服务查询商品全部数据
     *
     * @return 商品全部数据
     */
    @GetMapping("/product/list")
    List<Product> list();


    /**
     * 供收藏服务使用
     *
     * @param productIdsParam 商品id
     * @return 商品集合数据
     */
    @PostMapping("/product/ids")
    List<Product> ids(@RequestBody ProductIdsParam productIdsParam);
}
