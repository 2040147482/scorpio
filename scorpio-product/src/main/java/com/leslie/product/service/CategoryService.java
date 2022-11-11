package com.leslie.product.service;

import com.leslie.product.pojo.Category;
import com.baomidou.mybatisplus.extension.service.IService;
import com.leslie.utils.Result;

/**
* @author 20110
* @description 针对表【tb_category(商品分类表)】的数据库操作Service
* @createDate 2022-11-11 17:32:17
*/
public interface CategoryService extends IService<Category> {

    /**
     * 查询类别数据
     *
     * @return Result 类别数据集合
     */
    Result all();

    /**
     * 根据类别名称查询类别数据
     * @param categoryName 类别名称
     * @return Category类型数据
     */
    Category byName(String categoryName);
}
