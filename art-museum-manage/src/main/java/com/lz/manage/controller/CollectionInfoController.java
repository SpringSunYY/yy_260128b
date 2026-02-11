package com.lz.manage.controller;

import com.lz.common.annotation.Log;
import com.lz.common.core.controller.BaseController;
import com.lz.common.core.domain.AjaxResult;
import com.lz.common.core.page.TableDataInfo;
import com.lz.common.enums.BusinessType;
import com.lz.common.utils.poi.ExcelUtil;
import com.lz.manage.model.domain.CollectionInfo;
import com.lz.manage.model.dto.collectionInfo.CollectionInfoEdit;
import com.lz.manage.model.dto.collectionInfo.CollectionInfoInsert;
import com.lz.manage.model.dto.collectionInfo.CollectionInfoQuery;
import com.lz.manage.model.vo.collectionInfo.CollectionInfoDetailVo;
import com.lz.manage.model.vo.collectionInfo.CollectionInfoVo;
import com.lz.manage.service.ICollectionInfoService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 藏品信息Controller
 *
 * @author YY
 * @date 2026-02-09
 */
@RestController
@RequestMapping("/manage/collectionInfo")
public class CollectionInfoController extends BaseController {
    @Resource
    private ICollectionInfoService collectionInfoService;

    /**
     * 查询藏品信息列表
     */
    @PreAuthorize("@ss.hasAnyPermi('manage:collectionInfo:list,manage:collectionInfo:query')")
    @GetMapping("/list")
    public TableDataInfo list(CollectionInfoQuery collectionInfoQuery) {
        CollectionInfo collectionInfo = CollectionInfoQuery.queryToObj(collectionInfoQuery);
        startPage();
        List<CollectionInfo> list = collectionInfoService.selectCollectionInfoList(collectionInfo);
        List<CollectionInfoVo> listVo = list.stream().map(CollectionInfoVo::objToVo).collect(Collectors.toList());
        TableDataInfo table = getDataTable(list);
        table.setRows(listVo);
        return table;
    }

    /**
     * 导出藏品信息列表
     */
    @PreAuthorize("@ss.hasPermi('manage:collectionInfo:export')")
    @Log(title = "藏品信息", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, CollectionInfoQuery collectionInfoQuery) {
        CollectionInfo collectionInfo = CollectionInfoQuery.queryToObj(collectionInfoQuery);
        List<CollectionInfo> list = collectionInfoService.selectCollectionInfoList(collectionInfo);
        ExcelUtil<CollectionInfo> util = new ExcelUtil<CollectionInfo>(CollectionInfo.class);
        util.exportExcel(response, list, "藏品信息数据");
    }

    /**
     * 获取藏品信息详细信息
     */
    @PreAuthorize("@ss.hasPermi('manage:collectionInfo:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id) {
        CollectionInfo collectionInfo = collectionInfoService.selectCollectionInfoById(id);
        return success(CollectionInfoVo.objToVo(collectionInfo));
    }

    /**
     * 藏品详细信息
     */
    @PreAuthorize("@ss.hasPermi('manage:collectionInfo:query')")
    @GetMapping(value = "/detail/{id}")
    public AjaxResult getDetailInfo(@PathVariable("id") Long id) {
        CollectionInfoDetailVo collectionInfoDetailVo = collectionInfoService.selectCollectionInfoDetailById(id);
        return success(collectionInfoDetailVo);
    }

    /**
     * 新增藏品信息
     */
    @PreAuthorize("@ss.hasPermi('manage:collectionInfo:add')")
    @Log(title = "藏品信息", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody CollectionInfoInsert collectionInfoInsert) {
        CollectionInfo collectionInfo = CollectionInfoInsert.insertToObj(collectionInfoInsert);
        return toAjax(collectionInfoService.insertCollectionInfo(collectionInfo));
    }

    /**
     * 修改藏品信息
     */
    @PreAuthorize("@ss.hasPermi('manage:collectionInfo:edit')")
    @Log(title = "藏品信息", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody CollectionInfoEdit collectionInfoEdit) {
        CollectionInfo collectionInfo = CollectionInfoEdit.editToObj(collectionInfoEdit);
        return toAjax(collectionInfoService.updateCollectionInfo(collectionInfo));
    }

    /**
     * 删除藏品信息
     */
    @PreAuthorize("@ss.hasPermi('manage:collectionInfo:remove')")
    @Log(title = "藏品信息", businessType = BusinessType.DELETE)
    @DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids) {
        return toAjax(collectionInfoService.deleteCollectionInfoByIds(ids));
    }
}
