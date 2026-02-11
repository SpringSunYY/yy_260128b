package com.lz.manage.service;

import java.util.List;
import com.lz.manage.model.domain.Order;
import com.lz.manage.model.vo.order.OrderVo;
import com.lz.manage.model.dto.order.OrderQuery;

import com.baomidou.mybatisplus.extension.service.IService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
/**
 * 订单信息Service接口
 *
 * @author YY
 * @date 2026-02-09
 */
public interface IOrderService extends IService<Order>
{
    //region mybatis代码
    /**
     * 查询订单信息
     *
     * @param id 订单信息主键
     * @return 订单信息
     */
    public Order selectOrderById(Long id);

    /**
     * 查询订单信息列表
     *
     * @param order 订单信息
     * @return 订单信息集合
     */
    public List<Order> selectOrderList(Order order);

    /**
     * 新增订单信息
     *
     * @param order 订单信息
     * @return 结果
     */
    public int insertOrder(Order order);

    /**
     * 修改订单信息
     *
     * @param order 订单信息
     * @return 结果
     */
    public int updateOrder(Order order);

    /**
     * 批量删除订单信息
     *
     * @param ids 需要删除的订单信息主键集合
     * @return 结果
     */
    public int deleteOrderByIds(Long[] ids);

    /**
     * 删除订单信息信息
     *
     * @param id 订单信息主键
     * @return 结果
     */
    public int deleteOrderById(Long id);
    //endregion
    /**
     * 获取查询条件
     *
     * @param orderQuery 查询条件对象
     * @return 查询条件
     */
    QueryWrapper<Order> getQueryWrapper(OrderQuery orderQuery);

    /**
     * 转换vo
     *
     * @param orderList Order集合
     * @return OrderVO集合
     */
    List<OrderVo> convertVoList(List<Order> orderList);

    int payOrder(Long id);

    int deliveryOrder(Long id);

    int receiveOrder(Long id);

    /**
     * 自动更新订单
     */
    void autoUpdateOrder();

}
