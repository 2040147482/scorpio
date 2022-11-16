package com.leslie.collect.controller;

import com.leslie.collect.service.CollectService;
import com.leslie.collect.vo.CollectParams;
import com.leslie.utils.Result;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * @author 20110
 */
@RestController
@RequestMapping("collect")
public class CollectController {

    @Resource
    private CollectService collectService;

    @PostMapping("/save")
    public Result saveProduct(@RequestBody CollectParams collectParams) {
        return collectService.saveProduct(collectParams);
    }

    @PostMapping("/show/{userId}")
    public Result showCollect(@PathVariable("userId") Long userId) {
        return collectService.show(userId);
    }
}