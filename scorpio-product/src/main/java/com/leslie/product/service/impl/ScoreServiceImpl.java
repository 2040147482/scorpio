package com.leslie.product.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.leslie.pojo.Score;
import com.leslie.product.service.ScoreService;
import com.leslie.product.mapper.ScoreMapper;
import com.leslie.utils.Result;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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

    @Cacheable(cacheNames = "score", key = "'score:'+#root.methodName+#p0+#p1")
    @Override
    public Result queryByUserIdAndProductId(Long userId, Long productId) {
        QueryWrapper<Score> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("user_id", userId);
        queryWrapper.eq("product_id", productId);
        Score score = scoreMapper.selectOne(queryWrapper);
        return Result.ok(score);
    }

    @Cacheable(cacheNames = "score", key = "'score:'+#root.methodName+#p0+#p1")
    @Override
    public Result queryPage(Integer curPage, Integer size) {
        Page<Score> page = new Page<>(curPage, size);
        page = scoreMapper.selectPage(page, null);

        List<Score> scoreList = page.getRecords();
        long total = page.getTotal();
        return Result.ok(scoreList, total);
    }

    @Override
    public Result add(Score score){
        QueryWrapper<Score> AddWrapper = new QueryWrapper<>();
        AddWrapper.eq("user_id", score.getUserId());
        AddWrapper.eq("product_id", score.getProductId());
        Score score1 = scoreMapper.selectOne(AddWrapper);
        if (score1 != null){
            return Result.fail("该用户已评价过该商品，不可重复评价！");
        }
        int row = scoreMapper.insert(score);
        if(row == 0){
            return Result.fail("添加评分失败");
        }
         return Result.ok();
    }

    @Override
    public Result update(Score score){

        UpdateWrapper<Score> updateWrapper = new UpdateWrapper<>();
        updateWrapper.eq("user_id", score.getUserId());
        updateWrapper.eq("product_id", score.getProductId());
        updateWrapper.set("score", score.getScore())
                .set("comment", score.getComment());
        int row = scoreMapper.update(null, updateWrapper);
        if(row == 0){
            return Result.fail("更新评分失败");
        }
        return Result.ok();
    }

    @Override
    public Result delete(Long scoreId){
        int row = scoreMapper.deleteById(scoreId);
        if(row == 0){
            return Result.fail("删除评分失败");
        }
        return Result.ok();
    }



}




