package com.leslie.recommender.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.leslie.recommender.pojo.StreamProductRecs;
import com.leslie.utils.Result;

public interface StreamProductRecsService extends IService<StreamProductRecs> {
    Result getStreamProductRecs(Integer userId);
}