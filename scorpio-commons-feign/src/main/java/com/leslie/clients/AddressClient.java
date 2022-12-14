package com.leslie.clients;

import com.leslie.pojo.Address;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

/**
 * @author 20110
 */
@FeignClient("member-service")
public interface AddressClient {

    @GetMapping("/user/address/query/{addressId}")
    Address queryByAddressId(@PathVariable("addressId") Integer addressId);

}