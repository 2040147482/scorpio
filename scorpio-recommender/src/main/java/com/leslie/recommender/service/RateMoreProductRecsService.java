package com.leslie.recommender.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.leslie.recommender.pojo.RateMoreProductRecs;
import com.leslie.utils.Result;

public interface RateMoreProductRecsService  extends IService<RateMoreProductRecs> {
    Result getRateMoreProductRecs();

}
