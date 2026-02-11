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
import com.lz.manage.model.domain.CollectionMultimedia;
import com.lz.manage.model.vo.collectionMultimedia.CollectionMultimediaVo;
import com.lz.manage.model.dto.collectionMultimedia.CollectionMultimediaQuery;
import com.lz.manage.model.dto.collectionMultimedia.CollectionMultimediaInsert;
import com.lz.manage.model.dto.collectionMultimedia.CollectionMultimediaEdit;
import com.lz.manage.service.ICollectionMultimediaService;
import com.lz.common.utils.poi.ExcelUtil;
import com.lz.common.core.page.TableDataInfo;

/**
 * 藏品多媒体Controller
 *
 * @author YY
 * @date 2026-02-09
 */
@RestController
@RequestMapping("/manage/collectionMultimedia")
public class CollectionMultimediaController extends BaseController
{
    @Resource
    private ICollectionMultimediaService collectionMultimediaService;

    /**
     * 查询藏品多媒体列表
     */
    @PreAuthorize("@ss.hasAnyPermi('manage:collectionMultimedia:list,manage:collectionMultimedia:query')")
    @GetMapping("/list")
    public TableDataInfo list(CollectionMultimediaQuery collectionMultimediaQuery)
    {
        CollectionMultimedia collectionMultimedia = CollectionMultimediaQuery.queryToObj(collectionMultimediaQuery);
        startPage();
        List<CollectionMultimedia> list = collectionMultimediaService.selectCollectionMultimediaList(collectionMultimedia);
        List<CollectionMultimediaVo> listVo= list.stream().map(CollectionMultimediaVo::objToVo).collect(Collectors.toList());
        TableDataInfo table = getDataTable(list);
        table.setRows(listVo);
        return table;
    }

    /**
     * 导出藏品多媒体列表
     */
    @PreAuthorize("@ss.hasPermi('manage:collectionMultimedia:export')")
    @Log(title = "藏品多媒体", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, CollectionMultimediaQuery collectionMultimediaQuery)
    {
        CollectionMultimedia collectionMultimedia = CollectionMultimediaQuery.queryToObj(collectionMultimediaQuery);
        List<CollectionMultimedia> list = collectionMultimediaService.selectCollectionMultimediaList(collectionMultimedia);
        ExcelUtil<CollectionMultimedia> util = new ExcelUtil<CollectionMultimedia>(CollectionMultimedia.class);
        util.exportExcel(response, list, "藏品多媒体数据");
    }

    /**
     * 获取藏品多媒体详细信息
     */
    @PreAuthorize("@ss.hasPermi('manage:collectionMultimedia:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id)
    {
        CollectionMultimedia collectionMultimedia = collectionMultimediaService.selectCollectionMultimediaById(id);
        return success(CollectionMultimediaVo.objToVo(collectionMultimedia));
    }

    /**
     * 新增藏品多媒体
     */
    @PreAuthorize("@ss.hasPermi('manage:collectionMultimedia:add')")
    @Log(title = "藏品多媒体", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody CollectionMultimediaInsert collectionMultimediaInsert)
    {
        CollectionMultimedia collectionMultimedia = CollectionMultimediaInsert.insertToObj(collectionMultimediaInsert);
        return toAjax(collectionMultimediaService.insertCollectionMultimedia(collectionMultimedia));
    }

    /**
     * 修改藏品多媒体
     */
    @PreAuthorize("@ss.hasPermi('manage:collectionMultimedia:edit')")
    @Log(title = "藏品多媒体", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody CollectionMultimediaEdit collectionMultimediaEdit)
    {
        CollectionMultimedia collectionMultimedia = CollectionMultimediaEdit.editToObj(collectionMultimediaEdit);
        return toAjax(collectionMultimediaService.updateCollectionMultimedia(collectionMultimedia));
    }

    /**
     * 删除藏品多媒体
     */
    @PreAuthorize("@ss.hasPermi('manage:collectionMultimedia:remove')")
    @Log(title = "藏品多媒体", businessType = BusinessType.DELETE)
	@DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids)
    {
        return toAjax(collectionMultimediaService.deleteCollectionMultimediaByIds(ids));
    }
}
