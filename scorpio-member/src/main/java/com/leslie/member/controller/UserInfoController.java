package com.leslie.member.controller;

import com.leslie.member.service.UserInfoService;
import com.leslie.utils.Result;
import com.leslie.vo.UserInfoParam;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * @author 20110
 */
@RestController
@RequestMapping("userinfo")
public class UserInfoController {

    @Resource
    private UserInfoService userInfoService;

    /**
     * 后台管理服务调用
     *
     * @return 所有用户信息集合
     */
    @GetMapping("/{page}/{size}")
    public Result queryPage(@PathVariable("page") Integer page,
                            @PathVariable("size") Integer size) {
        return userInfoService.queryPageUserInfo(page, size);
    }

    @PutMapping("/update")
    public Result update(@RequestBody UserInfoParam userInfoParam) {
        return userInfoService.updateUserInfo(userInfoParam);
    }

    @DeleteMapping("/delete/{uid}")
    public Result deleteById(@PathVariable("uid") Long uid) {
        return userInfoService.deleteById(uid);
    }

}
