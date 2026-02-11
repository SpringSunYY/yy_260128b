package com.lz.manage.controller;

import com.lz.common.annotation.Log;
import com.lz.common.core.controller.BaseController;
import com.lz.common.core.domain.AjaxResult;
import com.lz.common.core.page.TableDataInfo;
import com.lz.common.enums.BusinessType;
import com.lz.common.utils.poi.ExcelUtil;
import com.lz.manage.model.domain.AfterSales;
import com.lz.manage.model.dto.afterSales.AfterSalesEdit;
import com.lz.manage.model.dto.afterSales.AfterSalesInsert;
import com.lz.manage.model.dto.afterSales.AfterSalesQuery;
import com.lz.manage.model.vo.afterSales.AfterSalesVo;
import com.lz.manage.service.IAfterSalesService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 售后信息Controller
 *
 * @author YY
 * @date 2026-02-09
 */
@RestController
@RequestMapping("/manage/afterSales")
public class AfterSalesController extends BaseController {
    @Resource
    private IAfterSalesService afterSalesService;

    /**
     * 查询售后信息列表
     */
    @PreAuthorize("@ss.hasPermi('manage:afterSales:list')")
    @GetMapping("/list")
    public TableDataInfo list(AfterSalesQuery afterSalesQuery) {
        AfterSales afterSales = AfterSalesQuery.queryToObj(afterSalesQuery);
        startPage();
        List<AfterSales> list = afterSalesService.selectAfterSalesList(afterSales);
        List<AfterSalesVo> listVo = list.stream().map(AfterSalesVo::objToVo).collect(Collectors.toList());
        TableDataInfo table = getDataTable(list);
        table.setRows(listVo);
        return table;
    }

    /**
     * 导出售后信息列表
     */
    @PreAuthorize("@ss.hasPermi('manage:afterSales:export')")
    @Log(title = "售后信息", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, AfterSalesQuery afterSalesQuery) {
        AfterSales afterSales = AfterSalesQuery.queryToObj(afterSalesQuery);
        List<AfterSales> list = afterSalesService.selectAfterSalesList(afterSales);
        ExcelUtil<AfterSales> util = new ExcelUtil<AfterSales>(AfterSales.class);
        util.exportExcel(response, list, "售后信息数据");
    }

    /**
     * 获取售后信息详细信息
     */
    @PreAuthorize("@ss.hasPermi('manage:afterSales:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id) {
        AfterSales afterSales = afterSalesService.selectAfterSalesById(id);
        return success(AfterSalesVo.objToVo(afterSales));
    }

    /**
     * 新增售后信息
     */
    @PreAuthorize("@ss.hasPermi('manage:afterSales:add')")
    @Log(title = "售后信息", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody AfterSalesInsert afterSalesInsert) {
        AfterSales afterSales = AfterSalesInsert.insertToObj(afterSalesInsert);
        return toAjax(afterSalesService.insertAfterSales(afterSales));
    }

    /**
     * 修改售后信息
     */
    @PreAuthorize("@ss.hasPermi('manage:afterSales:edit')")
    @Log(title = "售后信息", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody AfterSalesEdit afterSalesEdit) {
        AfterSales afterSales = AfterSalesEdit.editToObj(afterSalesEdit);
        return toAjax(afterSalesService.updateAfterSales(afterSales));
    }

    /**
     * 审核售后信息
     */
    @PreAuthorize("@ss.hasPermi('manage:afterSales:audit')")
    @Log(title = "售后信息", businessType = BusinessType.UPDATE)
    @PutMapping("/audit")
    public AjaxResult audit(@RequestBody AfterSalesEdit afterSalesEdit) {
        AfterSales afterSales = AfterSalesEdit.editToObj(afterSalesEdit);
        return toAjax(afterSalesService.auditAfterSales(afterSales));
    }

    /**
     * 删除售后信息
     */
    @PreAuthorize("@ss.hasPermi('manage:afterSales:remove')")
    @Log(title = "售后信息", businessType = BusinessType.DELETE)
    @DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids) {
        return toAjax(afterSalesService.deleteAfterSalesByIds(ids));
    }
}
