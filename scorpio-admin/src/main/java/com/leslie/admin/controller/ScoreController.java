package com.leslie.admin.controller;

import com.leslie.clients.ProductClient;
import com.leslie.utils.Result;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;


/**
 * @author 20110
 * @descript 评分管理API接口
 */
@RestController
@RequestMapping("admin/score")
public class ScoreController {

   @Resource
   private ProductClient productClient;

    @GetMapping("/query/{curPage}/{size}")
    public Result queryPage(@PathVariable("curPage") Integer curPage, @PathVariable("size") Integer size) {
        return productClient.queryScorePage(curPage, size);
    }
}
