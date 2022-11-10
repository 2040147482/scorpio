package com.leslie.member.service.impl;


import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.bean.copier.CopyOptions;
import cn.hutool.core.util.RandomUtil;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.leslie.member.dto.UserDTO;
import com.leslie.member.pojo.User;
import com.leslie.member.mapper.UserMapper;
import com.leslie.member.service.UserService;
import com.leslie.member.utils.RegexUtils;
import com.leslie.member.vo.LoginWithCodeVo;
import com.leslie.member.vo.Result;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

import static com.leslie.member.constants.RedisConstants.*;
import static com.leslie.member.constants.RedisConstants.LOGIN_USER_TTL;
import static com.leslie.member.constants.SystemConstants.USER_NICK_NAME_PREFIX;


/**
 * @author 20110
 * @description 针对表【tb_user(用户登录表)】的数据库操作Service实现
 * @createDate 2022-11-06 16:34:04
 */
@Slf4j
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User>
        implements UserService {

    @Resource
    private StringRedisTemplate stringRedisTemplate;

    @Override
    public Result sendCode(String phone) {
        // 1.校验手机号
        if (!RegexUtils.isPhoneInvalid(phone)) {
            // 2.如果不符合，返回错误信息
            return Result.fail("手机号格式错误！");
        }
        // 3.生成验证码
        String code = RandomUtil.randomNumbers(6);

        // 4.保验证码到redis
        stringRedisTemplate.opsForValue().set(LOGIN_CODE_KEY + phone, code, LOGIN_CODE_TTL, TimeUnit.MINUTES);
        // TODO 5。发送验证码
        log.debug("发送短信验证码成功，验证码：{}", code);

        return Result.ok();
    }

    @Override
    @Transactional
    public Result loginWithCode(LoginWithCodeVo loginForm) {
        //校验手机号
        String phone = loginForm.getPhone();
        if (!RegexUtils.isPhoneInvalid(phone)) {
            return Result.fail("手机号格式错误！");
        }
        //校验验证码
        String cacheCode = stringRedisTemplate.opsForValue().get(LOGIN_CODE_KEY + phone);
        String code = loginForm.getCode();
        if (cacheCode == null || !cacheCode.equals(code)) {
            return Result.fail("验证码错误");
        }
        //一致，根据手机号查询用户
        User user = query().eq("phone", phone).one();
        if (user == null) {
            // 不存在，创建新用户并保存
            user = createUserWithPhone(phone);
        }
        //随机生成token，作为登录令牌
        String token = UUID.randomUUID().toString();
        // 将User对象转为HashMap存储
        UserDTO userDTO = BeanUtil.copyProperties(user, UserDTO.class);
        Map<String, Object> userMap = BeanUtil.beanToMap(userDTO, new HashMap<>(),
                CopyOptions.create()
                        .setFieldValueEditor((fieldName, fieldValue) -> {
                                    if (fieldValue == null) {
                                        fieldValue = "";
                                    } else {
                                        fieldValue = fieldValue.toString();
                                    }
                                    return fieldValue;
                                }
                        )
        );
        //存储
        String tokenKey = LOGIN_USER_KEY + token;
        stringRedisTemplate.opsForHash().putAll(tokenKey, userMap);
        //设置token有效期
        stringRedisTemplate.expire(tokenKey, LOGIN_USER_TTL, TimeUnit.MINUTES);

        //返回token
        return Result.ok(token);
    }

    private User createUserWithPhone(String phone) {
        User user = new User();
        user.setPhone(phone);
        user.setNickName(USER_NICK_NAME_PREFIX + RandomUtil.randomString(10));
        save(user);
        return user;
    }
}




