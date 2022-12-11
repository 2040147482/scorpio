package com.leslie.admin.service.impl;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.leslie.admin.pojo.Role;
import com.leslie.admin.service.RoleService;
import com.leslie.admin.mapper.RoleMapper;
import com.leslie.utils.Result;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author 20110
 * @description 针对表【tb_role(角色表)】的数据库操作Service实现
 * @createDate 2022-12-11 13:31:01
 */
@Service
public class RoleServiceImpl extends ServiceImpl<RoleMapper, Role>
        implements RoleService {

    @Resource
    private RoleMapper roleMapper;

    @Override
    public Result selectPage(Integer curPage, Integer size) {
        Page<Role> rolePage = new Page<>(curPage, size);
        rolePage = roleMapper.selectPage(rolePage, null);

        List<Role> roleList = rolePage.getRecords();
        long total = rolePage.getTotal();
        return Result.ok(roleList, total);
    }
}




