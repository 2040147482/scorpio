package com.leslie.product.controller;

import com.leslie.pojo.Category;
import com.leslie.product.service.CategoryService;
import com.leslie.utils.Result;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * @author 20110
 * @descript 类别管理controller
 */
@RestController
@RequestMapping("product/category")
public class CategoryController {


    @Resource
    private CategoryService categoryService;

    @GetMapping("/all")
    public Result allCategory() {
        return categoryService.all();
    }

    @GetMapping("/{curPage}/{size}")
    public Result list(@PathVariable("curPage") Integer curPage,
                       @PathVariable("size") Integer size) {
        return categoryService.selectPage(curPage, size);
    }

    @PostMapping("/add")
    public Result add(@RequestBody Category category){
        return categoryService.add(category);
    }

    @PutMapping("/update")
    public Result update(@RequestBody Category category) {
        return categoryService.update(category);
    }

    @DeleteMapping("/delete/{categoryId}")
    public Result delete(@PathVariable("categoryId") Long categoryId){
        return categoryService.delete(categoryId);
    }


}
