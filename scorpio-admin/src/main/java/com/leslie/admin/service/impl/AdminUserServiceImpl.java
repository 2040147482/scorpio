package com.leslie.admin.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.leslie.admin.pojo.AdminUser;
import com.leslie.admin.service.AdminUserService;
import com.leslie.admin.mapper.AdminUserMapper;
import org.springframework.stereotype.Service;

/**
* @author 20110
* @description 针对表【tb_admin_user(管理员信息表)】的数据库操作Service实现
* @createDate 2022-11-18 14:11:09
*/
@Service
public class AdminUserServiceImpl extends ServiceImpl<AdminUserMapper, AdminUser>
    implements AdminUserService{

}

