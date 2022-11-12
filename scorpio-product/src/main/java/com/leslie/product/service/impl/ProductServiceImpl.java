package com.leslie.product.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.leslie.product.pojo.Category;
import com.leslie.pojo.Product;
import com.leslie.product.service.CategoryService;
import com.leslie.product.service.ProductService;
import com.leslie.product.mapper.ProductMapper;
import com.leslie.utils.Result;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author 20110
 * @description 针对表【tb_product(商品表)】的数据库操作Service实现
 * @createDate 2022-11-11 19:51:56
 */
@Service
public class ProductServiceImpl extends ServiceImpl<ProductMapper, Product>
        implements ProductService {

    @Resource
    private ProductMapper productMapper;

    @Resource
    private CategoryService categoryService;

    @Override
    public Result promo(String categoryName) {
        Category category = categoryService.byName(categoryName);
        Long cid = category.getCid();

        //封装查询参数
        QueryWrapper<Product> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("category_id", cid);
        queryWrapper.orderByDesc("product_sales");

        Page<Product> page = new Page<>(1, 5);
        page = productMapper.selectPage(page, queryWrapper);
        long total = page.getTotal();
        List<Product> productList = page.getRecords();

        return Result.ok(productList, total);
    }


    @Override
    public Result detail(Long productId) {
        Product product = productMapper.selectById(productId);
        return Result.ok(product);
    }
}
