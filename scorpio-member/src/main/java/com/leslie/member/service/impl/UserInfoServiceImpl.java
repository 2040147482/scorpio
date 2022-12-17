package com.leslie.member.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.leslie.member.mapper.UserMapper;
import com.leslie.member.pojo.User;
import com.leslie.pojo.UserInfo;
import com.leslie.member.service.UserInfoService;
import com.leslie.member.mapper.UserInfoMapper;
import com.leslie.utils.Result;
import com.leslie.vo.UserInfoParam;
import org.springframework.beans.BeanUtils;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author 20110
 * @description 针对表【tb_user_info(用户信息表)】的数据库操作Service实现
 * @createDate 2022-11-18 14:36:50
 */
@CacheConfig(cacheNames = "user")
@Service
public class UserInfoServiceImpl extends ServiceImpl<UserInfoMapper, UserInfo>
        implements UserInfoService {

    @Resource
    private UserInfoMapper userInfoMapper;

    @Resource
    private UserMapper userMapper;

    @Cacheable(key = "'user:'+#page+':'+#size")
    @Override
    public Result queryPageUserInfo(Integer page, Integer size) {
        Page<UserInfo> userInfoPage = new Page<>(page, size);
        userInfoPage = userInfoMapper.selectPage(userInfoPage, null);

        List<UserInfo> userInfoList = userInfoPage.getRecords();
        long total = userInfoPage.getTotal();
        System.out.println("查询条数：" + total);
        return Result.ok(userInfoList, total);
    }

    @CacheEvict(value = "user", allEntries = true)
    @Transactional
    @Override
    public Result updateUserInfo(UserInfoParam userInfoParam) {
        QueryWrapper<UserInfo> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("user_id", userInfoParam.getUserId());

        UserInfo userInfo = new UserInfo();
        BeanUtils.copyProperties(userInfoParam, userInfo);
        int row = userInfoMapper.update(userInfo, queryWrapper);
        if (row == 0) {
            return Result.fail("数据更新失败！");
        }
        return Result.ok();
    }

    @CacheEvict(value = "user", allEntries = true)
    @Transactional
    @Override
    public Result deleteById(Long uid) {
        QueryWrapper<UserInfo> userInfoQueryWrapper = new QueryWrapper<>();
        userInfoQueryWrapper.eq("user_id", uid);
        int row1 = userInfoMapper.delete(userInfoQueryWrapper);
        int row2 = userMapper.deleteById(uid);
        if (row1 == 0 || row2 == 0) {
            return Result.fail("数据更新失败！");
        }
        return Result.ok();
    }
}




