package com.leslie.admin.controller;

import com.leslie.clients.UserClient;
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
    private UserClient userClient;

    @GetMapping("/query/{page}/{size}")
    public Result queryPageForegroundUser(@PathVariable("page") Integer page,
                                          @PathVariable("size") Integer size) {
        return userClient.queryPage(page, size);
    }

    @PutMapping("/update")
    public Result update(@RequestBody UserInfoParam userInfoParam) {
        return userClient.update(userInfoParam);
    }

    @DeleteMapping("/delete/{uid}")
    public Result deleteById(@PathVariable("uid") Long uid) {
        return userClient.deleteById(uid);
    }
}
