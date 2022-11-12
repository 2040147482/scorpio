package com.leslie.search.controller;

import com.leslie.search.service.SearchService;
import com.leslie.search.vo.SearchParams;
import com.leslie.utils.Result;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * @author 20110
 */
@RestController
@RequestMapping("search")
public class SearchController {

    @Resource
    private SearchService searchService;

    @PostMapping("/list")
    public Result list(@RequestBody SearchParams searchParams) {
        return searchService.search(searchParams);
    }
}
