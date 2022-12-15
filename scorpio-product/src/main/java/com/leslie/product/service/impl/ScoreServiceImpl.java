package com.leslie.product.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.leslie.pojo.Score;
import com.leslie.product.service.ScoreService;
import com.leslie.product.mapper.ScoreMapper;
import com.leslie.utils.Result;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author 20110
 * @description 针对表【tb_score(评分表)】的数据库操作Service实现
 * @createDate 2022-12-15 16:28:34
 */
@Service
public class ScoreServiceImpl extends ServiceImpl<ScoreMapper, Score>
        implements ScoreService {

    @Resource
    private ScoreMapper scoreMapper;

    @Override
    public Result queryByUserIdAndProductId(Long userId, Long productId) {
        QueryWrapper<Score> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("user_id", userId);
        queryWrapper.eq("product_id", productId);
        Score score = scoreMapper.selectOne(queryWrapper);
        return Result.ok(score);
    }

    @Override
    public Result queryPage(Integer curPage, Integer size) {
        Page<Score> page = new Page<>(curPage, size);
        page = scoreMapper.selectPage(page, null);

        List<Score> scoreList = page.getRecords();
        long total = page.getTotal();
        return Result.ok(scoreList, total);
    }


}




