package com.leslie.admin.service;

import com.leslie.admin.pojo.Permission;
import com.baomidou.mybatisplus.extension.service.IService;
import com.leslie.utils.Result;

/**
* @authork 20110
* @description 针对表【tb_permission(权限表)】的数据库操作Service
* @createDate 2022-12-11 13:23:13
*/
public interface PermissionService extends IService<Permission> {

    /**
     * 查询
     *      菜单列表（树形）
     * @return
     */
    Result findAll();
}
