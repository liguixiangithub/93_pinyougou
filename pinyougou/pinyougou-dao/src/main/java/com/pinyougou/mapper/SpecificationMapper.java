package com.pinyougou.mapper;

import com.pinyougou.pojo.TbSpecification;

import tk.mybatis.mapper.common.Mapper;

import java.util.List;
import java.util.Map;

public interface SpecificationMapper extends Mapper<TbSpecification> {


    /**
     * 查询规格列表
     * @return 返回规格列表，数据结构（json格式）: ["id":1,"text":"内存"]
     */
    List<Map> selectOptionList();
}
