package com.pinyougou.sellergoods.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.github.pagehelper.PageHelper;
import com.pinyougou.mapper.BrandMapper;
import com.pinyougou.pojo.TbBrand;
import com.pinyougou.sellergoods.service.BrandService;
import com.pinyougou.service.impl.BaseServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

//暴露服务，也就是将该服务注册到注册中心;并在ioc中存在该对象
@Service(interfaceClass = BrandService.class)
public class BrandServiceImpl extends BaseServiceImpl<TbBrand> implements BrandService {

    @Autowired
    private BrandMapper brandMapper;

    /**
     * 查询全部品牌
     * @return
     */
    @Override
    public List<TbBrand> queryAll() {
        return brandMapper.queryAll();
    }

    /**
     * 根据页号和页大小分页
     *
     * @param page 页号
     * @param rows 页大小
     * @return 列表数据
     */
    @Override
    public List<TbBrand> testPage(Integer page, Integer rows) {

        //设置分页
        PageHelper.startPage(page,rows);
        return brandMapper.selectAll();
    }


}

