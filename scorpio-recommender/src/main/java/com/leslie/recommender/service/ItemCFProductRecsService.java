package com.leslie.recommender.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.leslie.recommender.pojo.ItemCFProductRecs;
import com.leslie.utils.Result;

public interface ItemCFProductRecsService extends IService<ItemCFProductRecs> {
    Result getItemCFProductRecs(Integer pId);
}
