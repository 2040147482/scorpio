package com.leslie.recommender.pojo;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

@TableName(value = "streamRecs")
@Data
public class StreamProductRecs {
    Integer userId;
    String recs;
    String createTime;
}
