package com.leslie.clients;

import com.leslie.pojo.Address;
import com.leslie.vo.AddressIdsParam;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author 20110
 */
@FeignClient("member-service")
public interface AddressClient {

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
}