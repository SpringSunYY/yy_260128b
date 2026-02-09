package com.lz.manage.mapper;

import java.util.List;
import com.lz.manage.model.domain.Goods;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

/**
 * 商品信息Mapper接口
 * 
 * @author YY
 * @date 2026-02-09
 */
public interface GoodsMapper extends BaseMapper<Goods>
{
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
     * 删除商品信息
     * 
     * @param id 商品信息主键
     * @return 结果
     */
    public int deleteGoodsById(Long id);

    /**
     * 批量删除商品信息
     * 
     * @param ids 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteGoodsByIds(Long[] ids);
}
