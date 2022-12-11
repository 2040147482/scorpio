package com.leslie.admin.controller;

import com.leslie.clients.UserInfoClient;
import com.leslie.utils.Result;
import com.leslie.vo.UserInfoParam;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * @author 20110
 */
@RestController
@RequestMapping("admin/fguser")
public class ForegroundUserController {

    @Resource
    private UserInfoClient userInfoClient;

    @GetMapping("/query/{page}/{size}")
    public Result queryPageForegroundUser(@PathVariable("page") Integer page,
                                          @PathVariable("size") Integer size) {
        return userInfoClient.queryPage(page, size);
    }

    @PutMapping("/update")
    public Result update(@RequestBody UserInfoParam userInfoParam) {
        return userInfoClient.update(userInfoParam);
    }

    @DeleteMapping("/delete/{uid}")
    public Result deleteById(@PathVariable("uid") Long uid) {
        return userInfoClient.deleteById(uid);
    }
}
