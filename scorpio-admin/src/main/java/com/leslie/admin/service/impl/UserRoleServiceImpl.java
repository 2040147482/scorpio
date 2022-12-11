package com.leslie.admin.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.leslie.admin.pojo.UserRole;
import com.leslie.admin.service.UserRoleService;
import com.leslie.admin.mapper.UserRoleMapper;
import org.springframework.stereotype.Service;

/**
 * @author 20110
 * @description 针对表【tb_user_role(用户角色关系表)】的数据库操作Service实现
 * @createDate 2022-12-11 13:06:28
 */
@Service
public class UserRoleServiceImpl extends ServiceImpl<UserRoleMapper, UserRole>
        implements UserRoleService {

}


