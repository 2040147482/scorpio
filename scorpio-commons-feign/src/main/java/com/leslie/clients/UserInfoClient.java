package com.leslie.clients;

import com.leslie.utils.Result;
import com.leslie.vo.UserInfoParam;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;


/**
 * @author 20110
 */
@FeignClient("member-service")
public interface UserInfoClient {

    @GetMapping("/userinfo/{page}/{size}")
    Result queryPage(@PathVariable("page") Integer page,
                     @PathVariable("size") Integer size);

    @PutMapping("/userinfo/update")
    Result update(@RequestBody UserInfoParam userInfoParam);

    @DeleteMapping("/userinfo/delete/{uid}")
    Result deleteById(@PathVariable("uid") Long uid);
}
