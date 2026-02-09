package com.lz.manage.service.impl;

import java.util.*;
import java.util.List;
import java.util.Map;
import java.util.HashMap;
import java.util.stream.Collectors;
import javax.validation.Validator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.lz.common.utils.StringUtils;
import java.math.BigDecimal;
import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.lz.common.utils.DateUtils;
import javax.annotation.Resource;
import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.lz.manage.mapper.OrderMapper;
import com.lz.manage.model.domain.Order;
import com.lz.manage.service.IOrderService;
import com.lz.manage.model.dto.order.OrderQuery;
import com.lz.manage.model.vo.order.OrderVo;

/**
 * 订单信息Service业务层处理
 *
 * @author YY
 * @date 2026-02-09
 */
@Service
public class OrderServiceImpl extends ServiceImpl<OrderMapper, Order> implements IOrderService
{

    @Resource
    private OrderMapper orderMapper;

    //region mybatis代码
    /**
     * 查询订单信息
     *
     * @param id 订单信息主键
     * @return 订单信息
     */
    @Override
    public Order selectOrderById(Long id)
    {
        return orderMapper.selectOrderById(id);
    }

    /**
     * 查询订单信息列表
     *
     * @param order 订单信息
     * @return 订单信息
     */
    @Override
    public List<Order> selectOrderList(Order order)
    {
        return orderMapper.selectOrderList(order);
    }

    /**
     * 新增订单信息
     *
     * @param order 订单信息
     * @return 结果
     */
    @Override
    public int insertOrder(Order order)
    {
        order.setCreateTime(DateUtils.getNowDate());
        return orderMapper.insertOrder(order);
    }

    /**
     * 修改订单信息
     *
     * @param order 订单信息
     * @return 结果
     */
    @Override
    public int updateOrder(Order order)
    {
        order.setUpdateTime(DateUtils.getNowDate());
        return orderMapper.updateOrder(order);
    }

    /**
     * 批量删除订单信息
     *
     * @param ids 需要删除的订单信息主键
     * @return 结果
     */
    @Override
    public int deleteOrderByIds(Long[] ids)
    {
        return orderMapper.deleteOrderByIds(ids);
    }

    /**
     * 删除订单信息信息
     *
     * @param id 订单信息主键
     * @return 结果
     */
    @Override
    public int deleteOrderById(Long id)
    {
        return orderMapper.deleteOrderById(id);
    }
    //endregion
    @Override
    public QueryWrapper<Order> getQueryWrapper(OrderQuery orderQuery){
        QueryWrapper<Order> queryWrapper = new QueryWrapper<>();
        //如果不使用params可以删除
        Map<String, Object> params = orderQuery.getParams();
        if (StringUtils.isNull(params)) {
            params = new HashMap<>();
        }
        Long id = orderQuery.getId();
        queryWrapper.eq( StringUtils.isNotNull(id),"id",id);

        Long goodsId = orderQuery.getGoodsId();
        queryWrapper.eq( StringUtils.isNotNull(goodsId),"goods_id",goodsId);

        Long userId = orderQuery.getUserId();
        queryWrapper.eq( StringUtils.isNotNull(userId),"user_id",userId);

        String status = orderQuery.getStatus();
        queryWrapper.eq(StringUtils.isNotEmpty(status) ,"status",status);

        Date createTime = orderQuery.getCreateTime();
        queryWrapper.between(StringUtils.isNotNull(params.get("beginCreateTime"))&&StringUtils.isNotNull(params.get("endCreateTime")),"create_time",params.get("beginCreateTime"),params.get("endCreateTime"));

        return queryWrapper;
    }

    @Override
    public List<OrderVo> convertVoList(List<Order> orderList) {
        if (StringUtils.isEmpty(orderList)) {
            return Collections.emptyList();
        }
        return orderList.stream().map(OrderVo::objToVo).collect(Collectors.toList());
    }

}
