package com.leslie.product.service;

import com.leslie.pojo.Product;
import com.baomidou.mybatisplus.extension.service.IService;
import com.leslie.vo.ProductIdsParam;
import com.leslie.utils.Result;

import java.util.List;

/**
 * @author 20110
 * @description 针对表【tb_product(商品表)】的数据库操作Service
 * @createDate 2022-11-11 19:51:56
 */
public interface ProductService extends IService<Product> {


    /**
     * 单类别名称
     * 1.根据类别名称获取类别的数据
     * 2.继续根据类别id查询商品数据 （销售量倒序 查询）
     *
     * @param categoryName 类别名称
     * @return Result
     */
    Result promo(String categoryName);

    /**
     * 查询商品详情
     *
     * @param productId 商品id
     * @return Result
     */
    Result detail(Long productId);

    /**
     * 供收藏服务使用,根据传入的商品id,查询商品集合!
     *
     * @param productIdsParam 商品id
     * @return 商品集合数据
     */
    List<Product> ids(ProductIdsParam productIdsParam);
}
