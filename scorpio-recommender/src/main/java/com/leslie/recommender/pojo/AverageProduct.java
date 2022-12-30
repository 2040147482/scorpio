package com.leslie.recommender.pojo;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

@TableName(value = "AverageProducts")
@Data
public class AverageProduct {
    private Integer productId;
    private Double avg;
    private String createTime;
}
