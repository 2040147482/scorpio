package com.leslie.clients;

import com.leslie.pojo.Address;
import com.leslie.utils.Result;
import com.leslie.vo.AddressIdsParam;
import com.leslie.vo.UserInfoParam;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author 20110
 */
@FeignClient("member-service")
public interface UserClient {

    @GetMapping("/user/address/query/{addressId}")
    Address queryByAddressId(@PathVariable("addressId") Integer addressId);

    /**
     * 根据地址id查询Address集合
     *
     * @param addressIdsParam
     * @return
     */
    @PostMapping("/user/address/ids")
    List<Address> ids(@RequestBody AddressIdsParam addressIdsParam);


    @GetMapping("/userinfo/{page}/{size}")
    Result queryPage(@PathVariable("page") Integer page,
                     @PathVariable("size") Integer size);

    @PutMapping("/userinfo/update")
    Result update(@RequestBody UserInfoParam userInfoParam);

    @DeleteMapping("/userinfo/delete/{uid}")
    Result deleteById(@PathVariable("uid") Long uid);
}
