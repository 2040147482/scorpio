package com.leslie.admin.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.leslie.admin.pojo.AdminUser;
import com.leslie.admin.service.AdminUserService;
import com.leslie.admin.mapper.AdminUserMapper;
import com.leslie.utils.Result;
import com.leslie.vo.AdminLoginParam;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * @author 20110
 * @description 针对表【tb_admin_user(管理员信息表)】的数据库操作Service实现
 * @createDate 2022-11-18 14:11:09
 */
@Service
public class AdminUserServiceImpl extends ServiceImpl<AdminUserMapper, AdminUser>
        implements AdminUserService {

    @Resource
    private AdminUserMapper adminMapper;

    @Override
    public Result login(AdminLoginParam adminLoginParam) {
        //封装查询
        QueryWrapper<AdminUser> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("account", adminLoginParam.getAccount());
        queryWrapper.eq("password", adminLoginParam.getPassword());

        AdminUser adminUser = adminMapper.selectOne(queryWrapper);
        if (adminUser == null) {
            return Result.fail("账号或密码错误!");
        }
        return Result.ok();
    }
}

