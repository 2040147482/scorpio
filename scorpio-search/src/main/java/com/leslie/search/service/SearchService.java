package com.leslie.search.service;

import com.leslie.search.vo.SearchParams;
import com.leslie.utils.Result;

/**
 * @author 20110
 */
public interface SearchService {


    /**
     * 根据输入关键子搜索
     *
     * @param searchParams 输入关键词、起始分页值、分页大小
     * @return Result
     */
    Result search(SearchParams searchParams);

}
