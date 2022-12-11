package com.leslie.admin.controller;

import com.leslie.admin.service.RoleService;
import com.leslie.utils.Result;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * @author 20110
 * @descript 角色controller
 */
@RestController
@RequestMapping("admin/role")
public class RoleController {

    @Resource
    private RoleService roleService;

    @GetMapping("/{curPage}/{size}")
    public Result list(@PathVariable("curPage") Integer curPage,
                       @PathVariable("size") Integer size) {
        return roleService.selectPage(curPage, size);
    }
}
