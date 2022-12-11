package com.leslie.product.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.leslie.constants.MqConstants;
import com.leslie.product.pojo.Category;
import com.leslie.pojo.Product;
import com.leslie.product.service.CategoryService;
import com.leslie.product.service.ProductService;
import com.leslie.product.mapper.ProductMapper;
import com.leslie.vo.ProductIdsParam;
import com.leslie.utils.Result;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author 20110
 * @description 针对表【tb_product(商品表)】的数据库操作Service实现
 * @createDate 2022-11-11 19:51:56
 */
@Slf4j
@Service
public class ProductServiceImpl extends ServiceImpl<ProductMapper, Product>
        implements ProductService {

    @Resource
    private ProductMapper productMapper;

    @Resource
    private CategoryService categoryService;

    @Resource
    private RabbitTemplate rabbitTemplate;

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

    @Override
    public List<Product> ids(ProductIdsParam productIdsParam) {
        List<Long> productIds = productIdsParam.getProductIds();
        QueryWrapper<Product> queryWrapper = new QueryWrapper<>();
        queryWrapper.in("product_id", productIds);
        List<Product> list = productMapper.selectList(queryWrapper);
        return list;
    }

    @Override
    public Result queryPage(Integer page, Integer size) {
        Page<Product> productPage = new Page<>(page, size);
        productPage = productMapper.selectPage(productPage,null);
        List<Product> productList = productPage.getRecords();
        long total = productPage.getTotal();
        return Result.ok(productList,total);
    }

    @Transactional
    @Override
    public Result saveProduct(Product product) {
        log.info("商品信息:{}", product);
        Long categoryId = product.getCategoryId();
        if (categoryId == null || categoryId < 0) {
            return Result.fail("分类id有误！");
        }
        //保存商品信息
        int row = productMapper.insert(product);

        if (row == 0) {
            return Result.fail("插入失败");
        }

        //通知es更新数据
        rabbitTemplate.convertAndSend(MqConstants.PRODUCT_EXCHANGE, MqConstants.PRODUCT_INSERT_KEY, product);
        return Result.ok();
    }

    @Transactional
    @Override
    public Result updateProduct(Product product) {
        if (product.getProductId() == null) {
            return Result.fail("id不能为空");
        }

        QueryWrapper<Product> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("product_id", product.getProductId());

        int row = productMapper.update(product, queryWrapper);
        if (row == 0) {
            return Result.fail("商品信息更新失败");
        }

        //通知es更新数据
        rabbitTemplate.convertAndSend(MqConstants.PRODUCT_EXCHANGE, MqConstants.PRODUCT_INSERT_KEY, product);
        return Result.ok();
    }

    @Transactional
    @Override
    public Result removeProduct(Long id) {
        QueryWrapper<Product> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("product_id", id);

        int row = productMapper.delete(queryWrapper);
        if (row == 0) {
            return Result.fail("删除商品信息失败！");
        }
        //通知es更新数据
        rabbitTemplate.convertAndSend(MqConstants.PRODUCT_EXCHANGE, MqConstants.PRODUCT_DELETE_KEY, id);
        return Result.ok();
    }
}
