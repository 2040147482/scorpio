package com.leslie.recommender.controller;

import com.leslie.recommender.service.ItemCFProductRecsService;
import com.leslie.recommender.service.RateMoreProductRecsService;
import com.leslie.recommender.service.UserRecsService;
import com.leslie.utils.Result;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

@RestController
@RequestMapping("recommender")
public class ProductRecsController {
    @Resource
    private ItemCFProductRecsService itemCFProductRecsService;

    /**
     * 基于物品的协同过滤
     */
    @Resource
    private UserRecsService userRecsService;

    @GetMapping("/ItemCFRec/{pid}")
    public Result getItemCFProducts(@RequestBody @PathVariable("pid") Integer pid){
        return itemCFProductRecsService.getItemCFProductRecs(pid);
    }

    /**
     * 离线推荐
     */
    @PostMapping("/OfflineRec")
    public Result getOfflineProducts(@RequestBody @RequestParam("userId") int userId){
        return userRecsService.getUserProductRecs(userId);

    }
    /**
     * 实时推荐
     */
//    @PostMapping("StreamRec")
//    public Result getStreamProducts(@RequestBody @RequestParam( "username") String username){
//        User user = userRecsService.
//        return userRecsService.getUserProductRecs();
//    }


}
