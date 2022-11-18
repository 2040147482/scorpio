package com.leslie.admin.controller;

import com.leslie.admin.service.AdminUserService;
import com.leslie.utils.Result;
import com.leslie.vo.AdminLoginParam;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * @author 20110
 */
@RestController
@RequestMapping("admin")
public class AdminController {

    @Resource
    private AdminUserService adminUserService;

    @PostMapping("/login")
    public Result login(@RequestBody AdminLoginParam adminLoginParam){
        return adminUserService.login(adminLoginParam);
    }
}
