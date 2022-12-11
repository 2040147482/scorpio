package com.leslie.admin.controller;

import com.leslie.admin.service.PermissionService;
import com.leslie.utils.Result;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * @author 20110
 */
@RestController
@RequestMapping("admin/permission")
public class PermissionController {

    @Resource
    private PermissionService permissionService;

    @GetMapping("/query")
    public Result findAll() {
        return permissionService.findAll();
    }


}
