package com.pinyougou.mapper;

import com.pinyougou.pojo.TbBrand;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;
import java.util.Map;

public interface BrandMapper extends Mapper<TbBrand> {

    List<TbBrand> queryAll();

    /**
     * 查询品牌列表
     * @return 返回json格式: ["id":1,"text":"联想"]
     */
    List<Map> selectOptionList();
}
