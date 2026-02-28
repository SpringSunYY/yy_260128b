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
import com.lz.manage.model.domain.GalleryInfo;
import com.lz.manage.model.vo.galleryInfo.GalleryInfoVo;
import com.lz.manage.model.dto.galleryInfo.GalleryInfoQuery;
import com.lz.manage.model.dto.galleryInfo.GalleryInfoInsert;
import com.lz.manage.model.dto.galleryInfo.GalleryInfoEdit;
import com.lz.manage.service.IGalleryInfoService;
import com.lz.common.utils.poi.ExcelUtil;
import com.lz.common.core.page.TableDataInfo;

/**
 * 图书馆信息Controller
 *
 * @author YY
 * @date 2026-02-28
 */
@RestController
@RequestMapping("/manage/galleryInfo")
public class GalleryInfoController extends BaseController
{
    @Resource
    private IGalleryInfoService galleryInfoService;

    /**
     * 查询图书馆信息列表
     */
    @PreAuthorize("@ss.hasPermi('manage:galleryInfo:list')")
    @GetMapping("/list")
    public TableDataInfo list(GalleryInfoQuery galleryInfoQuery)
    {
        GalleryInfo galleryInfo = GalleryInfoQuery.queryToObj(galleryInfoQuery);
        startPage();
        List<GalleryInfo> list = galleryInfoService.selectGalleryInfoList(galleryInfo);
        List<GalleryInfoVo> listVo= list.stream().map(GalleryInfoVo::objToVo).collect(Collectors.toList());
        TableDataInfo table = getDataTable(list);
        table.setRows(listVo);
        return table;
    }

    /**
     * 导出图书馆信息列表
     */
    @PreAuthorize("@ss.hasPermi('manage:galleryInfo:export')")
    @Log(title = "图书馆信息", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, GalleryInfoQuery galleryInfoQuery)
    {
        GalleryInfo galleryInfo = GalleryInfoQuery.queryToObj(galleryInfoQuery);
        List<GalleryInfo> list = galleryInfoService.selectGalleryInfoList(galleryInfo);
        ExcelUtil<GalleryInfo> util = new ExcelUtil<GalleryInfo>(GalleryInfo.class);
        util.exportExcel(response, list, "图书馆信息数据");
    }

    /**
     * 获取图书馆信息详细信息
     */
    @PreAuthorize("@ss.hasPermi('manage:galleryInfo:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id)
    {
        GalleryInfo galleryInfo = galleryInfoService.selectGalleryInfoById(id);
        return success(GalleryInfoVo.objToVo(galleryInfo));
    }

    /**
     * 新增图书馆信息
     */
    @PreAuthorize("@ss.hasPermi('manage:galleryInfo:add')")
    @Log(title = "图书馆信息", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody GalleryInfoInsert galleryInfoInsert)
    {
        GalleryInfo galleryInfo = GalleryInfoInsert.insertToObj(galleryInfoInsert);
        return toAjax(galleryInfoService.insertGalleryInfo(galleryInfo));
    }

    /**
     * 修改图书馆信息
     */
    @PreAuthorize("@ss.hasPermi('manage:galleryInfo:edit')")
    @Log(title = "图书馆信息", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody GalleryInfoEdit galleryInfoEdit)
    {
        GalleryInfo galleryInfo = GalleryInfoEdit.editToObj(galleryInfoEdit);
        return toAjax(galleryInfoService.updateGalleryInfo(galleryInfo));
    }

    /**
     * 删除图书馆信息
     */
    @PreAuthorize("@ss.hasPermi('manage:galleryInfo:remove')")
    @Log(title = "图书馆信息", businessType = BusinessType.DELETE)
	@DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids)
    {
        return toAjax(galleryInfoService.deleteGalleryInfoByIds(ids));
    }
}
