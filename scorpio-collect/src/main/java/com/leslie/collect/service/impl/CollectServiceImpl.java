package com.leslie.collect.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.leslie.clients.ProductClient;
import com.leslie.collect.service.CollectService;
import com.leslie.collect.mapper.CollectMapper;
import com.leslie.collect.vo.CollectParams;
import com.leslie.pojo.Collect;
import com.leslie.pojo.Product;
import com.leslie.utils.Result;
import com.leslie.vo.ProductIdsParam;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;


/**
 * @author 20110
 * @description 针对表【tb_collect(收藏表)】的数据库操作Service实现
 * @createDate 2022-11-12 21:17:07
 */
@Service
public class CollectServiceImpl extends ServiceImpl<CollectMapper, Collect> implements CollectService {

    @Resource
    private CollectMapper collectMapper;

    @Resource
    private ProductClient productClient;

    @Override
    public Result saveProduct(CollectParams collectParams) {
        Collect collect = new Collect();
        collect.setUserId(collectParams.getUserId());
        collect.setProductId(collectParams.getProductId());
        save(collect);
        return Result.ok();
    }

    @Override
    public Result show(Long userId) {
        //查询商品id
        QueryWrapper<Collect> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("user_id", userId);
        queryWrapper.select("product_id");
        List<Long> ids = new ArrayList<>();
        List<Collect> collects = collectMapper.selectList(queryWrapper);
        for (Collect collect : collects) {
            ids.add(collect.getProductId());
        }

        //根据商品id集查询商品集
        ProductIdsParam productIdsParam = new ProductIdsParam();
        productIdsParam.setProductIds(ids);
        List<Product> products = productClient.ids(productIdsParam);

        //收藏数
        Long total = collectMapper.selectCount(queryWrapper);
        return Result.ok(products, total);
    }

    @Transactional
    @Override
    public Result removeCollect(CollectParams collectParams) {
        QueryWrapper<Collect> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("user_id", collectParams.getUserId());
        queryWrapper.eq("product_id", collectParams.getProductId());

        int row = collectMapper.delete(queryWrapper);
        if (row == 0) {
            return Result.fail("收藏的商品删除失败！");
        }

        return Result.ok();
    }
}
