package com.leslie.search.vo;

import lombok.Data;


/**
 * @author 20110
 */
@Data
public class SearchParams {

    private String search;
    private Integer currentPage;
    private Integer pageSize;

}
