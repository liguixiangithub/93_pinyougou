package com.pinyougou.shop.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.pinyougou.pojo.TbTypeTemplate;
import com.pinyougou.sellergoods.service.TypeTemplateService;
import com.pinyougou.vo.PageResult;
import com.pinyougou.vo.Result;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RequestMapping("/typeTemplate")
@RestController
public class TypeTemplateController {

    @Reference
    private TypeTemplateService typeTemplateService;

    @RequestMapping("/findAll")
    public List<TbTypeTemplate> findAll() {
        return typeTemplateService.findAll();
    }

    @GetMapping("/findPage")
    public PageResult findPage(@RequestParam(value = "page", defaultValue = "1")Integer page,
                               @RequestParam(value = "rows", defaultValue = "10")Integer rows) {
        return typeTemplateService.findPage(page, rows);
    }

    @PostMapping("/add")
    public Result add(@RequestBody TbTypeTemplate typeTemplate) {
        try {
            typeTemplateService.add(typeTemplate);
            return Result.ok("类型模板增加成功");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return Result.fail("类型模板增加失败");
    }

    @GetMapping("/findOne")
    public TbTypeTemplate findOne(Long id) {
        return typeTemplateService.findOne(id);
    }

    @PostMapping("/update")
    public Result update(@RequestBody TbTypeTemplate typeTemplate) {
        try {
            typeTemplateService.update(typeTemplate);
            return Result.ok("类型模板修改成功");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return Result.fail("类型模板修改失败");
    }

    @GetMapping("/delete")
    public Result delete(Long[] ids) {
        try {
            typeTemplateService.deleteByIds(ids);
            return Result.ok("类型模板删除成功");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return Result.fail("类型模板删除失败");
    }

    /**
     * 分页查询列表
     * @param typeTemplate 查询条件
     * @param page 页号
     * @param rows 每页大小
     * @return
     */
    @PostMapping("/search")
    public PageResult search(@RequestBody  TbTypeTemplate typeTemplate, @RequestParam(value = "page", defaultValue = "1")Integer page,
                               @RequestParam(value = "rows", defaultValue = "10")Integer rows) {
        return typeTemplateService.search(page, rows, typeTemplate);
    }

    /**
     * 获取分类模版id并查询所期望的数据，结构如：
     * [      {"id":27,"text":"网络","options":[    {"id":123,"optionName":"2G","orders":"1"},
     * {"id":123,"optionName":"3G","orders":"3"}   ]},  
     *   {"id":32,"text":"机身内存","options":[    {"id":123,"optionName":"32G","orders":"1"},
     *  {"id":123,"optionName":"64G","orders":"2"}    ]}    ]
     * @param id 分类模板id
     * @return 规格及其规格选项
     */
    @GetMapping("/findSpecList")
    public List<Map> findSpecList(Long id){
        return typeTemplateService.findSpecList(id);
    }

}
