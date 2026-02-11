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
import com.lz.manage.model.domain.Order;
import com.lz.manage.model.vo.order.OrderVo;
import com.lz.manage.model.dto.order.OrderQuery;
import com.lz.manage.model.dto.order.OrderInsert;
import com.lz.manage.model.dto.order.OrderEdit;
import com.lz.manage.service.IOrderService;
import com.lz.common.utils.poi.ExcelUtil;
import com.lz.common.core.page.TableDataInfo;

/**
 * 订单信息Controller
 *
 * @author YY
 * @date 2026-02-09
 */
@RestController
@RequestMapping("/manage/order")
public class OrderController extends BaseController
{
    @Resource
    private IOrderService orderService;

    /**
     * 查询订单信息列表
     */
    @PreAuthorize("@ss.hasPermi('manage:order:list')")
    @GetMapping("/list")
    public TableDataInfo list(OrderQuery orderQuery)
    {
        Order order = OrderQuery.queryToObj(orderQuery);
        startPage();
        List<Order> list = orderService.selectOrderList(order);
        List<OrderVo> listVo= list.stream().map(OrderVo::objToVo).collect(Collectors.toList());
        TableDataInfo table = getDataTable(list);
        table.setRows(listVo);
        return table;
    }

    /**
     * 导出订单信息列表
     */
    @PreAuthorize("@ss.hasPermi('manage:order:export')")
    @Log(title = "订单信息", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, OrderQuery orderQuery)
    {
        Order order = OrderQuery.queryToObj(orderQuery);
        List<Order> list = orderService.selectOrderList(order);
        ExcelUtil<Order> util = new ExcelUtil<Order>(Order.class);
        util.exportExcel(response, list, "订单信息数据");
    }

    /**
     * 获取订单信息详细信息
     */
    @PreAuthorize("@ss.hasPermi('manage:order:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id)
    {
        Order order = orderService.selectOrderById(id);
        return success(OrderVo.objToVo(order));
    }

    /**
     * 支付
     */
    @PreAuthorize("@ss.hasPermi('manage:order:add')")
    @Log(title = "订单信息", businessType = BusinessType.UPDATE)
    @GetMapping("/payOrder/{id}")
    public AjaxResult payOrder(@PathVariable("id") Long id)
    {
        return toAjax(orderService.payOrder(id));
    }

    /**
     * 发货
     */
    @PreAuthorize("@ss.hasPermi('manage:order:manage')")
    @Log(title = "订单信息", businessType = BusinessType.UPDATE)
    @GetMapping("/deliveryOrder/{id}")
    public AjaxResult deliveryOrder(@PathVariable("id") Long id)
    {
        return toAjax(orderService.deliveryOrder(id));
    }

    /**
     * 新增订单信息
     */
    @PreAuthorize("@ss.hasPermi('manage:order:add')")
    @Log(title = "订单信息", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody OrderInsert orderInsert)
    {
        Order order = OrderInsert.insertToObj(orderInsert);
        return toAjax(orderService.insertOrder(order));
    }

    /**
     * 修改订单信息
     */
    @PreAuthorize("@ss.hasPermi('manage:order:edit')")
    @Log(title = "订单信息", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody OrderEdit orderEdit)
    {
        Order order = OrderEdit.editToObj(orderEdit);
        return toAjax(orderService.updateOrder(order));
    }

    /**
     * 删除订单信息
     */
    @PreAuthorize("@ss.hasPermi('manage:order:remove')")
    @Log(title = "订单信息", businessType = BusinessType.DELETE)
	@DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids)
    {
        return toAjax(orderService.deleteOrderByIds(ids));
    }
}
