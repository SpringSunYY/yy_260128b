package com.lz.manage.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.lz.common.core.domain.entity.SysUser;
import com.lz.common.utils.DateUtils;
import com.lz.common.utils.SecurityUtils;
import com.lz.common.utils.StringUtils;
import com.lz.common.utils.ThrowUtils;
import com.lz.manage.mapper.GoodsMapper;
import com.lz.manage.mapper.InventoryMapper;
import com.lz.manage.model.domain.Goods;
import com.lz.manage.model.domain.Inventory;
import com.lz.manage.model.dto.inventory.InventoryQuery;
import com.lz.manage.model.enums.InventoryTypeEnum;
import com.lz.manage.model.vo.inventory.InventoryVo;
import com.lz.manage.service.IInventoryService;
import com.lz.system.service.ISysUserService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.*;
import java.util.stream.Collectors;

/**
 * 库存信息Service业务层处理
 *
 * @author YY
 * @date 2026-02-09
 */
@Service
public class InventoryServiceImpl extends ServiceImpl<InventoryMapper, Inventory> implements IInventoryService {

    @Resource
    private InventoryMapper inventoryMapper;

    @Resource
    private GoodsMapper goodsMapper;

    @Resource
    private ISysUserService sysUserService;

    //region mybatis代码

    /**
     * 查询库存信息
     *
     * @param id 库存信息主键
     * @return 库存信息
     */
    @Override
    public Inventory selectInventoryById(Long id) {
        return inventoryMapper.selectInventoryById(id);
    }

    /**
     * 查询库存信息列表
     *
     * @param inventory 库存信息
     * @return 库存信息
     */
    @Override
    public List<Inventory> selectInventoryList(Inventory inventory) {
        List<Inventory> inventories = inventoryMapper.selectInventoryList(inventory);
        for (Inventory info : inventories) {
            SysUser sysUser = sysUserService.selectUserById(info.getUserId());
            if (StringUtils.isNotNull(sysUser)) {
                info.setUserName(sysUser.getUserName());
            }
            Goods goods = goodsMapper.selectGoodsById(info.getGoodsId());
            if (StringUtils.isNotNull(goods)) {
                info.setGoodsName(goods.getName());
            }
        }
        return inventories;
    }

    /**
     * 新增库存信息
     *
     * @param inventory 库存信息
     * @return 结果
     */
    @Transactional
    @Override
    public int insertInventory(Inventory inventory) {
        //首先查询商品是否存在
        Goods goods = goodsMapper.selectGoodsById(inventory.getGoodsId());
        ThrowUtils.throwIf(StringUtils.isNull(goods), "商品不存在");
        //如果是出库
        if (InventoryTypeEnum.INVENTORY_TYPE_1.getValue().equals(inventory.getType())) {
            goods.setInventory(goods.getInventory() - inventory.getNumbers());
        } else {
            goods.setInventory(goods.getInventory() + inventory.getNumbers());
        }
        goodsMapper.updateGoods(goods);
        inventory.setUserId(SecurityUtils.getUserId());
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
    public int updateInventory(Inventory inventory) {
        inventory.setUpdateBy(SecurityUtils.getUsername());
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
    public int deleteInventoryByIds(Long[] ids) {
        return inventoryMapper.deleteInventoryByIds(ids);
    }

    /**
     * 删除库存信息信息
     *
     * @param id 库存信息主键
     * @return 结果
     */
    @Override
    public int deleteInventoryById(Long id) {
        return inventoryMapper.deleteInventoryById(id);
    }

    //endregion
    @Override
    public QueryWrapper<Inventory> getQueryWrapper(InventoryQuery inventoryQuery) {
        QueryWrapper<Inventory> queryWrapper = new QueryWrapper<>();
        //如果不使用params可以删除
        Map<String, Object> params = inventoryQuery.getParams();
        if (StringUtils.isNull(params)) {
            params = new HashMap<>();
        }
        Long id = inventoryQuery.getId();
        queryWrapper.eq(StringUtils.isNotNull(id), "id", id);

        Long goodsId = inventoryQuery.getGoodsId();
        queryWrapper.eq(StringUtils.isNotNull(goodsId), "goods_id", goodsId);

        String type = inventoryQuery.getType();
        queryWrapper.eq(StringUtils.isNotEmpty(type), "type", type);

        String name = inventoryQuery.getName();
        queryWrapper.like(StringUtils.isNotEmpty(name), "name", name);

        Date inventory = inventoryQuery.getInventory();
        queryWrapper.between(StringUtils.isNotNull(params.get("beginInventory")) && StringUtils.isNotNull(params.get("endInventory")), "inventory", params.get("beginInventory"), params.get("endInventory"));

        Date createTime = inventoryQuery.getCreateTime();
        queryWrapper.between(StringUtils.isNotNull(params.get("beginCreateTime")) && StringUtils.isNotNull(params.get("endCreateTime")), "create_time", params.get("beginCreateTime"), params.get("endCreateTime"));

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
