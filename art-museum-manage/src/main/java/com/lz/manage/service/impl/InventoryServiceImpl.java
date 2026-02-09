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
import com.lz.manage.mapper.InventoryMapper;
import com.lz.manage.model.domain.Inventory;
import com.lz.manage.service.IInventoryService;
import com.lz.manage.model.dto.inventory.InventoryQuery;
import com.lz.manage.model.vo.inventory.InventoryVo;

/**
 * 库存信息Service业务层处理
 *
 * @author YY
 * @date 2026-02-09
 */
@Service
public class InventoryServiceImpl extends ServiceImpl<InventoryMapper, Inventory> implements IInventoryService
{

    @Resource
    private InventoryMapper inventoryMapper;

    //region mybatis代码
    /**
     * 查询库存信息
     *
     * @param id 库存信息主键
     * @return 库存信息
     */
    @Override
    public Inventory selectInventoryById(Long id)
    {
        return inventoryMapper.selectInventoryById(id);
    }

    /**
     * 查询库存信息列表
     *
     * @param inventory 库存信息
     * @return 库存信息
     */
    @Override
    public List<Inventory> selectInventoryList(Inventory inventory)
    {
        return inventoryMapper.selectInventoryList(inventory);
    }

    /**
     * 新增库存信息
     *
     * @param inventory 库存信息
     * @return 结果
     */
    @Override
    public int insertInventory(Inventory inventory)
    {
        inventory.setCreateTime(DateUtils.getNowDate());
        return inventoryMapper.insertInventory(inventory);
    }

    /**
     * 修改库存信息
     *
     * @param inventory 库存信息
     * @return 结果
     */
    @Override
    public int updateInventory(Inventory inventory)
    {
        inventory.setUpdateTime(DateUtils.getNowDate());
        return inventoryMapper.updateInventory(inventory);
    }

    /**
     * 批量删除库存信息
     *
     * @param ids 需要删除的库存信息主键
     * @return 结果
     */
    @Override
    public int deleteInventoryByIds(Long[] ids)
    {
        return inventoryMapper.deleteInventoryByIds(ids);
    }

    /**
     * 删除库存信息信息
     *
     * @param id 库存信息主键
     * @return 结果
     */
    @Override
    public int deleteInventoryById(Long id)
    {
        return inventoryMapper.deleteInventoryById(id);
    }
    //endregion
    @Override
    public QueryWrapper<Inventory> getQueryWrapper(InventoryQuery inventoryQuery){
        QueryWrapper<Inventory> queryWrapper = new QueryWrapper<>();
        //如果不使用params可以删除
        Map<String, Object> params = inventoryQuery.getParams();
        if (StringUtils.isNull(params)) {
            params = new HashMap<>();
        }
        Long id = inventoryQuery.getId();
        queryWrapper.eq( StringUtils.isNotNull(id),"id",id);

        Long goodsId = inventoryQuery.getGoodsId();
        queryWrapper.eq( StringUtils.isNotNull(goodsId),"goods_id",goodsId);

        String type = inventoryQuery.getType();
        queryWrapper.eq(StringUtils.isNotEmpty(type) ,"type",type);

        String name = inventoryQuery.getName();
        queryWrapper.like(StringUtils.isNotEmpty(name) ,"name",name);

        Date inventory = inventoryQuery.getInventory();
        queryWrapper.between(StringUtils.isNotNull(params.get("beginInventory"))&&StringUtils.isNotNull(params.get("endInventory")),"inventory",params.get("beginInventory"),params.get("endInventory"));

        Date createTime = inventoryQuery.getCreateTime();
        queryWrapper.between(StringUtils.isNotNull(params.get("beginCreateTime"))&&StringUtils.isNotNull(params.get("endCreateTime")),"create_time",params.get("beginCreateTime"),params.get("endCreateTime"));

        return queryWrapper;
    }

    @Override
    public List<InventoryVo> convertVoList(List<Inventory> inventoryList) {
        if (StringUtils.isEmpty(inventoryList)) {
            return Collections.emptyList();
        }
        return inventoryList.stream().map(InventoryVo::objToVo).collect(Collectors.toList());
    }

}
