package com.pinyougou.sellergoods.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.pinyougou.mapper.*;
import com.pinyougou.pojo.*;
import com.pinyougou.sellergoods.service.GoodsService;
import com.pinyougou.service.impl.BaseServiceImpl;
import com.pinyougou.vo.Goods;
import com.pinyougou.vo.PageResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import tk.mybatis.mapper.entity.Example;

import java.util.*;

@Transactional
@Service(interfaceClass = GoodsService.class)
public class GoodsServiceImpl extends BaseServiceImpl<TbGoods> implements GoodsService {

    @Autowired
    private GoodsMapper goodsMapper;

    @Autowired
    private GoodsDescMapper goodsDescMapper;

  @Autowired
    private ItemCatMapper itemCatMapper;

 @Autowired
    private BrandMapper brandMapper;

 @Autowired
    private SellerMapper sellerMapper;

   @Autowired
    private ItemMapper itemMapper;

    @Override
    public PageResult search(Integer page, Integer rows, TbGoods goods) {
        PageHelper.startPage(page, rows);

        Example example = new Example(TbGoods.class);
        Example.Criteria criteria = example.createCriteria();

        //不查询删除的商品
        criteria.andNotEqualTo("isDelete","1");

        if(!StringUtils.isEmpty(goods.getSellerId())){
            criteria.andEqualTo("sellerId", goods.getSellerId());
        }
        if (!StringUtils.isEmpty(goods.getAuditStatus())){
            criteria.andEqualTo("auditStatus", goods.getAuditStatus());
        }

        if(!StringUtils.isEmpty(goods.getGoodsName())){
            criteria.andLike("goodsName", "%" + goods.getGoodsName() + "%");
        }
        List<TbGoods> list = goodsMapper.selectByExample(example);
        PageInfo<TbGoods> pageInfo = new PageInfo<>(list);

        return new PageResult(pageInfo.getTotal(), pageInfo.getList());
    }

    @Override
    public void addGoods(Goods goods) {
        //1.保存基本信息
        add(goods.getGoods());

//        int i = 1/0;

        //2.保存描述信息
        goods.getGoodsDesc().setGoodsId(goods.getGoods().getId());
        goodsDescMapper.insertSelective(goods.getGoodsDesc());

        //3.保存sku
        saveItemList(goods);

    }

    @Override
    public Goods findGoodsById(Long id) {
        Goods goods = new Goods();
        //查询商品spu
        TbGoods tbGoods = goodsMapper.selectByPrimaryKey(id);
        goods.setGoods(tbGoods);

        //查询商品描述
        TbGoodsDesc tbGoodsDesc = goodsDescMapper.selectByPrimaryKey(id);
        goods.setGoodsDesc(tbGoodsDesc);

        //查询商品sku列表
        Example example = new Example(TbItem.class);
        example.createCriteria().andEqualTo("goodsId",id);

        List<TbItem> itemList = itemMapper.selectByExample(example);
        goods.setItemList(itemList);

        return goods;
    }

    @Override
    public void updateGoods(Goods goods) {

        //更新商品基本信息
        goods.getGoods().setAuditStatus("0");
        goodsMapper.updateByPrimaryKeySelective(goods.getGoods());

        //更新商品描述信息
        goodsDescMapper.updateByPrimaryKeySelective(goods.getGoodsDesc());

        //删除原有的sku列表
        TbItem param = new TbItem();
        param.setGoodsId(goods.getGoods().getId());
        itemMapper.delete(param);

        //保存商品sku列表
        saveItemList(goods);
    }

    @Override
    public void updateStatus(Long[] ids, String status) {
        TbGoods goods = new TbGoods();
        goods.setAuditStatus(status);

        Example example = new Example(TbGoods.class);
        example.createCriteria().andIn("id", Arrays.asList(ids));

        //批量更新商品的审核状态
        goodsMapper.updateByExampleSelective(goods,example);

        //如果是审核通过则将sku设置为启用状态
        if ("2".equals(status)){
            TbItem item = new TbItem();
            item.setStatus("1");

            Example itemExample = new Example(TbGoods.class);
            itemExample.createCriteria().andIn("goodsId", Arrays.asList(ids));

            //更新条件
            itemMapper.updateByExampleSelective(item,itemExample);
        }
    }

    @Override
    public void deleteGoodsByIds(Long[] ids) {

        TbGoods goods = new TbGoods();
        goods.setIsDelete("1");

        Example example = new Example(TbGoods.class);
        example.createCriteria().andIn("id",Arrays.asList(ids));

        //批量更新商品的删除状态为删除
        goodsMapper.updateByExampleSelective(goods,example);
    }

    private void saveItemList(Goods goods) {

        if ("1".equals(goods.getGoods().getIsEnableSpec())){
            //启用规格
            if (goods.getItemList()!=null && goods.getItemList().size()>0){
                for (TbItem item : goods.getItemList()){

                    //标题= spu名称 + 所有规格选项的值
                    String title = goods.getGoods().getGoodsName();
                    //获取规格：{"网络":"移动3G","机身内存":"16G"}
                    Map<String,String> map = JSON.parseObject(item.getSpec(), Map.class);
                    Set<Map.Entry<String, String>> entries = map.entrySet();
                    for (Map.Entry<String, String> entry : entries) {
                        title+=" " + entry.getValue();
                    }
                    item.setTitle(title);

                    //设置商品其他信息
                    setItemValue(item,goods);

                    //保存到数据库中（保存sku）
                    itemMapper.insertSelective(item);
                }
            }
        }else {
            //未启用规格
            //1.创建item对象；大多数数据来自spu设置到对象中
            TbItem tbItem = new TbItem();
            //2.如果spu中没有的数据，如 spec（{}）,num(9999),status(0未启用)，isDefault（1默认）
            tbItem.setSpec("{}");
            tbItem.setPrice(goods.getGoods().getPrice());
            tbItem.setStatus("0");
            tbItem.setIsDefault("1");
            tbItem.setNum(9999);
            tbItem.setTitle(goods.getGoods().getGoodsName());

            //设置商品其他信息
            setItemValue(tbItem,goods);

            //3.保存到数据库中
            itemMapper.insertSelective(tbItem);
        }

    }


    //设置商品其他信息
    private void setItemValue(TbItem item, Goods goods) {
        //商品分类 来自 商品spu的第三级商品分类id
        item.setCategoryid(goods.getGoods().getCategory3Id());
        TbItemCat itemCat = itemCatMapper.selectByPrimaryKey(item.getCategoryid());
        item.setCategory(itemCat.getName());

        //图片： 可以从spu中的图片地址列表中获取第一张图片
        if (StringUtils.isEmpty(goods.getGoodsDesc().getItemImages())){
            List<Map> imageList = JSONArray.parseArray(goods.getGoodsDesc().getItemImages(), Map.class);
            item.setImage(imageList.get(0).get("url").toString());

        }

        item.setGoodsId(goods.getGoods().getId());

        //品牌
        TbBrand brand = brandMapper.selectByPrimaryKey(goods.getGoods().getBrandId());
        item.setBrand(brand.getName());

        item.setCreateTime(new Date());
        item.setUpdateTime(item.getCreateTime());

        //卖家
        item.setSellerId(goods.getGoods().getSellerId());
        TbSeller seller = sellerMapper.selectByPrimaryKey(item.getSellerId());
        item.setSeller(seller.getName());
    }

}
