package com.pinyougou.sellergoods.service;

import com.pinyougou.pojo.TbGoods;
import com.pinyougou.service.BaseService;
import com.pinyougou.vo.Goods;
import com.pinyougou.vo.PageResult;

public interface GoodsService extends BaseService<TbGoods> {

    PageResult search(Integer page, Integer rows, TbGoods goods);

    /**
     * 保存商品基本、描述、sku信息
     * @param goods 商品基本、描述、sku信息
     * @return
     */
    void addGoods(Goods goods);

    /**
     * 修改商品 回显
     * @param id
     * @return 商品列表
     */
    Goods findGoodsById(Long id);

    /**
     * 修改商品 回显并保存
     * @param goods 商品基本信息
     * @return 商品列表
     */
    void updateGoods(Goods goods);


    /**
     * 更新商品状态
     * @param ids
     * @param status
     * @return
     */
    void updateStatus(Long[] ids, String status);

    /**
     * 批量删除商品
     * @param ids
     * @return
     */
    void deleteGoodsByIds(Long[] ids);
}