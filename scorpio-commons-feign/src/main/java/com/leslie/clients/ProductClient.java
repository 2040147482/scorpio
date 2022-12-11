package com.leslie.clients;

import com.leslie.pojo.Product;
import com.leslie.utils.Result;
import com.leslie.vo.ProductIdsParam;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

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
     * 供收藏服务调用
     *
     * @param productIdsParam 商品id
     * @return 商品集合数据
     */
    @PostMapping("/product/ids")
    List<Product> ids(@RequestBody ProductIdsParam productIdsParam);

    /**
     * 供后台管理调用 分页查询商品信息
     * @param page
     * @param size
     * @return
     */
    @GetMapping("/product/query/{page}/{size}")
    Result queryPage(@PathVariable("page") Integer page,
                     @PathVariable("size") Integer size);

    @PostMapping("/product/save")
    Result save(@RequestBody Product product);

    @PutMapping("/product/update")
    Result update(@RequestBody Product product);

    @DeleteMapping("/product/remove/{id}")
    Result deleteByProductId(@PathVariable("id") Long id);
}
