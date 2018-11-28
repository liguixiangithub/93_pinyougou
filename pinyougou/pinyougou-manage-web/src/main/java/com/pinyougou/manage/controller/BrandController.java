package com.pinyougou.manage.controller;

        import com.alibaba.dubbo.config.annotation.Reference;
        import com.pinyougou.sellergoods.service.BrandService;
        import com.pinyougou.pojo.TbBrand;
        import com.pinyougou.vo.PageResult;
        import com.pinyougou.vo.Result;
        import org.springframework.web.bind.annotation.*;

        import java.io.Serializable;
        import java.util.Collections;
        import java.util.List;

@RequestMapping("/brand")
@RestController
public class BrandController{

    @Reference
    private BrandService brandService;

    @PostMapping("/search")
    public PageResult search(@RequestParam(defaultValue = "1")Integer page,
                             @RequestParam(defaultValue = "10")Integer rows,@RequestBody TbBrand brand){
        return brandService.search(page,rows,brand);
    }


    @GetMapping("/delete")
    public Result delete( Long[] ids){
        try {
            brandService.deleteByIds(ids);
            return Result.ok("批量删除品牌成功");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return Result.fail("批量删除品牌失败");
    }


    @GetMapping("/findOne")
    public TbBrand findOne(Long id) {
        return brandService.findOne(id);
    }

    /**
     * 根据主键更新品牌数据
     * @param brand
     * @return
     */
    @PostMapping("/update")
    public Result update(@RequestBody TbBrand brand){
        try {
            brandService.update(brand);
            return Result.ok("修改品牌成功");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return Result.fail("修改品牌失败");
    }

    /**
     * 新建
     * @param brand
     * @return
     */
    @PostMapping("/add")
    public Result add(@RequestBody TbBrand brand){
        try {
            brandService.add(brand);
            return Result.ok("新增品牌成功");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return Result.fail("新增品牌失败");
    }

/*

    @PostMapping("/search")
    public PageResult search(Integer page, Integer rows){

        return new PageResult(page, Collections.singletonList(rows));
    }
*/

    /**
     * 根据页号和页大小查询品牌列表
     * @param page 页号
     * @param rows 页大小
     * @return 分页结果
     */
    @GetMapping("/findPage")
    public PageResult findPage(@RequestParam(defaultValue = "1") Integer page, @RequestParam(defaultValue = "10")Integer rows){
        return brandService.findPage(page,rows);
    }


    /**
     * 根据页号和页大小查询品牌列表
     * @param page 页号
     * @param rows 页大小
     * @return 品牌列表
     */
    @GetMapping("/testPage")
    public List<TbBrand> testPage(@RequestParam(defaultValue = "1") Integer page, @RequestParam(defaultValue = "10")Integer rows){
        //return brandService.testPage(page,rows);
        return (List<TbBrand>) brandService.findPage(page,rows).getRows();
    }


    /**
     * 查询全部品牌
     * @return
     */
    @GetMapping("/findAll")
    public List<TbBrand> findAll(){
       /* List<TbBrand> tbBrands = brandService.queryAll();
        return tbBrands;*/

//       return brandService.queryAll();
        return brandService.findAll();
    }


}
