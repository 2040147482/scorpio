package com.leslie.admin.controller;

import com.leslie.admin.service.UserService;
import com.leslie.admin.vo.UpdateUserVo;
import com.leslie.utils.Result;
import com.leslie.admin.vo.LoginVo;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;


/**
 * @author 20110
 */
@RestController
@RequestMapping("admin/user")
public class UserController {

    @Resource
    private UserService userService;

    @PostMapping("/login")
    public Result login(@RequestBody LoginVo loginVo) {
        return userService.login(loginVo);
    }

    @GetMapping("/query/{page}/{size}")
    public Result queryPageUsers(@PathVariable("page") Integer page,
                                 @PathVariable("size") Integer size) {
        return userService.queryPageUsers(page, size);
    }

    @PutMapping("/update")
    public Result updateUser(@RequestBody UpdateUserVo updateUserVo) {
        if (updateUserVo == null) {
            return Result.fail("请提供有用的参数，无法更新数据！");
        }
        return userService.updateUser(updateUserVo);
    }

    @DeleteMapping("/delete/{uid}")
    public Result deleleById(@PathVariable("uid") Integer uid) {
        return userService.deleteById(uid);
    }
}
