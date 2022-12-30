package com.leslie.recommender.pojo;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

@TableName(value = "userRecs")
@Data
public class UserRecs {
    Integer userId;
    String recs;
    String createTime;
}
