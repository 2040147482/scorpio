package com.leslie.member.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.leslie.pojo.UserInfo;
import com.leslie.member.service.UserInfoService;
import com.leslie.member.mapper.UserInfoMapper;
import org.springframework.stereotype.Service;

/**
* @author 20110
* @description 针对表【tb_user_info(用户信息表)】的数据库操作Service实现
* @createDate 2022-11-18 14:36:50
*/
@Service
public class UserInfoServiceImpl extends ServiceImpl<UserInfoMapper, UserInfo>
    implements UserInfoService{

}




