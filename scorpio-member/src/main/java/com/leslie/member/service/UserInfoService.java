package com.leslie.member.service;

import com.leslie.pojo.UserInfo;
import com.baomidou.mybatisplus.extension.service.IService;
import com.leslie.utils.Result;
import com.leslie.vo.UserInfoParam;

import java.util.List;

/**
* @author 20110
* @description 针对表【tb_user_info(用户信息表)】的数据库操作Service
* @createDate 2022-11-18 14:36:50
*/
public interface UserInfoService extends IService<UserInfo> {

    /**
     * 分页查询用户信息
     * @param page
     * @param size
     * @return
     */
    Result queryPageUserInfo(Integer page, Integer size);

    /**
     * 用户数据修改业务
     * @param userInfoParam
     * @return
     */
    Result updateUserInfo(UserInfoParam userInfoParam);

    /**
     * 根据用户id删除用户数据
     * @param uid 用户id
     * @return
     */
    Result deleteById(Long uid);
}
