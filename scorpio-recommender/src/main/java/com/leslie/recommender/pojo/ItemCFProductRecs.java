package com.leslie.recommender.pojo;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

/**
 * 基于物体的协同过滤表
 */
@TableName(value = "ItemCFProductRecs")
@Data
public class ItemCFProductRecs {

    Integer productId;
    String recs;
    String createTime;
}
