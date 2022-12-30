package com.leslie.recommender.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.leslie.clients.ProductClient;
import com.leslie.pojo.Product;
import com.leslie.recommender.mapper.AverageProductMapper;
import com.leslie.recommender.mapper.RateMoreProductRecsMapper;
import com.leslie.recommender.pojo.AverageProduct;
import com.leslie.recommender.pojo.Products;
import com.leslie.recommender.pojo.RateMoreProductRecs;
import com.leslie.recommender.service.RateMoreProductRecsService;
import com.leslie.utils.Result;
import com.leslie.vo.ProductIdsParam;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

@Service
public class RateMoreProductRecsServiceimpl extends ServiceImpl<RateMoreProductRecsMapper, RateMoreProductRecs>
        implements RateMoreProductRecsService {
    @Resource
    private RateMoreProductRecsMapper rateMoreProductRecsMapper;
    @Resource
    private ProductClient productClient;

    @Resource
    private AverageProductMapper averageProductMapper;

    @Override
    public Result getRateMoreProductRecs(){
        QueryWrapper<RateMoreProductRecs> wrapper = new QueryWrapper<>();
        wrapper.orderByDesc("count").last("LIMIT 20");
        //获取评分最多电影的条数
        List<Long> recByProductIds = new ArrayList<>();;
        List<RateMoreProductRecs> rateMores = rateMoreProductRecsMapper.selectList(wrapper);
        for(RateMoreProductRecs rateMore: rateMores){
            recByProductIds.add(Long.parseLong(rateMore.getProductId().toString()));
        }

        ProductIdsParam rec = new ProductIdsParam();
        rec.setProductIds(recByProductIds);
        //根据productId查找商品
        List<Product> proList = productClient.ids(rec);
        //赋予平均分
        List<Products> productLists = new ArrayList<>();
        for(Product product: proList){
            productLists.add(getProByScore(product));
        }

        return Result.ok(productLists);
    }
    public Products getProByScore(Product pro){
        Products product = new Products();

        //赋予初值
        product.setProductId(pro.getProductId());
        product.setProductName(pro.getProductName());
        product.setCategoryId(pro.getCategoryId());
        product.setPrice(pro.getPrice());
        product.setProductStatus(pro.getProductStatus());
        product.setProductStock(pro.getProductStock());
        product.setProductSales(pro.getProductSales());
        product.setImageUrl(pro.getImageUrl());
        product.setCreateTime(pro.getCreateTime());
        product.setUpdateTime(pro.getUpdateTime());

        QueryWrapper<AverageProduct> Avgwrapper = new QueryWrapper<>();
        Avgwrapper.eq("product_id",pro.getProductId());
        Double score = averageProductMapper.selectOne(Avgwrapper).getAvg();
        if(score.isNaN() || score == 0D){
            product.setAvgScore(0D);
        }else {
            //保留两位小数
            product.setAvgScore(Double.parseDouble(String.format("%.2f",score)));
        }

        return product;

    }
}
