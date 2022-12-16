package com.leslie.admin.service;

import com.leslie.admin.pojo.User;
import com.baomidou.mybatisplus.extension.service.IService;
import com.leslie.admin.vo.LoginVo;
import com.leslie.admin.vo.UpdateUserVo;
import com.leslie.utils.Result;
import com.leslie.vo.UploadUserImgVo;

/**
* @author 20110
* @description 针对表【tb_user(用户表)】的数据库操作Service
* @createDate 2022-12-11 13:06:22
*/
public interface UserService extends IService<User> {

    /**
     * 登录服务
     * @param loginVo 用户名
     * @return
     */
    Result login(LoginVo loginVo);

    /**
     * 分页查询后台用户
     * @param page 当前页码
     * @param size 条数
     * @return
     */
    Result queryPageUsers(Integer page, Integer size);

    /**
     * 修改后台用户
     * @param updateUserVo
     * @return
     */
    Result updateUser(UpdateUserVo updateUserVo);

    /**
     * 根据用户id删除用户信息
     * @param uid 用户id
     * @return
     */
    Result deleteById(Integer uid);

    /**
     * 上传用户头像
     * @param imgVo 用户id、图片
     * @return
     */
    Result uploadUserIcon(UploadUserImgVo imgVo);
}
