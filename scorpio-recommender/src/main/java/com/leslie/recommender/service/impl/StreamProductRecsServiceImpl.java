package com.leslie.recommender.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.leslie.clients.ProductClient;
import com.leslie.pojo.Product;
import com.leslie.recommender.mapper.StreamProductRecsMapper;
import com.leslie.recommender.pojo.StreamProductRecs;
import com.leslie.recommender.service.StreamProductRecsService;
import com.leslie.utils.Result;
import com.leslie.vo.ProductIdsParam;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class StreamProductRecsServiceImpl extends ServiceImpl<StreamProductRecsMapper, StreamProductRecs>
        implements StreamProductRecsService {

    @Resource
    private StreamProductRecsMapper streamProductRecsMapper;

    @Resource
    private ProductClient productClient;

    @Override
    public Result getStreamProductRecs(Integer userId){
        QueryWrapper<StreamProductRecs> wrapper = new QueryWrapper<>();
        wrapper.eq("user_id",userId);

        String UserByProductRecs = streamProductRecsMapper.selectOne(wrapper).getRecs();
        List<String> recs = Arrays.stream(UserByProductRecs.replace("((","").replace("))","")
                .split("\\), \\(")).collect(Collectors.toList());
        List<Long> recByProductIds = new ArrayList<>();
        for(String rec:recs){
            Long productId = Long.parseLong(rec.split(",")[0].trim());
            recByProductIds.add(productId);
        }

        ProductIdsParam rec = new ProductIdsParam();
        rec.setProductIds(recByProductIds);
        //根据productId查找商品
        List<Product> productList = productClient.ids(rec);

        return Result.ok(productList);
    }
}