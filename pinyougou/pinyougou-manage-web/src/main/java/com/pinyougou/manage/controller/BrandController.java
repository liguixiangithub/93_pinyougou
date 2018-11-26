package com.pinyougou.manage.controller;

        import com.alibaba.dubbo.config.annotation.Reference;
        import com.pinyougou.sellergoods.service.BrandService;
        import com.pinyougou.pojo.TbBrand;
        import com.pinyougou.vo.PageResult;
        import org.springframework.web.bind.annotation.*;

        import java.util.Collections;
        import java.util.List;

@RequestMapping("/brand")
@RestController
public class BrandController {

    @Reference
    private BrandService brandService;

    @PostMapping("/search")
    public PageResult search(Integer page, Integer rows){

        return new PageResult(page, Collections.singletonList(rows));
    }

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
