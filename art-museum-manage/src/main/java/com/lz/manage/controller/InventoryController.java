package com.lz.manage.controller;

import java.util.List;
import java.util.stream.Collectors;
import javax.servlet.http.HttpServletResponse;
import org.springframework.security.access.prepost.PreAuthorize;
import javax.annotation.Resource;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.lz.common.annotation.Log;
import com.lz.common.core.controller.BaseController;
import com.lz.common.core.domain.AjaxResult;
import com.lz.common.enums.BusinessType;
import com.lz.manage.model.domain.Inventory;
import com.lz.manage.model.vo.inventory.InventoryVo;
import com.lz.manage.model.dto.inventory.InventoryQuery;
import com.lz.manage.model.dto.inventory.InventoryInsert;
import com.lz.manage.model.dto.inventory.InventoryEdit;
import com.lz.manage.service.IInventoryService;
import com.lz.common.utils.poi.ExcelUtil;
import com.lz.common.core.page.TableDataInfo;

/**
 * 库存信息Controller
 *
 * @author YY
 * @date 2026-02-09
 */
@RestController
@RequestMapping("/manage/inventory")
public class InventoryController extends BaseController
{
    @Resource
    private IInventoryService inventoryService;

    /**
     * 查询库存信息列表
     */
    @PreAuthorize("@ss.hasPermi('manage:inventory:list')")
    @GetMapping("/list")
    public TableDataInfo list(InventoryQuery inventoryQuery)
    {
        Inventory inventory = InventoryQuery.queryToObj(inventoryQuery);
        startPage();
        List<Inventory> list = inventoryService.selectInventoryList(inventory);
        List<InventoryVo> listVo= list.stream().map(InventoryVo::objToVo).collect(Collectors.toList());
        TableDataInfo table = getDataTable(list);
        table.setRows(listVo);
        return table;
    }

    /**
     * 导出库存信息列表
     */
    @PreAuthorize("@ss.hasPermi('manage:inventory:export')")
    @Log(title = "库存信息", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, InventoryQuery inventoryQuery)
    {
        Inventory inventory = InventoryQuery.queryToObj(inventoryQuery);
        List<Inventory> list = inventoryService.selectInventoryList(inventory);
        ExcelUtil<Inventory> util = new ExcelUtil<Inventory>(Inventory.class);
        util.exportExcel(response, list, "库存信息数据");
    }

    /**
     * 获取库存信息详细信息
     */
    @PreAuthorize("@ss.hasPermi('manage:inventory:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id)
    {
        Inventory inventory = inventoryService.selectInventoryById(id);
        return success(InventoryVo.objToVo(inventory));
    }

    /**
     * 新增库存信息
     */
    @PreAuthorize("@ss.hasPermi('manage:inventory:add')")
    @Log(title = "库存信息", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody InventoryInsert inventoryInsert)
    {
        Inventory inventory = InventoryInsert.insertToObj(inventoryInsert);
        return toAjax(inventoryService.insertInventory(inventory));
    }

    /**
     * 修改库存信息
     */
    @PreAuthorize("@ss.hasPermi('manage:inventory:edit')")
    @Log(title = "库存信息", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody InventoryEdit inventoryEdit)
    {
        Inventory inventory = InventoryEdit.editToObj(inventoryEdit);
        return toAjax(inventoryService.updateInventory(inventory));
    }

    /**
     * 删除库存信息
     */
    @PreAuthorize("@ss.hasPermi('manage:inventory:remove')")
    @Log(title = "库存信息", businessType = BusinessType.DELETE)
	@DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids)
    {
        return toAjax(inventoryService.deleteInventoryByIds(ids));
    }
}
