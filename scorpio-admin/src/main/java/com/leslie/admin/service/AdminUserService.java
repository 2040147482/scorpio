package com.leslie.admin.service;

import com.leslie.admin.pojo.AdminUser;
import com.baomidou.mybatisplus.extension.service.IService;
import com.leslie.utils.Result;
import com.leslie.vo.AdminLoginParam;

/**
* @author 20110
* @description 针对表【tb_admin_user(管理员信息表)】的数据库操作Service
* @createDate 2022-11-18 14:11:09
*/
public interface AdminUserService extends IService<AdminUser> {

    /**
     * 后台管理登录服务
     * @param adminLoginParam 管理员登录账号、密码
     * @return Result 666 或 886
     */
    Result login(AdminLoginParam adminLoginParam);
}
