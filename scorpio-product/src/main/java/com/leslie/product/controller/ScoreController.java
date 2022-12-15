package com.leslie.product.controller;

import com.leslie.product.service.ScoreService;
import com.leslie.utils.Result;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * @author 20110
 * @descript 评分API接口
 */
@RestController
@RequestMapping("/product/score")
public class ScoreController {

    @Resource
    private ScoreService scoreService;

    @GetMapping("/query/{userId}/{productId}")
    public Result query(@PathVariable("userId") Long userId,
                        @PathVariable("productId") Long productId) {
        return scoreService.queryByUserIdAndProductId(userId, productId);
    }

    @GetMapping("/{curPage}/{size}")
    public Result queryScorePage(@PathVariable("curPage") Integer curPage,
                            @PathVariable("size") Integer size) {
        return scoreService.queryPage(curPage, size);
    }
}
