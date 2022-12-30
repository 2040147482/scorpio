package com.leslie.admin.controller;

import com.leslie.clients.ProductClient;
import com.leslie.pojo.Score;
import com.leslie.utils.Result;
import org.springframework.web.bind.annotation.*;

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
    @PostMapping("/add")
    public Result add(@RequestBody Score score){
        return productClient.add(score);
    }

    @PutMapping("/update")
    public Result update(@RequestBody Score score){
        return productClient.update(score);
    }

    @DeleteMapping("/delete/{scoreId}")
    public Result delete(@PathVariable("scoreId") Long scoreId){
        return productClient.deleteByScoreId(scoreId);
    }


}
