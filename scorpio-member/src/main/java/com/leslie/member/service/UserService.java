package com.leslie.member.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.leslie.member.pojo.User;
import com.leslie.member.vo.LoginWithCodeVo;
import com.leslie.member.vo.Result;

/**
 * @author 20110
 * @description 针对表【tb_user(用户登录表)】的数据库操作Service
 * @createDate 2022-11-06 16:34:04
 */
public interface UserService extends IService<User> {

    /**
     * 发送验证码
     *
     * @param phone
     * @return 返回成功或失败
     */
    Result sendCode(String phone);

    /**
     * 基于验证码登录
     *
     * @param loginForm 手机号、验证码
     * @return 返回token
     */
    Result loginWithCode(LoginWithCodeVo loginForm);
}
