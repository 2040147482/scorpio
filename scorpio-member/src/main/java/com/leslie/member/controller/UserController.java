package com.leslie.member.controller;

import com.leslie.member.vo.LoginWithCodeVo;
import com.leslie.member.service.UserService;
import com.leslie.vo.UploadUserImgVo;
import com.leslie.utils.Result;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

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

    @PostMapping("/upload/{userId}")
    public Result uploadFile(@PathVariable("userId") Long userId, @RequestPart("file") MultipartFile file) {
        UploadUserImgVo userImgVo = new UploadUserImgVo();
        userImgVo.setUserId(userId);
        userImgVo.setFile(file);
        return userService.uploadHeadImg(userImgVo);
    }

}
