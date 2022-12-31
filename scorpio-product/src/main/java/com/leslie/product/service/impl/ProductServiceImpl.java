package com.leslie.product.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.leslie.clients.FastdfsClient;
import com.leslie.constants.MqConstants;
import com.leslie.pojo.Category;
import com.leslie.pojo.Product;
import com.leslie.product.service.CategoryService;
import com.leslie.product.service.ProductService;
import com.leslie.product.mapper.ProductMapper;
import com.leslie.product.vo.UploadProductImgVo;
import com.leslie.to.OrderToProduct;
import com.leslie.vo.ProductIdsParam;
import com.leslie.utils.Result;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * @author 20110
 * @description 针对表【tb_product(商品表)】的数据库操作Service实现
 * @createDate 2022-11-11 19:51:56
 */
@CacheConfig(cacheNames = "product")
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

    @Resource
    private FastdfsClient fastdfsClient;

    @Cacheable(key = "'product:'+#p0")
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

    @Cacheable(key = "'product:'+#p0")
    @Override
    public Result detail(Long productId) {
        Product product = productMapper.selectById(productId);
        return Result.ok(product);
    }

    @Cacheable(key = "'product:'+#productIdsParam.productIds")
    @Override
    public List<Product> ids(ProductIdsParam productIdsParam) {
        List<Long> productIds = productIdsParam.getProductIds();
        QueryWrapper<Product> queryWrapper = new QueryWrapper<>();
        queryWrapper.in("product_id", productIds);
        List<Product> list = productMapper.selectList(queryWrapper);
        return list;
    }

    @Cacheable(key = "'product:'+#p0+':'+#p1")
    @Override
    public Result queryPage(Integer page, Integer size) {
        Page<Product> productPage = new Page<>(page, size);
        productPage = productMapper.selectPage(productPage, null);
        List<Product> productList = productPage.getRecords();
        long total = productPage.getTotal();
        return Result.ok(productList, total);
    }

    @CacheEvict(allEntries = true)
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

    @CacheEvict(allEntries = true)
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

    @CacheEvict(allEntries = true)
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

    @CacheEvict(allEntries = true)
    @Transactional(rollbackFor = Exception.class)
    @Override
    public void pubAddOrder(List<OrderToProduct> orderToProducts) {
        //将集合转为map key:productId, value: orderToProduct
        Map<Long, OrderToProduct> map = orderToProducts.stream().collect(
                Collectors.toMap(OrderToProduct::getProductId, orderToProduct -> orderToProduct)
        );

        //获取商品id集合
        Set<Long> productIds = map.keySet();

        //查询商品id集合对应的商品信息
        List<Product> productList = productMapper.selectBatchIds(productIds);

        //修改商品信息
        for (Product product : productList) {
            Integer num = map.get(product.getProductId()).getNum();
            //减库存
            product.setProductStock(product.getProductStock() - num);
            //加销量
            product.setProductSales(product.getProductSales() + num);
        }

        //批量更新商品数据
        boolean isUpdate = updateBatchById(productList);
        if (!isUpdate) {
            throw new RuntimeException("系统异常，商品信息更新失败！");
        }

        rabbitTemplate.convertAndSend(MqConstants.PRODUCT_EXCHANGE, MqConstants.PRODUCT_UPDATE_KEY, productList);
    }

    @CacheEvict(allEntries = true)
    @Transactional(rollbackFor = Exception.class)
    @Override
    public void pubCancelOrder(OrderToProduct orderToProduct) {
        Product product = productMapper.selectById(orderToProduct.getProductId());
        Integer num = orderToProduct.getNum();
        //加库存
        product.setProductStock(product.getProductStock() + num);
        //减销量
        product.setProductSales(product.getProductSales() - num);

        boolean update = updateById(product);
        if (!update) {
            throw new RuntimeException("系统异常，商品信息更新失败！");
        }

        rabbitTemplate.convertAndSend(MqConstants.PRODUCT_EXCHANGE, MqConstants.PRODUCT_INSERT_KEY, product);
    }

    @CacheEvict(allEntries = true)
    @Transactional(rollbackFor = Exception.class)
    @Override
    public Result uploadImg(UploadProductImgVo uploadImgVo) {
        Product product = productMapper.selectById(uploadImgVo.getId());
        if (product == null) {
            return Result.fail("不存在该商品id!");
        }
        String res = fastdfsClient.uploadFile(uploadImgVo.getFile());
        if ("不支持该类型文件".equals(res)) {
            return Result.fail("目前只支持ico、jpg、jpeg、png后缀的图片！");
        }
        product.setImageUrl(res);
        int row = productMapper.updateById(product);
        if ("文件上传失败".equals(res) || row == 0) {
            return Result.fail("图片上传失败!");
        }
        return Result.ok("图片上传成功", res);
    }
}
