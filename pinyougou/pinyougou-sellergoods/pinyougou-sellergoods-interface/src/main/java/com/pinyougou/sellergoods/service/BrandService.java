package com.pinyougou.sellergoods.service;

import com.pinyougou.pojo.TbBrand;
import com.pinyougou.service.BaseService;
import com.pinyougou.vo.PageResult;
import com.pinyougou.vo.Result;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

public interface BrandService extends BaseService<TbBrand> {
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

    PageResult search(Integer page, Integer rows, TbBrand brand);

    /**
     * 查询品牌列表
     * @return 返回json格式: ["id":1,"text":"联想"]
     */
    List<Map> selectOptionList();
}
