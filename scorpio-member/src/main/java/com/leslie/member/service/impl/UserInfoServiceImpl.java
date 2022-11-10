package com.leslie.member.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.leslie.member.pojo.UserInfo;
import com.leslie.member.mapper.UserInfoMapper;
import com.leslie.member.service.UserInfoService;
import org.springframework.stereotype.Service;

/**
 * @author 20110
 * @description 针对表【tb_user_info(用户信息表)】的数据库操作Service实现
 * @createDate 2022-11-06 16:34:16
 */
@Service
public class UserInfoServiceImpl extends ServiceImpl<UserInfoMapper, UserInfo>
        implements UserInfoService {

}




