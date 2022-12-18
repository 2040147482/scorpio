package com.leslie.admin.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.leslie.admin.pojo.Permission;
import com.leslie.admin.service.PermissionService;
import com.leslie.admin.mapper.PermissionMapper;
import com.leslie.admin.utils.PermissionHelper;
import com.leslie.utils.Result;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author 20110
 * @description 针对表【tb_permission(权限表)】的数据库操作Service实现
 * @createDate 2022-12-11 13:23:13
 */
@Service
public class PermissionServiceImpl extends ServiceImpl<PermissionMapper, Permission>
        implements PermissionService {

    @Resource
    private PermissionMapper permissionMapper;

    @Cacheable(cacheNames = "admin", key = "'admin:perms'")
    @Override
    public Result findAll() {
        QueryWrapper<Permission> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("status", 1);
        //查询所有菜单
        List<Permission> permissionList = permissionMapper.selectList(queryWrapper);
        //所有菜单数据转换树形结构
        List<Permission> treeList = PermissionHelper.bulidTree(permissionList);
        return Result.ok(treeList);
    }
}




