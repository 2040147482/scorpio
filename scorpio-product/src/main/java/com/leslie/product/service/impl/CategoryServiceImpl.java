package com.leslie.product.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.leslie.pojo.Category;
import com.leslie.product.service.CategoryService;
import com.leslie.product.mapper.CategoryMapper;
import com.leslie.utils.Result;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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

    @Override
    public Result selectPage(Integer curPage, Integer size) {
        Page<Category> categoryPage = new Page<>(curPage, size);
        categoryPage = categoryMapper.selectPage(categoryPage, null);

        List<Category> categoryList = categoryPage.getRecords();
        long total = categoryPage.getTotal();
        return Result.ok(categoryList, total);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public Result add(Category category) {
        int row = categoryMapper.insert(category);
        if (row == 0) {
            return Result.fail("添加类别失败!");
        }
        return Result.ok();
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public Result update(Category category) {
        int row = categoryMapper.updateById(category);
        if (row == 0) {
            return Result.fail("添加类别失败!");
        }
        return Result.ok();
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public Result delete(Long categoryId) {
        int row = categoryMapper.deleteById(categoryId);
        if (row == 0) {
            return Result.fail("添加类别失败!");
        }
        return Result.ok();
    }
}
