package com.leslie.product.controller;

import com.leslie.product.service.CategoryService;
import com.leslie.product.service.ProductService;
import com.leslie.utils.Result;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

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

    @GetMapping("/all")
    public Result all(){
        return productService.all();
    }

    @GetMapping("/category/all")
    public Result allCategory() {
        return categoryService.all();
    }

    @PostMapping("/promo")
    public Result promo(@RequestParam("categoryName") String categoryName){
        if (categoryName.isEmpty()) {
            return Result.fail("空参无法查询");
        }
        return productService.promo(categoryName);
    }
}
