package com.leslie.product.service;

import com.leslie.pojo.Product;
import com.baomidou.mybatisplus.extension.service.IService;
import com.leslie.product.vo.UploadProductImgVo;
import com.leslie.to.OrderToProduct;
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
     * 供收藏服务、购物车服务调用，根据传入的商品id集合，查询商品集合
     *
     * @param productIdsParam 商品id
     * @return 商品集合数据
     */
    List<Product> ids(ProductIdsParam productIdsParam);

    /**
     * 分页查询商品信息
     *
     * @param page 当前页码
     * @param size 条数
     * @return
     */
    Result queryPage(Integer page, Integer size);

    /**
     * 1.保存商品数据
     * 2.发送消息通知es更新数据
     *
     * @param product 商品数据
     * @return Result
     */
    Result saveProduct(Product product);

    /**
     * 1.更新商品数据
     * 2.发送消息通知es更新数据
     *
     * @param product 商品数据
     * @return Result
     */
    Result updateProduct(Product product);

    /**
     * 1.根据id删除商品数据
     * 2.发送消息通知es更新数据
     *
     * @param id 商品id
     * @return Result
     */
    Result removeProduct(Long id);

    /**
     * 更新商品库存和销量
     * @param orderToProducts 商品id，商品销量值
     */
    void pubAddOrder(List<OrderToProduct> orderToProducts);

    void pubCancelOrder(OrderToProduct orderToProduct);

    /**
     * 上传商品图片
     *
     * @param uploadImgVo 商品id、商品图片
     * @return
     */
    Result uploadImg(UploadProductImgVo uploadImgVo);
}
