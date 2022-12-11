package com.leslie.admin.controller;

import com.leslie.clients.ProductClient;
import com.leslie.pojo.Product;
import com.leslie.utils.Result;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * @author 20110
 */
@RestController
@RequestMapping("admin/product")
public class ProductController {

    @Resource
    private ProductClient productClient;

    @GetMapping("/query/{page}/{size}")
    public Result queryPage(@PathVariable("page") Integer page,
                            @PathVariable("size") Integer size) {
        return productClient.queryPage(page, size);
    }

    @PostMapping("/save")
    public Result save(@RequestBody Product product) {
        return productClient.save(product);
    }

    @PutMapping("/update")
    public Result update(@RequestBody Product product) {
        return productClient.update(product);
    }

    @DeleteMapping("/delete/{id}")
    public Result deleteByProductId(@PathVariable("id") Long id) {
        return productClient.deleteByProductId(id);
    }
}
