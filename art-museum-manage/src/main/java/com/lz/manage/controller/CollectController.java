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
import com.lz.manage.model.domain.Collect;
import com.lz.manage.model.vo.collect.CollectVo;
import com.lz.manage.model.dto.collect.CollectQuery;
import com.lz.manage.model.dto.collect.CollectInsert;
import com.lz.manage.model.dto.collect.CollectEdit;
import com.lz.manage.service.ICollectService;
import com.lz.common.utils.poi.ExcelUtil;
import com.lz.common.core.page.TableDataInfo;

/**
 * 收藏信息Controller
 *
 * @author YY
 * @date 2026-02-09
 */
@RestController
@RequestMapping("/manage/collect")
public class CollectController extends BaseController
{
    @Resource
    private ICollectService collectService;

    /**
     * 查询收藏信息列表
     */
    @PreAuthorize("@ss.hasPermi('manage:collect:list')")
    @GetMapping("/list")
    public TableDataInfo list(CollectQuery collectQuery)
    {
        Collect collect = CollectQuery.queryToObj(collectQuery);
        startPage();
        List<Collect> list = collectService.selectCollectList(collect);
        List<CollectVo> listVo= list.stream().map(CollectVo::objToVo).collect(Collectors.toList());
        TableDataInfo table = getDataTable(list);
        table.setRows(listVo);
        return table;
    }

    /**
     * 导出收藏信息列表
     */
    @PreAuthorize("@ss.hasPermi('manage:collect:export')")
    @Log(title = "收藏信息", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, CollectQuery collectQuery)
    {
        Collect collect = CollectQuery.queryToObj(collectQuery);
        List<Collect> list = collectService.selectCollectList(collect);
        ExcelUtil<Collect> util = new ExcelUtil<Collect>(Collect.class);
        util.exportExcel(response, list, "收藏信息数据");
    }

    /**
     * 获取收藏信息详细信息
     */
    @PreAuthorize("@ss.hasPermi('manage:collect:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id)
    {
        Collect collect = collectService.selectCollectById(id);
        return success(CollectVo.objToVo(collect));
    }

    /**
     * 新增收藏信息
     */
    @PreAuthorize("@ss.hasPermi('manage:collect:add')")
    @Log(title = "收藏信息", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody CollectInsert collectInsert)
    {
        Collect collect = CollectInsert.insertToObj(collectInsert);
        return toAjax(collectService.insertCollect(collect));
    }

    /**
     * 修改收藏信息
     */
    @PreAuthorize("@ss.hasPermi('manage:collect:edit')")
    @Log(title = "收藏信息", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody CollectEdit collectEdit)
    {
        Collect collect = CollectEdit.editToObj(collectEdit);
        return toAjax(collectService.updateCollect(collect));
    }

    /**
     * 删除收藏信息
     */
    @PreAuthorize("@ss.hasPermi('manage:collect:remove')")
    @Log(title = "收藏信息", businessType = BusinessType.DELETE)
	@DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids)
    {
        return toAjax(collectService.deleteCollectByIds(ids));
    }
}
