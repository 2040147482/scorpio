package com.leslie.product.controller;

import com.leslie.pojo.Product;
import com.leslie.product.service.CategoryService;
import com.leslie.product.service.ProductService;
import com.leslie.vo.ProductIdsParam;
import com.leslie.utils.Result;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author 20110
 */
@RestController
@RequestMapping("product")
public class ProductController {

    @Resource
    private ProductService productService;

    @Resource
    private CategoryService categoryService;

    @GetMapping("/category/all")
    public Result allCategory() {
        return categoryService.all();
    }

    @PostMapping("/promo")
    public Result promo(@RequestParam("categoryName") String categoryName) {
        if (categoryName.isEmpty()) {
            return Result.fail("空参无法查询");
        }
        return productService.promo(categoryName);
    }

    @PostMapping("/detail/{productId}")
    public Result detail(@PathVariable("productId") Long productId) {
        if (productId == null) {
            return Result.fail("请请求正确的路径");
        }
        return productService.detail(productId);
    }

    /**
     * 供搜索服务调用
     *
     * @return 全部商品
     */
    @GetMapping("/list")
    public List<Product> list() {
        return productService.list();
    }

    @PostMapping("/ids")
    public List<Product> ids(@RequestBody ProductIdsParam productIdsParam) {
        return productService.ids(productIdsParam);
    }

    /**
     * 以下接口后台管理模块调用
     */
    @GetMapping("/query/{page}/{size}")
    public Result queryPage(@PathVariable("page") Integer page,
                            @PathVariable("size") Integer size) {
        return productService.queryPage(page, size);
    }

    @PostMapping("/save")
    public Result save(@RequestBody Product product) {
        return productService.saveProduct(product);
    }

    @PutMapping("/update")
    public Result update(@RequestBody Product product) {
        return productService.updateProduct(product);
    }

    @DeleteMapping("/remove/{id}")
    public Result deleteByProductId(@PathVariable("id") Long id) {
        return productService.removeProduct(id);
    }

    /**
     * 以下为购物车服务调用的接口
     */
    @GetMapping("/cart/{productId}")
    public Product cartProductDetail(@PathVariable("productId") Long productId) {
        Result result = productService.detail(productId);
        Product product = (Product) result.getData();
        return product;
    }
}
