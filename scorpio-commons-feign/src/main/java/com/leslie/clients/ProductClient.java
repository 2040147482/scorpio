package com.leslie.clients;

import com.leslie.pojo.Category;
import com.leslie.pojo.Product;
import com.leslie.pojo.Score;
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
     * 供收藏服务、购物车服务调用
     *
     * @param productIdsParam 商品id
     * @return 商品集合数据
     */
    @PostMapping("/product/ids")
    List<Product> ids(@RequestBody ProductIdsParam productIdsParam);

    /**
     * 供后台管理调用 分页查询商品信息
     *
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

    @GetMapping("/product/category/{page}/{size}")
    Result selectPage(@PathVariable("page") Integer page,
                      @PathVariable("size") Integer size);

    @PostMapping("/product/category/add")
    Result add(@RequestBody Category category);

    @PutMapping("/product/category/update")
    Result update(@RequestBody Category category);

    @DeleteMapping("/product/category/delete/{categoryId}")
    Result delete(@PathVariable("categoryId") Long categoryId);

    @GetMapping("/product/promo")
    Result promo(@RequestParam("categoryName") String categoryName);

    /**
     * 以下为购物车服务调用的接口
     *
     * @param productId 商品id
     * @return 单个商品信息
     */
    @GetMapping("/product/cart/{productId}")
    Product cartProductDetail(@PathVariable("productId") Long productId);

    /**
     * 以下为购物车服务调用的接口
     *
     * @param curPage 当前页
     * @param size 页容量
     * @return
     */
    @GetMapping("/product/score/{curPage}/{size}")
    Result queryScorePage(@PathVariable("curPage") Integer curPage, @PathVariable("size") Integer size);

    @PostMapping("/product/score/add")
    Result add(@RequestBody Score score);

    @PutMapping("/product/score/update")
    Result update(@RequestBody Score score);

    @DeleteMapping("/product/score/delete/{scoreId}")
    Result deleteByScoreId(@PathVariable("scoreId") Long scoreId);


}
