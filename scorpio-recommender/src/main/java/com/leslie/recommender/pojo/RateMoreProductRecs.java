package com.leslie.recommender.pojo;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;


/**
 * 基于评分最多的商品
 */
@TableName(value = "RateMoreProducts")
@Data
public class RateMoreProductRecs {
    Integer productId;
    Integer  count;
    String createTime;
}
