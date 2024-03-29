package com.leslie.admin.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.leslie.admin.pojo.User;
import com.leslie.admin.service.UserService;
import com.leslie.admin.mapper.UserMapper;
import com.leslie.admin.vo.LoginVo;
import com.leslie.admin.vo.UpdateUserVo;
import com.leslie.clients.FastdfsClient;
import com.leslie.utils.MD5;
import com.leslie.utils.Result;
import com.leslie.vo.UploadUserImgVo;
import org.springframework.beans.BeanUtils;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author 20110
 * @description 针对表【tb_user(用户表)】的数据库操作Service实现
 * @createDate 2022-12-11 13:06:22
 */
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User>
        implements UserService {

    @Resource
    private UserMapper userMapper;

    @Resource
    private FastdfsClient fastdfsClient;

    @Override
    public Result login(LoginVo loginVo) {
        QueryWrapper<User> userQueryWrapper = new QueryWrapper<>();
        userQueryWrapper.eq("username", loginVo.getUsername());
        userQueryWrapper.eq("password", MD5.encrypt(loginVo.getPassword()));
        User user = userMapper.selectOne(userQueryWrapper);
        if (user == null) {
            return Result.fail("用户名不存在或密码错误！");
        }
        if (user.getStatus() == 0) {
            return Result.fail("该用户已停用，请联系管理员!");
        }
        return Result.ok();
    }

    @Cacheable(cacheNames = "admin", key = "'admin:user:'+#p0+#p1")
    @Override
    public Result queryPageUsers(Integer page, Integer size) {
        Page<User> userPage = new Page<>(page, size);
        userPage = userMapper.selectPage(userPage, null);

        List<User> userList = userPage.getRecords();
        long total = userPage.getTotal();
        return Result.ok(userList, total);
    }

    @CacheEvict(cacheNames = "admin", allEntries = true)
    @Transactional
    @Override
    public Result updateUser(UpdateUserVo updateUserVo) {
        User user = new User();
        BeanUtils.copyProperties(updateUserVo, user);

        int row = userMapper.updateById(user);
        if (row == 0) {
            return Result.fail("用户数据更新失败！");
        }
        return Result.ok();
    }

    @CacheEvict(cacheNames = "admin", allEntries = true)
    @Transactional
    @Override
    public Result deleteById(Integer uid) {
        QueryWrapper<User> wrapper = new QueryWrapper<>();
        wrapper.eq("id", uid);
        int row = userMapper.delete(wrapper);
        if (row == 0) {
            return Result.fail("用户数据删除失败！");
        }
        return Result.ok();
    }

    @CacheEvict(cacheNames = "admin", allEntries = true)
    @Transactional(rollbackFor = Exception.class)
    @Override
    public Result uploadUserIcon(UploadUserImgVo imgVo) {
        User user = userMapper.selectById(imgVo.getUserId());
        if (user == null) {
            return Result.fail("用户不存在！");
        }
        String res = fastdfsClient.uploadFile(imgVo.getFile());
        if ("不支持该类型文件".equals(res)) {
            return Result.fail("目前只支持ico、jpg、jpeg、png后缀的图片！");
        }
        user.setIcon(res);
        int row = userMapper.updateById(user);
        if ("文件上传失败".equals(res) || row == 0) {
            return Result.fail("图片上传失败!");
        }
        return Result.ok("图片上传成功", res);
    }
}




