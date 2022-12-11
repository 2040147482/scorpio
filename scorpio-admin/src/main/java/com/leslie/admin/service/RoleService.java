package com.leslie.admin.service;

import com.leslie.admin.pojo.Role;
import com.baomidou.mybatisplus.extension.service.IService;
import com.leslie.utils.Result;

/**
* @author 20110
* @description 针对表【tb_role(角色表)】的数据库操作Service
* @createDate 2022-12-11 13:31:01
*/
public interface RoleService extends IService<Role> {

    /**
     * 分页查询
     * @param curPage 页码
     * @param size 页数
     * @return Result
     */
    Result selectPage(Integer curPage, Integer size);
}
