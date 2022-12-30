package com.leslie.recommender.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.leslie.clients.ProductClient;
import com.leslie.pojo.Product;
import com.leslie.recommender.mapper.AverageProductMapper;
import com.leslie.recommender.mapper.ItemCFProductRecsMapper;
import com.leslie.recommender.pojo.AverageProduct;
import com.leslie.recommender.pojo.ItemCFProductRecs;
import com.leslie.recommender.pojo.Products;
import com.leslie.recommender.service.ItemCFProductRecsService;
import com.leslie.utils.Result;
import com.leslie.vo.ProductIdsParam;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
public class ItemCFProductRecsServiceImpl extends ServiceImpl<ItemCFProductRecsMapper, ItemCFProductRecs>
        implements ItemCFProductRecsService {
    @Resource
    private ItemCFProductRecsMapper itemCFProductRecsMapper;

    @Resource
    private ProductClient productClient;

    @Resource
    private AverageProductMapper averageProductMapper;

    @Override
    public Result getItemCFProductRecs(Integer pId){
        QueryWrapper<ItemCFProductRecs> wrapper = new QueryWrapper<>();
        wrapper.eq("product_id",pId);

        String ItemByProductRecs = itemCFProductRecsMapper.selectOne(wrapper).getRecs();

        //拆分推荐列表
        List<String> recs = Arrays.stream(ItemByProductRecs.replace("((","").replace("))","")
                .split("\\), \\(")).collect(Collectors.toList());
        System.out.println(recs);
        List<Long> productByIds = new ArrayList<>();
        for(String rec: recs){
            Long productId = Long.parseLong(rec.split(",")[0].trim());
            productByIds.add(productId);
        }
        ProductIdsParam rec = new ProductIdsParam();

        rec.setProductIds(productByIds);
        //根据productId查找商品
        List<Product> proList = productClient.ids(rec);

        //赋予平均分
        List<Products> productLists = new ArrayList<>();
        for(Product product: proList){
            productLists.add(getProByScore(product));
        }

        System.out.println(productLists);

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
