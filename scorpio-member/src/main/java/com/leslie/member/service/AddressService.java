package com.leslie.member.service;

import com.leslie.member.vo.AddAddressVo;
import com.leslie.pojo.Address;
import com.baomidou.mybatisplus.extension.service.IService;
import com.leslie.utils.Result;
import com.leslie.vo.AddressIdsParam;

import java.util.List;

/**
 * @author 20110
 * @description 针对表【tb_address(地址表)】的数据库操作Service
 * @createDate 2022-12-14 15:39:32
 */
public interface AddressService extends IService<Address> {

    /**
     * 根据用户id查询用户地址业务
     *
     * @param userId 用户id
     * @return
     */
    Result show(Long userId);

    /**
     * 添加用户地址业务
     *
     * @param addAddressVo 用户id、联系人、手机号、详情地址
     * @return
     */
    Result add(AddAddressVo addAddressVo);

    /**
     * 地址删除业务
     *
     * @param addessId 要删除的地址id
     * @return
     */
    Result delete(Integer addessId);

    /**
     * 根据id查询地址信息
     *
     * @param addressId 地址主键id
     * @return
     */
    Address queryByAddressId(Integer addressId);

    /**
     * 修改地址信息
     *
     * @param address
     * @return
     */
    Result update(Address address);

    /**
     * 根据地址id查询Address集合
     *
     * @param addressIdsParam
     * @return
     */
    List<Address> ids(AddressIdsParam addressIdsParam);
}
