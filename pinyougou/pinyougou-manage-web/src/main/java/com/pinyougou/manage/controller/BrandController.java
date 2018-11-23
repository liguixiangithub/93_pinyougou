package com.pinyougou.manage.controller;

        import com.alibaba.dubbo.config.annotation.Reference;
        import com.pinyougou.sellergoods.service.BrandService;
        import com.pinyougou.pojo.TbBrand;
        import org.springframework.web.bind.annotation.GetMapping;
        import org.springframework.web.bind.annotation.RequestMapping;
        import org.springframework.web.bind.annotation.RestController;

        import java.util.List;

@RequestMapping("/brand")
@RestController
public class BrandController {

    @Reference
    private BrandService brandService;

    /**
     * 查询全部品牌
     * @return
     */
    @GetMapping("/findAll")
    public List<TbBrand> findAll(){
       /* List<TbBrand> tbBrands = brandService.queryAll();
        return tbBrands;*/

       return brandService.queryAll();
    }

}
