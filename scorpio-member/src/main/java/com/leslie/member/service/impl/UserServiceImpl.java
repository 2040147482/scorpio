package com.leslie.member.service.impl;


import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.leslie.member.entity.User;
import com.leslie.member.mapper.UserMapper;
import com.leslie.member.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;


/**
 * @author 20110
 * @description 针对表【tb_user(用户登录表)】的数据库操作Service实现
 * @createDate 2022-11-06 16:34:04
 */
@Slf4j
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User>
        implements UserService {

}




