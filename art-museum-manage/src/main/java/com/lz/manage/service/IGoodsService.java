package com.lz.manage.service;

import java.util.List;
import com.lz.manage.model.domain.Goods;
import com.lz.manage.model.vo.goods.GoodsVo;
import com.lz.manage.model.dto.goods.GoodsQuery;

import com.baomidou.mybatisplus.extension.service.IService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
/**
 * 商品信息Service接口
 * 
 * @author YY
 * @date 2026-02-09
 */
public interface IGoodsService extends IService<Goods>
{
    //region mybatis代码
    /**
     * 查询商品信息
     * 
     * @param id 商品信息主键
     * @return 商品信息
     */
    public Goods selectGoodsById(Long id);

    /**
     * 查询商品信息列表
     * 
     * @param goods 商品信息
     * @return 商品信息集合
     */
    public List<Goods> selectGoodsList(Goods goods);

    /**
     * 新增商品信息
     * 
     * @param goods 商品信息
     * @return 结果
     */
    public int insertGoods(Goods goods);

    /**
     * 修改商品信息
     * 
     * @param goods 商品信息
     * @return 结果
     */
    public int updateGoods(Goods goods);

    /**
     * 批量删除商品信息
     * 
     * @param ids 需要删除的商品信息主键集合
     * @return 结果
     */
    public int deleteGoodsByIds(Long[] ids);

    /**
     * 删除商品信息信息
     * 
     * @param id 商品信息主键
     * @return 结果
     */
    public int deleteGoodsById(Long id);
    //endregion
    /**
     * 获取查询条件
     *
     * @param goodsQuery 查询条件对象
     * @return 查询条件
     */
    QueryWrapper<Goods> getQueryWrapper(GoodsQuery goodsQuery);

    /**
     * 转换vo
     *
     * @param goodsList Goods集合
     * @return GoodsVO集合
     */
    List<GoodsVo> convertVoList(List<Goods> goodsList);
}
