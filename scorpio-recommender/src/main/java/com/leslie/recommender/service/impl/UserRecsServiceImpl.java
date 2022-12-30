package com.leslie.recommender.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.leslie.clients.ProductClient;
import com.leslie.pojo.Product;
import com.leslie.recommender.mapper.AverageProductMapper;
import com.leslie.recommender.mapper.UserRecsMapper;
import com.leslie.recommender.pojo.AverageProduct;
import com.leslie.recommender.pojo.Products;
import com.leslie.recommender.pojo.UserRecs;
import com.leslie.recommender.service.RateMoreProductRecsService;
import com.leslie.recommender.service.UserRecsService;
import com.leslie.utils.Result;
import com.leslie.vo.ProductIdsParam;
import org.apache.catalina.User;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserRecsServiceImpl extends ServiceImpl<UserRecsMapper, UserRecs>
        implements UserRecsService {
    @Resource
    private UserRecsMapper userRecsMapper;

    @Resource
    private RateMoreProductRecsService rateMoreProductRecsService;

    @Resource
    private ProductClient productClient;

    @Resource
    private AverageProductMapper averageProductMapper;

    @Override
    public Result getUserProductRecs(Integer userId){

        QueryWrapper<UserRecs> wrapper = new QueryWrapper<>();
        wrapper.eq("user_id",userId);
        UserRecs UserByProductRecs = userRecsMapper.selectOne(wrapper);

        //新用户给推荐热门商品
        if (UserByProductRecs == null){
            return rateMoreProductRecsService.getRateMoreProductRecs();
        }

        List<String> recs = Arrays.stream(UserByProductRecs.getRecs()
                .split(",")).collect(Collectors.toList());
        List<Long> recByProductIds = new ArrayList<>();
        for(String rec:recs){
            Long productId = Long.parseLong(rec.split(":")[0].trim());
            System.out.println(productId);
            recByProductIds.add(productId);
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

    //为商品赋予平均得分
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
