package com.leslie.cart.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.leslie.clients.ProductClient;
import com.leslie.pojo.Cart;
import com.leslie.cart.service.CartService;
import com.leslie.cart.mapper.CartMapper;
import com.leslie.pojo.Product;
import com.leslie.utils.Result;
import com.leslie.vo.CartSaveParam;
import com.leslie.vo.CartVo;
import com.leslie.vo.ProductIdsParam;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @author 20110
 * @description 针对表【tb_cart(购物车表)】的数据库操作Service实现
 * @createDate 2022-12-12 14:20:34
 */
@Service
public class CartServiceImpl extends ServiceImpl<CartMapper, Cart>
        implements CartService {

    @Resource
    private CartMapper cartMapper;

    @Resource
    private ProductClient productClient;

    @Override
    public Result show(Long userId) {
        //1.查询用户对应的购物车数据
        QueryWrapper<Cart> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("user_id", userId);
        List<Cart> cartList = cartMapper.selectList(queryWrapper);

        //2.判断是否存在，不存在，返回空集合
        if (cartList == null) {
            return Result.ok("您的购物车似乎被洗劫一空！", new ArrayList<>());
        }

        //3.存在，查询购物车对应的商品数据
        List<Long> productIds = new ArrayList<>();
        for (Cart cart : cartList) {
            productIds.add(cart.getProductId());
        }
        ProductIdsParam productIdsParam = new ProductIdsParam();
        productIdsParam.setProductIds(productIds);
        List<Product> productList = productClient.ids(productIdsParam);

        //4.进行数据封装
        //集合转map
        Map<Long, Product> productMap = productList.stream().collect(Collectors.toMap(Product::getProductId, product -> product));

        List<CartVo> cartVoList = new ArrayList<>();
        for (Cart cart : cartList) {
            CartVo cartVo = new CartVo(productMap.get(cart.getProductId()), cart);
            cartVoList.add(cartVo);
        }
        int total = cartVoList.size();
        return Result.ok(cartVoList, total);
    }

    @Transactional
    @Override
    public Result add(CartSaveParam cartSaveParam) {
        //1.查询商品数据
        Long productId = cartSaveParam.getProductId();
        Product product = productClient.cartProductDetail(productId);

        if (product == null) {
            return Result.fail("商品已删除，无法添加到购物车！");
        }

        //2.检查商品的库存
        if (product.getProductStock() == 0) {
            return Result.fail("该商品没有库存，无法购买！");
        }

        //3.检查是否添加过
        QueryWrapper<Cart> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("user_id", cartSaveParam.getUserId());
        queryWrapper.eq("product_id", cartSaveParam.getProductId());
        Cart cart = cartMapper.selectOne(queryWrapper);

        if (cart != null) {
            //不为空，证明购物车存在，原有的数量+1
            cart.setNum(cart.getNum() + 1);
            int update = cartMapper.updateById(cart);
            if (update == 0) {
                return Result.fail("系统异常，添加失败！");
            }
            return Result.ok("购车车存在该商品，数量+1", null);
        }

        //4.添加到购物车
        cart = new Cart();
        cart.setNum(1);
        cart.setUserId(cartSaveParam.getUserId());
        cart.setProductId(cartSaveParam.getProductId());
        int row = cartMapper.insert(cart);
        if (row == 0) {
            return Result.fail("添加购物车失败!");
        }

        //5.结果封装返回
        CartVo cartVo = new CartVo(product, cart);
        return Result.ok("成功添加到购物车", cartVo);
    }

    @Transactional
    @Override
    public Result updateStock(Cart cart) {
        //1. 检查修改后的数量是否超出库存
        Product product = productClient.cartProductDetail(cart.getProductId());
        if (cart.getNum() > product.getProductStock()) {
            return Result.fail("修改失败,超出库存数量!");
        }

        //2. 没有，进行修改
        QueryWrapper<Cart> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("user_id", cart.getUserId());
        queryWrapper.eq("product_id", cart.getProductId());
        Cart c = cartMapper.selectOne(queryWrapper);
        c.setNum(cart.getNum());
        int row = cartMapper.updateById(c);
        if (row == 0) {
            return Result.fail("数据更新失败!");
        }

        return Result.ok();
    }

    @Transactional
    @Override
    public Result delete(Long userId, Long productId) {
        QueryWrapper<Cart> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("user_id", userId);
        queryWrapper.eq("product_id", productId);
        int row = cartMapper.delete(queryWrapper);
        if (row == 0) {
            return Result.fail("数据更新失败!");
        }
        return Result.ok();
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void clearIds(List<Long> cartIds) {
        List<Cart> carts = cartMapper.selectBatchIds(cartIds);
        if (carts == null) {
            log.warn("购物车早已清空该购物车项！");
            return;
        }

        int row = cartMapper.deleteBatchIds(cartIds);
        if (row == 0) {
            log.warn("系统异常！清空对应id购物车项失败！");
        }
    }


}




