package com.pinyougou.search.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.pinyougou.search.service.ItemSearchService;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RequestMapping("/itemSearch")
@RestController
public class ItemSearchController {

    @Reference
    private ItemSearchService itemSearchService;

    /**
     * 根据搜索关键字搜索商品列表
     * @param searchMap 搜索条件
     * @return 商品列表
     */
    @PostMapping("/search")
    public Map<String,Object> search(@RequestBody Map<String,Object> searchMap){

        return itemSearchService.search(searchMap);
    }
}
