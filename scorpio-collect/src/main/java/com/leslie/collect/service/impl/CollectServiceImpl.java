package com.leslie.collect.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.leslie.collect.service.CollectService;
import com.leslie.collect.mapper.CollectMapper;
import com.leslie.collect.vo.CollectParams;
import com.leslie.pojo.Collect;
import com.leslie.utils.Result;
import org.springframework.stereotype.Service;


/**
 * @author 20110
 * @description 针对表【tb_collect(收藏表)】的数据库操作Service实现
 * @createDate 2022-11-12 21:17:07
 */
@Service
public class CollectServiceImpl extends ServiceImpl<CollectMapper, Collect> implements CollectService {


    @Override
    public Result saveProduct(CollectParams collectParams) {
        Collect collect = new Collect();
        collect.setUserId(collectParams.getUserId());
        collect.setProductId(collectParams.getProductId());
        save(collect);
        return Result.ok();
    }
}
