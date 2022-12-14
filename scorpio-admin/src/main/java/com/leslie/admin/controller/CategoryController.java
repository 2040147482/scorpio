package com.leslie.admin.controller;

import com.leslie.clients.ProductClient;
import com.leslie.pojo.Category;
import com.leslie.utils.Result;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * @author 20110
 * @descript 类别管理controller
 */
@RestController
@RequestMapping("admin/category")
public class CategoryController {

    @Resource
    private ProductClient productClient;

    @GetMapping("/{curPage}/{size}")
    public Result list(@PathVariable("curPage") Integer curPage,
                       @PathVariable("size") Integer size) {
        return productClient.selectPage(curPage, size);
    }

    @PostMapping("/add")
    public Result add(@RequestBody Category category) {
        return productClient.add(category);
    }

    @PutMapping("/update")
    public Result update(@RequestBody Category category) {
        return productClient.update(category);
    }

    @DeleteMapping("/delete/{categoryId}")
    public Result delete(@PathVariable("categoryId") Long categoryId) {
        return productClient.delete(categoryId);
    }

    @GetMapping("/promo")
    public Result promo(@RequestParam("categoryName") String categoryName) {
        if (categoryName.isEmpty()) {
            return Result.fail("参数为null，无法查询！");
        }
        return productClient.promo(categoryName);
    }


}
