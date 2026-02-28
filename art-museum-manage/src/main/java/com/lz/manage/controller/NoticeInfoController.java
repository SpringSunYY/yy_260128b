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
import com.lz.manage.model.domain.NoticeInfo;
import com.lz.manage.model.vo.noticeInfo.NoticeInfoVo;
import com.lz.manage.model.dto.noticeInfo.NoticeInfoQuery;
import com.lz.manage.model.dto.noticeInfo.NoticeInfoInsert;
import com.lz.manage.model.dto.noticeInfo.NoticeInfoEdit;
import com.lz.manage.service.INoticeInfoService;
import com.lz.common.utils.poi.ExcelUtil;
import com.lz.common.core.page.TableDataInfo;

/**
 * 资讯信息Controller
 *
 * @author YY
 * @date 2026-02-10
 */
@RestController
@RequestMapping("/manage/noticeInfo")
public class NoticeInfoController extends BaseController
{
    @Resource
    private INoticeInfoService noticeInfoService;

    /**
     * 查询资讯信息列表
     */
    @PreAuthorize("@ss.hasPermi('manage:noticeInfo:list')")
    @GetMapping("/list")
    public TableDataInfo list(NoticeInfoQuery noticeInfoQuery)
    {
        NoticeInfo noticeInfo = NoticeInfoQuery.queryToObj(noticeInfoQuery);
        startPage();
        List<NoticeInfo> list = noticeInfoService.selectNoticeInfoList(noticeInfo);
        List<NoticeInfoVo> listVo= list.stream().map(NoticeInfoVo::objToVo).collect(Collectors.toList());
        TableDataInfo table = getDataTable(list);
        table.setRows(listVo);
        return table;
    }

    /**
     * 导出资讯信息列表
     */
    @PreAuthorize("@ss.hasPermi('manage:noticeInfo:export')")
    @Log(title = "资讯信息", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, NoticeInfoQuery noticeInfoQuery)
    {
        NoticeInfo noticeInfo = NoticeInfoQuery.queryToObj(noticeInfoQuery);
        List<NoticeInfo> list = noticeInfoService.selectNoticeInfoList(noticeInfo);
        ExcelUtil<NoticeInfo> util = new ExcelUtil<NoticeInfo>(NoticeInfo.class);
        util.exportExcel(response, list, "资讯信息数据");
    }

    /**
     * 获取资讯信息详细信息
     */
    @PreAuthorize("@ss.hasPermi('manage:noticeInfo:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id)
    {
        NoticeInfo noticeInfo = noticeInfoService.selectNoticeInfoById(id);
        return success(NoticeInfoVo.objToVo(noticeInfo));
    }

    /**
     * 详细信息
     */
    @PreAuthorize("@ss.hasPermi('manage:noticeInfo:query')")
    @GetMapping(value = "/detail/{id}")
    public AjaxResult detail(@PathVariable("id") Long id)
    {
        NoticeInfoVo noticeInfo = noticeInfoService.selectNoticeInfoDetailById(id);
        return success(noticeInfo);
    }

    /**
     * 新增资讯信息
     */
    @PreAuthorize("@ss.hasPermi('manage:noticeInfo:add')")
    @Log(title = "资讯信息", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody NoticeInfoInsert noticeInfoInsert)
    {
        NoticeInfo noticeInfo = NoticeInfoInsert.insertToObj(noticeInfoInsert);
        return toAjax(noticeInfoService.insertNoticeInfo(noticeInfo));
    }

    /**
     * 修改资讯信息
     */
    @PreAuthorize("@ss.hasPermi('manage:noticeInfo:edit')")
    @Log(title = "资讯信息", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody NoticeInfoEdit noticeInfoEdit)
    {
        NoticeInfo noticeInfo = NoticeInfoEdit.editToObj(noticeInfoEdit);
        return toAjax(noticeInfoService.updateNoticeInfo(noticeInfo));
    }

    /**
     * 删除资讯信息
     */
    @PreAuthorize("@ss.hasPermi('manage:noticeInfo:remove')")
    @Log(title = "资讯信息", businessType = BusinessType.DELETE)
	@DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids)
    {
        return toAjax(noticeInfoService.deleteNoticeInfoByIds(ids));
    }
}
