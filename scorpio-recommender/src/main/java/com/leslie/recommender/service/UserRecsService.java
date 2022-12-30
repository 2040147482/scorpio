package com.leslie.recommender.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.leslie.recommender.pojo.UserRecs;
import com.leslie.utils.Result;

public interface UserRecsService  extends IService<UserRecs> {
    Result getUserProductRecs(Integer userId);

}
