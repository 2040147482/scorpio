package com.leslie.product.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.leslie.product.pojo.Category;
import com.leslie.product.service.CategoryService;
import com.leslie.product.mapper.CategoryMapper;
import com.leslie.utils.Result;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author 20110
 * @description 针对表【tb_category(商品分类表)】的数据库操作Service实现
 * @createDate 2022-11-11 17:32:17
 */
@Slf4j
@Service
public class CategoryServiceImpl extends ServiceImpl<CategoryMapper, Category>
        implements CategoryService {

    @Resource
    private CategoryMapper categoryMapper;

    @Override
    public Result all() {
        List<Category> list = categoryMapper.selectList(null);
        return Result.ok(list);
    }

    @Override
    public Category byName(String categoryName) {
        QueryWrapper<Category> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("category_name", categoryName);
        Category category = categoryMapper.selectOne(queryWrapper);
        return category;
    }
}
