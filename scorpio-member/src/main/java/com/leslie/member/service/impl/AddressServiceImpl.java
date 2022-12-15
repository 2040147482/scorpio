package com.leslie.member.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.leslie.member.vo.AddAddressVo;
import com.leslie.pojo.Address;
import com.leslie.member.service.AddressService;
import com.leslie.member.mapper.AddressMapper;
import com.leslie.utils.Result;
import com.leslie.vo.AddressIdsParam;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author 20110
 * @description 针对表【tb_address(地址表)】的数据库操作Service实现
 * @createDate 2022-12-14 15:39:32
 */
@Service
public class AddressServiceImpl extends ServiceImpl<AddressMapper, Address>
        implements AddressService {

    @Resource
    private AddressMapper addressMapper;

    @Override
    public Result show(Long userId) {
        QueryWrapper<Address> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("user_id", userId);
        List<Address> addressList = addressMapper.selectList(queryWrapper);
        return Result.ok(addressList);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public Result add(AddAddressVo addAddressVo) {
        Address address = new Address();
        BeanUtils.copyProperties(addAddressVo, address);
        boolean save = save(address);
        if (!save) {
            return Result.fail("系统繁忙，添加地址失败！");
        }
        return Result.ok();
    }

    @Override
    public Result update(Address address) {
        int row = addressMapper.updateById(address);
        if (row == 0) {
            return Result.fail("系统繁忙，地址修改失败！");
        }
        return Result.ok();
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public Result delete(Integer addessId) {
        int row = addressMapper.deleteById(addessId);
        if (row == 0) {
            return Result.fail("系统繁忙，地址删除失败！");
        }
        return Result.ok();
    }

    @Override
    public Address queryByAddressId(Integer addressId) {
        return addressMapper.selectById(addressId);
    }


    @Override
    public List<Address> ids(AddressIdsParam addressIdsParam) {
        List<Integer> addressIds = addressIdsParam.getAddressIds();
        List<Address> addressList = addressMapper.selectBatchIds(addressIds);
        return addressList;
    }
}




