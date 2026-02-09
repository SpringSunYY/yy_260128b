package com.lz.manage.service.impl;

import java.util.*;
import java.util.List;
import java.util.Map;
import java.util.HashMap;
import java.util.stream.Collectors;
import javax.validation.Validator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.lz.common.utils.StringUtils;
import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.lz.common.utils.DateUtils;
import javax.annotation.Resource;
import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.lz.manage.mapper.GoodsMapper;
import com.lz.manage.model.domain.Goods;
import com.lz.manage.service.IGoodsService;
import com.lz.manage.model.dto.goods.GoodsQuery;
import com.lz.manage.model.vo.goods.GoodsVo;

/**
 * 商品信息Service业务层处理
 *
 * @author YY
 * @date 2026-02-09
 */
@Service
public class GoodsServiceImpl extends ServiceImpl<GoodsMapper, Goods> implements IGoodsService
{

    @Resource
    private GoodsMapper goodsMapper;

    //region mybatis代码
    /**
     * 查询商品信息
     *
     * @param id 商品信息主键
     * @return 商品信息
     */
    @Override
    public Goods selectGoodsById(Long id)
    {
        return goodsMapper.selectGoodsById(id);
    }

    /**
     * 查询商品信息列表
     *
     * @param goods 商品信息
     * @return 商品信息
     */
    @Override
    public List<Goods> selectGoodsList(Goods goods)
    {
        return goodsMapper.selectGoodsList(goods);
    }

    /**
     * 新增商品信息
     *
     * @param goods 商品信息
     * @return 结果
     */
    @Override
    public int insertGoods(Goods goods)
    {
        goods.setCreateTime(DateUtils.getNowDate());
        return goodsMapper.insertGoods(goods);
    }

    /**
     * 修改商品信息
     *
     * @param goods 商品信息
     * @return 结果
     */
    @Override
    public int updateGoods(Goods goods)
    {
        goods.setUpdateTime(DateUtils.getNowDate());
        return goodsMapper.updateGoods(goods);
    }

    /**
     * 批量删除商品信息
     *
     * @param ids 需要删除的商品信息主键
     * @return 结果
     */
    @Override
    public int deleteGoodsByIds(Long[] ids)
    {
        return goodsMapper.deleteGoodsByIds(ids);
    }

    /**
     * 删除商品信息信息
     *
     * @param id 商品信息主键
     * @return 结果
     */
    @Override
    public int deleteGoodsById(Long id)
    {
        return goodsMapper.deleteGoodsById(id);
    }
    //endregion
    @Override
    public QueryWrapper<Goods> getQueryWrapper(GoodsQuery goodsQuery){
        QueryWrapper<Goods> queryWrapper = new QueryWrapper<>();
        //如果不使用params可以删除
        Map<String, Object> params = goodsQuery.getParams();
        if (StringUtils.isNull(params)) {
            params = new HashMap<>();
        }
        Long id = goodsQuery.getId();
        queryWrapper.eq( StringUtils.isNotNull(id),"id",id);

        Long collectionId = goodsQuery.getCollectionId();
        queryWrapper.eq( StringUtils.isNotNull(collectionId),"collection_id",collectionId);

        String name = goodsQuery.getName();
        queryWrapper.like(StringUtils.isNotEmpty(name) ,"name",name);

        String status = goodsQuery.getStatus();
        queryWrapper.eq(StringUtils.isNotEmpty(status) ,"status",status);

        Date createTime = goodsQuery.getCreateTime();
        queryWrapper.between(StringUtils.isNotNull(params.get("beginCreateTime"))&&StringUtils.isNotNull(params.get("endCreateTime")),"create_time",params.get("beginCreateTime"),params.get("endCreateTime"));

        return queryWrapper;
    }

    @Override
    public List<GoodsVo> convertVoList(List<Goods> goodsList) {
        if (StringUtils.isEmpty(goodsList)) {
            return Collections.emptyList();
        }
        return goodsList.stream().map(GoodsVo::objToVo).collect(Collectors.toList());
    }

}
