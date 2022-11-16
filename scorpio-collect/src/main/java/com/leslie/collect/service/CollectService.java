package com.leslie.collect.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.leslie.collect.vo.CollectParams;
import com.leslie.pojo.Collect;
import com.leslie.utils.Result;

/**
 * @author 20110
 * @description 针对表【tb_collect(收藏表)】的数据库操作Service
 * @createDate 2022-11-12 21:17:07
 */
public interface CollectService extends IService<Collect> {

    /**
     * 收藏保存
     *
     * @param collectParams 用户id、商品
     * @return 666
     */
    Result saveProduct(CollectParams collectParams);

    /**
     * 查看收藏，
     *      1.根据用户id查询商品id集合
     *      2.调用商品服务，根据商品id集合查询商品数据集
     *
     * @param userId 用户id
     * @return 666
     */
    Result show(Long userId);

    /**
     * 根据用户和商品ID，删除收藏数据
     * @param collectParams 用户id、商品
     * @return 666
     */
    Result removeCollect(CollectParams collectParams);
}
