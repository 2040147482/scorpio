package com.leslie.product.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.leslie.pojo.Score;
import com.leslie.utils.Result;

/**
 * @author 20110
 * @description 针对表【tb_score(评分表)】的数据库操作Service
 * @createDate 2022-12-15 16:28:34
 */
public interface ScoreService extends IService<Score> {

    /**
     * 根据用户id和商品id查询
     *
     * @param userId    用户id
     * @param productId 商品id
     * @return
     */
    Result queryByUserIdAndProductId(Long userId, Long productId);

    /**
     * 分页查询
     *
     * @param curPage 当前页码
     * @param size 页容量
     * @return
     */
    Result queryPage(Integer curPage, Integer size);

    /**
     * 添加评分
     */
    Result add(Score score);

    /**
     * 更新评分
     */
    Result update(Score score);

    /**
     * 删除评分
     */
    Result delete(Long scoreId);
}
