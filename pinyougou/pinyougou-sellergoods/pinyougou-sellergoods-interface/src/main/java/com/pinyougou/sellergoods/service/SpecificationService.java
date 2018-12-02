package com.pinyougou.sellergoods.service;

import com.pinyougou.pojo.TbSpecification;
import com.pinyougou.service.BaseService;
import com.pinyougou.vo.PageResult;
import com.pinyougou.vo.Specification;

public interface SpecificationService extends BaseService<TbSpecification> {

    PageResult search(Integer page, Integer rows, TbSpecification specification);


    /**
     * 添加规格及规格选项
     * @param specification 规格对象
     * @return
     */
    void add(Specification specification);

    /**
     * 根据规格id查询规格及规格选项
     * @param id 规格id
     * @return 规格及规格选项
     */
    Specification findOne(Long id);

    /**
     * 更新规格及规格选项
     * @param specification 规格及规格选项
     * @return 规格及规格选项
     */
    void update(Specification specification);


    /**
     * 根据规格id批量删除规格及规格选项
     * @param ids 规格id集合
     * @return
     */
    void deleteSpecificationByIds(Long[] ids);
}