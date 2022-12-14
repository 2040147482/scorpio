package com.leslie.vo;

import com.leslie.pojo.Order;
import lombok.Data;

/**
 * @author 20110
 * @descript
 */
@Data
public class OrderVo extends Order {


    private String productName;
    private String productImage;
    private String address;
}
