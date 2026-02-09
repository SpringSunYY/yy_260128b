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
import com.lz.manage.model.domain.UserAddress;
import com.lz.manage.model.vo.userAddress.UserAddressVo;
import com.lz.manage.model.dto.userAddress.UserAddressQuery;
import com.lz.manage.model.dto.userAddress.UserAddressInsert;
import com.lz.manage.model.dto.userAddress.UserAddressEdit;
import com.lz.manage.service.IUserAddressService;
import com.lz.common.utils.poi.ExcelUtil;
import com.lz.common.core.page.TableDataInfo;

/**
 * 用户地址Controller
 *
 * @author YY
 * @date 2026-02-09
 */
@RestController
@RequestMapping("/manage/userAddress")
public class UserAddressController extends BaseController
{
    @Resource
    private IUserAddressService userAddressService;

    /**
     * 查询用户地址列表
     */
    @PreAuthorize("@ss.hasPermi('manage:userAddress:list')")
    @GetMapping("/list")
    public TableDataInfo list(UserAddressQuery userAddressQuery)
    {
        UserAddress userAddress = UserAddressQuery.queryToObj(userAddressQuery);
        startPage();
        List<UserAddress> list = userAddressService.selectUserAddressList(userAddress);
        List<UserAddressVo> listVo= list.stream().map(UserAddressVo::objToVo).collect(Collectors.toList());
        TableDataInfo table = getDataTable(list);
        table.setRows(listVo);
        return table;
    }

    /**
     * 导出用户地址列表
     */
    @PreAuthorize("@ss.hasPermi('manage:userAddress:export')")
    @Log(title = "用户地址", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, UserAddressQuery userAddressQuery)
    {
        UserAddress userAddress = UserAddressQuery.queryToObj(userAddressQuery);
        List<UserAddress> list = userAddressService.selectUserAddressList(userAddress);
        ExcelUtil<UserAddress> util = new ExcelUtil<UserAddress>(UserAddress.class);
        util.exportExcel(response, list, "用户地址数据");
    }

    /**
     * 获取用户地址详细信息
     */
    @PreAuthorize("@ss.hasPermi('manage:userAddress:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id)
    {
        UserAddress userAddress = userAddressService.selectUserAddressById(id);
        return success(UserAddressVo.objToVo(userAddress));
    }

    /**
     * 新增用户地址
     */
    @PreAuthorize("@ss.hasPermi('manage:userAddress:add')")
    @Log(title = "用户地址", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody UserAddressInsert userAddressInsert)
    {
        UserAddress userAddress = UserAddressInsert.insertToObj(userAddressInsert);
        return toAjax(userAddressService.insertUserAddress(userAddress));
    }

    /**
     * 修改用户地址
     */
    @PreAuthorize("@ss.hasPermi('manage:userAddress:edit')")
    @Log(title = "用户地址", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody UserAddressEdit userAddressEdit)
    {
        UserAddress userAddress = UserAddressEdit.editToObj(userAddressEdit);
        return toAjax(userAddressService.updateUserAddress(userAddress));
    }

    /**
     * 删除用户地址
     */
    @PreAuthorize("@ss.hasPermi('manage:userAddress:remove')")
    @Log(title = "用户地址", businessType = BusinessType.DELETE)
	@DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids)
    {
        return toAjax(userAddressService.deleteUserAddressByIds(ids));
    }
}
