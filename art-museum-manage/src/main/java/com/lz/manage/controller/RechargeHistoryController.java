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
import com.lz.manage.model.domain.RechargeHistory;
import com.lz.manage.model.vo.rechargeHistory.RechargeHistoryVo;
import com.lz.manage.model.dto.rechargeHistory.RechargeHistoryQuery;
import com.lz.manage.model.dto.rechargeHistory.RechargeHistoryInsert;
import com.lz.manage.model.dto.rechargeHistory.RechargeHistoryEdit;
import com.lz.manage.service.IRechargeHistoryService;
import com.lz.common.utils.poi.ExcelUtil;
import com.lz.common.core.page.TableDataInfo;

/**
 * 充值记录Controller
 *
 * @author YY
 * @date 2026-02-09
 */
@RestController
@RequestMapping("/manage/rechargeHistory")
public class RechargeHistoryController extends BaseController
{
    @Resource
    private IRechargeHistoryService rechargeHistoryService;

    /**
     * 查询充值记录列表
     */
    @PreAuthorize("@ss.hasPermi('manage:rechargeHistory:list')")
    @GetMapping("/list")
    public TableDataInfo list(RechargeHistoryQuery rechargeHistoryQuery)
    {
        RechargeHistory rechargeHistory = RechargeHistoryQuery.queryToObj(rechargeHistoryQuery);
        startPage();
        List<RechargeHistory> list = rechargeHistoryService.selectRechargeHistoryList(rechargeHistory);
        List<RechargeHistoryVo> listVo= list.stream().map(RechargeHistoryVo::objToVo).collect(Collectors.toList());
        TableDataInfo table = getDataTable(list);
        table.setRows(listVo);
        return table;
    }

    /**
     * 导出充值记录列表
     */
    @PreAuthorize("@ss.hasPermi('manage:rechargeHistory:export')")
    @Log(title = "充值记录", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, RechargeHistoryQuery rechargeHistoryQuery)
    {
        RechargeHistory rechargeHistory = RechargeHistoryQuery.queryToObj(rechargeHistoryQuery);
        List<RechargeHistory> list = rechargeHistoryService.selectRechargeHistoryList(rechargeHistory);
        ExcelUtil<RechargeHistory> util = new ExcelUtil<RechargeHistory>(RechargeHistory.class);
        util.exportExcel(response, list, "充值记录数据");
    }

    /**
     * 获取充值记录详细信息
     */
    @PreAuthorize("@ss.hasPermi('manage:rechargeHistory:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id)
    {
        RechargeHistory rechargeHistory = rechargeHistoryService.selectRechargeHistoryById(id);
        return success(RechargeHistoryVo.objToVo(rechargeHistory));
    }

    /**
     * 新增充值记录
     */
    @PreAuthorize("@ss.hasPermi('manage:rechargeHistory:add')")
    @Log(title = "充值记录", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody RechargeHistoryInsert rechargeHistoryInsert)
    {
        RechargeHistory rechargeHistory = RechargeHistoryInsert.insertToObj(rechargeHistoryInsert);
        return toAjax(rechargeHistoryService.insertRechargeHistory(rechargeHistory));
    }

    /**
     * 修改充值记录
     */
    @PreAuthorize("@ss.hasPermi('manage:rechargeHistory:edit')")
    @Log(title = "充值记录", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody RechargeHistoryEdit rechargeHistoryEdit)
    {
        RechargeHistory rechargeHistory = RechargeHistoryEdit.editToObj(rechargeHistoryEdit);
        return toAjax(rechargeHistoryService.updateRechargeHistory(rechargeHistory));
    }

    /**
     * 删除充值记录
     */
    @PreAuthorize("@ss.hasPermi('manage:rechargeHistory:remove')")
    @Log(title = "充值记录", businessType = BusinessType.DELETE)
	@DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids)
    {
        return toAjax(rechargeHistoryService.deleteRechargeHistoryByIds(ids));
    }
}
