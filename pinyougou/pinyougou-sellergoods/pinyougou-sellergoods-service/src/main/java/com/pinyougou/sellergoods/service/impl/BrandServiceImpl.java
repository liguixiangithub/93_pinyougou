package com.pinyougou.sellergoods.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.pinyougou.mapper.BrandMapper;
import com.pinyougou.pojo.TbBrand;
import com.pinyougou.sellergoods.service.BrandService;
import com.pinyougou.service.impl.BaseServiceImpl;
import com.pinyougou.vo.PageResult;
import com.pinyougou.vo.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import tk.mybatis.mapper.entity.Example;

import java.io.Serializable;
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
        PageHelper.startPage(page, rows);
        return brandMapper.selectAll();
    }

    /**
     * 根据名称和首字母模糊查询
     * @param page 页号
     * @param rows 页大小
     * @param brand
     * @return 分页对象
     */
    @Override
    public PageResult search(Integer page, Integer rows, TbBrand brand) {

        //设置分页
        PageHelper.startPage(page,rows);

        //创建查询
        Example example = new Example(TbBrand.class);

        //创建查询条件对象，相当于where子句
        Example.Criteria criteria = example.createCriteria();

        //首字母查询
        if (!StringUtils.isEmpty(brand.getFirstChar())){
            criteria.andEqualTo("firstChar",brand.getFirstChar());
        }

        //名称模糊查询
        if (!StringUtils.isEmpty(brand.getName())) {
            criteria.andLike("name","%" + brand.getName() + "%");
        }
        //返回分页对象
        List<TbBrand> list = brandMapper.selectByExample(example);

        //创建分页信息对象
        PageInfo<TbBrand> pageInfo = new PageInfo<>(list);

        return new PageResult(pageInfo.getTotal(),pageInfo.getList());
    }


}

