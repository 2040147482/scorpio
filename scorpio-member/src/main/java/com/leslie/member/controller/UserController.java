package com.leslie.member.controller;

import com.leslie.member.vo.LoginWithCodeVo;
import com.leslie.member.service.UserService;
import com.leslie.utils.Result;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * @author 20110
 */
@RestController
@RequestMapping("/user")
public class UserController {

    @Resource
    private UserService userService;

    @PostMapping("/code")
    public Result sendCode(@RequestParam("phone") String phone) {
        return userService.sendCode(phone);
    }

    @PostMapping("/login")
    public Result login(@RequestBody LoginWithCodeVo loginForm) {
        return userService.loginWithCode(loginForm);
    }

}
