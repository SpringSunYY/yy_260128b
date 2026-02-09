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
import com.lz.manage.model.domain.UserBalance;
import com.lz.manage.model.vo.userBalance.UserBalanceVo;
import com.lz.manage.model.dto.userBalance.UserBalanceQuery;
import com.lz.manage.model.dto.userBalance.UserBalanceInsert;
import com.lz.manage.model.dto.userBalance.UserBalanceEdit;
import com.lz.manage.service.IUserBalanceService;
import com.lz.common.utils.poi.ExcelUtil;
import com.lz.common.core.page.TableDataInfo;

/**
 * 用户余额Controller
 *
 * @author YY
 * @date 2026-02-09
 */
@RestController
@RequestMapping("/manage/userBalance")
public class UserBalanceController extends BaseController
{
    @Resource
    private IUserBalanceService userBalanceService;

    /**
     * 查询用户余额列表
     */
    @PreAuthorize("@ss.hasPermi('manage:userBalance:list')")
    @GetMapping("/list")
    public TableDataInfo list(UserBalanceQuery userBalanceQuery)
    {
        UserBalance userBalance = UserBalanceQuery.queryToObj(userBalanceQuery);
        startPage();
        List<UserBalance> list = userBalanceService.selectUserBalanceList(userBalance);
        List<UserBalanceVo> listVo= list.stream().map(UserBalanceVo::objToVo).collect(Collectors.toList());
        TableDataInfo table = getDataTable(list);
        table.setRows(listVo);
        return table;
    }

    /**
     * 导出用户余额列表
     */
    @PreAuthorize("@ss.hasPermi('manage:userBalance:export')")
    @Log(title = "用户余额", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, UserBalanceQuery userBalanceQuery)
    {
        UserBalance userBalance = UserBalanceQuery.queryToObj(userBalanceQuery);
        List<UserBalance> list = userBalanceService.selectUserBalanceList(userBalance);
        ExcelUtil<UserBalance> util = new ExcelUtil<UserBalance>(UserBalance.class);
        util.exportExcel(response, list, "用户余额数据");
    }

    /**
     * 获取用户余额详细信息
     */
    @PreAuthorize("@ss.hasPermi('manage:userBalance:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id)
    {
        UserBalance userBalance = userBalanceService.selectUserBalanceById(id);
        return success(UserBalanceVo.objToVo(userBalance));
    }

    /**
     * 新增用户余额
     */
    @PreAuthorize("@ss.hasPermi('manage:userBalance:add')")
    @Log(title = "用户余额", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody UserBalanceInsert userBalanceInsert)
    {
        UserBalance userBalance = UserBalanceInsert.insertToObj(userBalanceInsert);
        return toAjax(userBalanceService.insertUserBalance(userBalance));
    }

    /**
     * 修改用户余额
     */
    @PreAuthorize("@ss.hasPermi('manage:userBalance:edit')")
    @Log(title = "用户余额", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody UserBalanceEdit userBalanceEdit)
    {
        UserBalance userBalance = UserBalanceEdit.editToObj(userBalanceEdit);
        return toAjax(userBalanceService.updateUserBalance(userBalance));
    }

    /**
     * 删除用户余额
     */
    @PreAuthorize("@ss.hasPermi('manage:userBalance:remove')")
    @Log(title = "用户余额", businessType = BusinessType.DELETE)
	@DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids)
    {
        return toAjax(userBalanceService.deleteUserBalanceByIds(ids));
    }
}
