package com.leslie.member.controller;

import com.leslie.member.service.AddressService;
import com.leslie.member.vo.AddAddressVo;
import com.leslie.pojo.Address;
import com.leslie.utils.Result;
import com.leslie.vo.AddressIdsParam;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author 20110
 * @descript 地址controller
 */
@RestController
@RequestMapping("user/address")
public class AddressController {

    @Resource
    private AddressService addressService;

    @GetMapping("/list/{userId}")
    public Result show(@PathVariable("userId") Long userId) {
        return addressService.show(userId);
    }

    @PostMapping("/add")
    public Result add(@RequestBody AddAddressVo addAddressVo) {
        return addressService.add(addAddressVo);
    }

    @PutMapping("/update")
    public Result update(@RequestBody Address address) {
        return addressService.update(address);
    }

    @DeleteMapping("/delete/{addessId}")
    public Result delete(@PathVariable("addessId") Integer addessId) {
        return addressService.delete(addessId);
    }

    @GetMapping("/query/{addressId}")
    public Address queryByAddressId(@PathVariable("addressId") Integer addressId) {
        return addressService.queryByAddressId(addressId);
    }

    @PostMapping("/ids")
    public List<Address> ids(@RequestBody AddressIdsParam addressIdsParam) {

        return addressService.ids(addressIdsParam);
    }

}
