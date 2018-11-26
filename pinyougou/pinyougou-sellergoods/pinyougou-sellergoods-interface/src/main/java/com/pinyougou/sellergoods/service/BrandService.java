package com.pinyougou.sellergoods.service;

import com.pinyougou.pojo.TbBrand;
import com.pinyougou.service.baseService;

import java.util.List;

public interface BrandService extends baseService<TbBrand> {
    /**
     * 查询全部品牌
     * @return
     */
    List<TbBrand> queryAll();

    /**
     * 根据页号和页大小分页
     * @param page 页号
     * @param rows 页大小
     * @return 列表数据
     */

    List<TbBrand> testPage(Integer page, Integer rows);
}
