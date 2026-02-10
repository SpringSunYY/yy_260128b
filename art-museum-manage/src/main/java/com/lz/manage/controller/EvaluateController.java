package com.lz.manage.controller;

import com.lz.common.annotation.Log;
import com.lz.common.core.controller.BaseController;
import com.lz.common.core.domain.AjaxResult;
import com.lz.common.core.page.TableDataInfo;
import com.lz.common.enums.BusinessType;
import com.lz.common.utils.poi.ExcelUtil;
import com.lz.manage.model.domain.Evaluate;
import com.lz.manage.model.dto.evaluate.EvaluateEdit;
import com.lz.manage.model.dto.evaluate.EvaluateInsert;
import com.lz.manage.model.dto.evaluate.EvaluateQuery;
import com.lz.manage.model.vo.evaluate.EvaluateVo;
import com.lz.manage.service.IEvaluateService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 评价信息Controller
 *
 * @author YY
 * @date 2026-02-09
 */
@RestController
@RequestMapping("/manage/evaluate")
public class EvaluateController extends BaseController {
    @Resource
    private IEvaluateService evaluateService;

    /**
     * 查询评价信息列表
     */
    @PreAuthorize("@ss.hasPermi('manage:evaluate:list')")
    @GetMapping("/list")
    public TableDataInfo list(EvaluateQuery evaluateQuery) {
        Evaluate evaluate = EvaluateQuery.queryToObj(evaluateQuery);
        startPage();
        List<Evaluate> list = evaluateService.selectEvaluateList(evaluate);
        List<EvaluateVo> listVo = list.stream().map(EvaluateVo::objToVo).collect(Collectors.toList());
        TableDataInfo table = getDataTable(list);
        table.setRows(listVo);
        return table;
    }

    @PreAuthorize("@ss.hasPermi('manage:evaluate:list')")
    @GetMapping("/list/collection")
    public TableDataInfo listCollection(EvaluateQuery evaluateQuery) {
        Evaluate evaluate = EvaluateQuery.queryToObj(evaluateQuery);
        startPage();
        List<Evaluate> list = evaluateService.selectEvaluateCollectionList(evaluate);
        List<EvaluateVo> listVo = list.stream().map(EvaluateVo::objToVo).collect(Collectors.toList());
        TableDataInfo table = getDataTable(list);
        table.setRows(listVo);
        return table;
    }

    /**
     * 导出评价信息列表
     */
    @PreAuthorize("@ss.hasPermi('manage:evaluate:export')")
    @Log(title = "评价信息", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, EvaluateQuery evaluateQuery) {
        Evaluate evaluate = EvaluateQuery.queryToObj(evaluateQuery);
        List<Evaluate> list = evaluateService.selectEvaluateList(evaluate);
        ExcelUtil<Evaluate> util = new ExcelUtil<Evaluate>(Evaluate.class);
        util.exportExcel(response, list, "评价信息数据");
    }

    /**
     * 获取评价信息详细信息
     */
    @PreAuthorize("@ss.hasPermi('manage:evaluate:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id) {
        Evaluate evaluate = evaluateService.selectEvaluateById(id);
        return success(EvaluateVo.objToVo(evaluate));
    }

    /**
     * 新增评价信息
     */
    @PreAuthorize("@ss.hasPermi('manage:evaluate:add')")
    @Log(title = "评价信息", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody EvaluateInsert evaluateInsert) {
        Evaluate evaluate = EvaluateInsert.insertToObj(evaluateInsert);
        return toAjax(evaluateService.insertEvaluate(evaluate));
    }

    /**
     * 修改评价信息
     */
    @PreAuthorize("@ss.hasPermi('manage:evaluate:edit')")
    @Log(title = "评价信息", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody EvaluateEdit evaluateEdit) {
        Evaluate evaluate = EvaluateEdit.editToObj(evaluateEdit);
        return toAjax(evaluateService.updateEvaluate(evaluate));
    }

    /**
     * 删除评价信息
     */
    @PreAuthorize("@ss.hasPermi('manage:evaluate:remove')")
    @Log(title = "评价信息", businessType = BusinessType.DELETE)
    @DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids) {
        return toAjax(evaluateService.deleteEvaluateByIds(ids));
    }
}
