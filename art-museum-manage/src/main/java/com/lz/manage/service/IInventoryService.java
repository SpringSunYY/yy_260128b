package com.lz.manage.service;

import java.util.List;
import com.lz.manage.model.domain.Inventory;
import com.lz.manage.model.vo.inventory.InventoryVo;
import com.lz.manage.model.dto.inventory.InventoryQuery;

import com.baomidou.mybatisplus.extension.service.IService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
/**
 * 库存信息Service接口
 * 
 * @author YY
 * @date 2026-02-09
 */
public interface IInventoryService extends IService<Inventory>
{
    //region mybatis代码
    /**
     * 查询库存信息
     * 
     * @param id 库存信息主键
     * @return 库存信息
     */
    public Inventory selectInventoryById(Long id);

    /**
     * 查询库存信息列表
     * 
     * @param inventory 库存信息
     * @return 库存信息集合
     */
    public List<Inventory> selectInventoryList(Inventory inventory);

    /**
     * 新增库存信息
     * 
     * @param inventory 库存信息
     * @return 结果
     */
    public int insertInventory(Inventory inventory);

    /**
     * 修改库存信息
     * 
     * @param inventory 库存信息
     * @return 结果
     */
    public int updateInventory(Inventory inventory);

    /**
     * 批量删除库存信息
     * 
     * @param ids 需要删除的库存信息主键集合
     * @return 结果
     */
    public int deleteInventoryByIds(Long[] ids);

    /**
     * 删除库存信息信息
     * 
     * @param id 库存信息主键
     * @return 结果
     */
    public int deleteInventoryById(Long id);
    //endregion
    /**
     * 获取查询条件
     *
     * @param inventoryQuery 查询条件对象
     * @return 查询条件
     */
    QueryWrapper<Inventory> getQueryWrapper(InventoryQuery inventoryQuery);

    /**
     * 转换vo
     *
     * @param inventoryList Inventory集合
     * @return InventoryVO集合
     */
    List<InventoryVo> convertVoList(List<Inventory> inventoryList);
}
