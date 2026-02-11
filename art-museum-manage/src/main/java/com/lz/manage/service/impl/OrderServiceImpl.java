package com.lz.manage.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.lz.common.annotation.DataScope;
import com.lz.common.core.domain.entity.SysUser;
import com.lz.common.utils.DateUtils;
import com.lz.common.utils.SecurityUtils;
import com.lz.common.utils.StringUtils;
import com.lz.common.utils.ThrowUtils;
import com.lz.manage.mapper.OrderMapper;
import com.lz.manage.model.domain.*;
import com.lz.manage.model.dto.order.OrderQuery;
import com.lz.manage.model.enums.GoodsStatusEnum;
import com.lz.manage.model.enums.InventoryTypeEnum;
import com.lz.manage.model.enums.OrderStatusEnum;
import com.lz.manage.model.enums.YesNoEnum;
import com.lz.manage.model.vo.order.OrderVo;
import com.lz.manage.service.*;
import com.lz.system.service.ISysUserService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.*;
import java.util.stream.Collectors;

/**
 * 订单信息Service业务层处理
 *
 * @author YY
 * @date 2026-02-09
 */
@Service
public class OrderServiceImpl extends ServiceImpl<OrderMapper, Order> implements IOrderService {

    @Resource
    private OrderMapper orderMapper;

    @Resource
    private IGoodsService goodsService;

    @Resource
    private IUserAddressService userAddressService;

    @Resource
    private IUserBalanceService userBalanceService;

    @Resource
    private ISysUserService sysUserService;

    @Resource
    private IInventoryService inventoryService;

    //region mybatis代码

    /**
     * 查询订单信息
     *
     * @param id 订单信息主键
     * @return 订单信息
     */
    @Override
    public Order selectOrderById(Long id) {
        return orderMapper.selectOrderById(id);
    }

    /**
     * 查询订单信息列表
     *
     * @param order 订单信息
     * @return 订单信息
     */
    @Override
    @DataScope(deptAlias = "tb_order", userAlias = "tb_order")
    public List<Order> selectOrderList(Order order) {
        List<Order> orders = orderMapper.selectOrderList(order);
        for (Order info : orders) {
            SysUser sysUser = sysUserService.selectUserById(info.getUserId());
            if (StringUtils.isNotNull(sysUser)) {
                info.setUserName(sysUser.getUserName());
            }
            Goods goods = goodsService.selectGoodsById(info.getGoodsId());
            if (StringUtils.isNotNull(goods)) {
                info.setGoodsName(goods.getName());
            }
            UserAddress userAddress = userAddressService.selectUserAddressById(info.getAddressId());
            if (StringUtils.isNotNull(userAddress)) {
                StringBuilder stringBuffer = new StringBuilder();
                if (StringUtils.isNotEmpty(userAddress.getProvince())) {
                    stringBuffer.append(userAddress.getProvince());
                }
                if (StringUtils.isNotEmpty(userAddress.getCity())) {
                    stringBuffer.append(userAddress.getCity());
                }
                if (StringUtils.isNotEmpty(userAddress.getCounty())) {
                    stringBuffer.append(userAddress.getCounty());
                }
                if (StringUtils.isNotEmpty(userAddress.getAddress())) {
                    stringBuffer.append(userAddress.getAddress());
                }
                info.setAddressName(stringBuffer.toString());
            }
        }
        return orders;
    }

    /**
     * 新增订单信息
     *
     * @param order 订单信息
     * @return 结果
     */
    @Override
    public int insertOrder(Order order) {
        //首先判断商品库存
        Goods goods = goodsService.selectGoodsById(order.getGoodsId());
        ThrowUtils.throwIf(StringUtils.isNull(goods), "商品不存在");
        ThrowUtils.throwIf(!goods.getStatus().equals(GoodsStatusEnum.GOODS_STATUS_1.getValue()),
                "商品已经下架");
        ThrowUtils.throwIf(goods.getInventory() < order.getNumbers(), "商品库存不足");

        //判断用户余额
        Long userId = SecurityUtils.getUserId();
        UserBalance userBalance = userBalanceService.getOne(new LambdaQueryWrapper<UserBalance>().eq(UserBalance::getUserId, userId));
        ThrowUtils.throwIf(StringUtils.isNull(userBalance), "用户余额不足,请先充值");
        BigDecimal totalPrice = goods.getPrice().multiply(new BigDecimal(order.getNumbers()));
        ThrowUtils.throwIf(userBalance.getBalance().compareTo(totalPrice) < 0, "用户余额不足,请先充值");


        //判断地址
        //如果没有传递就是默认地址
        if (StringUtils.isNull(order.getAddressId())) {
            UserAddress userAddress = new UserAddress();
            userAddress.setUserId(userId);
            userAddress.setIsDefault(YesNoEnum.SYS_YES_NO_Y.getValue());
            List<UserAddress> userAddresses = userAddressService.selectUserAddressList(userAddress);
            ThrowUtils.throwIf(StringUtils.isEmpty(userAddresses), "用户没有默认地址");
            order.setAddressId(userAddresses.get(0).getId());
        }
        UserAddress userAddress = userAddressService.selectUserAddressById(order.getAddressId());
        ThrowUtils.throwIf(StringUtils.isNull(userAddress), "地址不存在");
        ThrowUtils.throwIf(!userAddress.getUserId().equals(userId), "用户地址错误");
        order.setUserId(userId);
        order.setStatus(OrderStatusEnum.ORDER_STATUS_1.getValue());
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
    public int updateOrder(Order order) {
        //如果不是待发货、待支付、收货不可以修改地址
        Order orderDb = orderMapper.selectOrderById(order.getId());
        ThrowUtils.throwIf(StringUtils.isNull(orderDb), "订单不存在");
        ThrowUtils.throwIf(!orderDb.getStatus().equals(OrderStatusEnum.ORDER_STATUS_1.getValue())
                && !orderDb.getStatus().equals(OrderStatusEnum.ORDER_STATUS_2.getValue())
                && !orderDb.getStatus().equals(OrderStatusEnum.ORDER_STATUS_3.getValue()), "订单状态错误,不可修改地址");
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
    public int deleteOrderByIds(Long[] ids) {
        return orderMapper.deleteOrderByIds(ids);
    }

    /**
     * 删除订单信息信息
     *
     * @param id 订单信息主键
     * @return 结果
     */
    @Override
    public int deleteOrderById(Long id) {
        return orderMapper.deleteOrderById(id);
    }

    //endregion
    @Override
    public QueryWrapper<Order> getQueryWrapper(OrderQuery orderQuery) {
        QueryWrapper<Order> queryWrapper = new QueryWrapper<>();
        //如果不使用params可以删除
        Map<String, Object> params = orderQuery.getParams();
        if (StringUtils.isNull(params)) {
            params = new HashMap<>();
        }
        Long id = orderQuery.getId();
        queryWrapper.eq(StringUtils.isNotNull(id), "id", id);

        Long goodsId = orderQuery.getGoodsId();
        queryWrapper.eq(StringUtils.isNotNull(goodsId), "goods_id", goodsId);

        Long userId = orderQuery.getUserId();
        queryWrapper.eq(StringUtils.isNotNull(userId), "user_id", userId);

        String status = orderQuery.getStatus();
        queryWrapper.eq(StringUtils.isNotEmpty(status), "status", status);

        Date createTime = orderQuery.getCreateTime();
        queryWrapper.between(StringUtils.isNotNull(params.get("beginCreateTime")) && StringUtils.isNotNull(params.get("endCreateTime")), "create_time", params.get("beginCreateTime"), params.get("endCreateTime"));

        return queryWrapper;
    }

    @Override
    public List<OrderVo> convertVoList(List<Order> orderList) {
        if (StringUtils.isEmpty(orderList)) {
            return Collections.emptyList();
        }
        return orderList.stream().map(OrderVo::objToVo).collect(Collectors.toList());
    }

    @Transactional
    @Override
    public int payOrder(Long id) {
        Long userId = SecurityUtils.getUserId();
        Order order = orderMapper.selectOrderById(id);
        ThrowUtils.throwIf(StringUtils.isNull(order), "订单不存在");
        ThrowUtils.throwIf(!order.getUserId().equals(userId), "订单不存在错误");
        ThrowUtils.throwIf(!order.getStatus().equals(OrderStatusEnum.ORDER_STATUS_1.getValue()), "订单状态错误");
        //首先判断商品库存
        Goods goods = goodsService.selectGoodsById(order.getGoodsId());
        ThrowUtils.throwIf(StringUtils.isNull(goods), "商品不存在");
        ThrowUtils.throwIf(!goods.getStatus().equals(GoodsStatusEnum.GOODS_STATUS_1.getValue()),
                "商品已经下架");
        ThrowUtils.throwIf(goods.getInventory() < order.getNumbers(), "商品库存不足");

        //判断用户余额
        UserBalance userBalance = userBalanceService.getOne(new LambdaQueryWrapper<UserBalance>().eq(UserBalance::getUserId, userId));
        ThrowUtils.throwIf(StringUtils.isNull(userBalance), "用户余额不足,请先充值");
        BigDecimal totalPrice = goods.getPrice().multiply(new BigDecimal(order.getNumbers()));
        ThrowUtils.throwIf(userBalance.getBalance().compareTo(totalPrice) < 0, "用户余额不足,请先充值");

        userBalance.setBalance(userBalance.getBalance().subtract(totalPrice));
        userBalanceService.updateUserBalance(userBalance);

        goods.setSales(goods.getSales() + order.getNumbers());
        goodsService.updateGoods(goods);
        order.setTotalPrice(totalPrice);
        order.setStatus(OrderStatusEnum.ORDER_STATUS_2.getValue());
        return orderMapper.updateOrder(order);
    }

    @Transactional
    @Override
    public int deliveryOrder(Long id) {
        Order order = orderMapper.selectOrderById(id);
        ThrowUtils.throwIf(StringUtils.isNull(order), "订单不存在");
        ThrowUtils.throwIf(!order.getStatus().equals(OrderStatusEnum.ORDER_STATUS_2.getValue()), "订单状态错误");
        //首先判断商品库存
        Goods goods = goodsService.selectGoodsById(order.getGoodsId());
        ThrowUtils.throwIf(StringUtils.isNull(goods), "商品不存在");
        ThrowUtils.throwIf(!goods.getStatus().equals(GoodsStatusEnum.GOODS_STATUS_1.getValue()),
                "商品已经下架");
        ThrowUtils.throwIf(goods.getInventory() < order.getNumbers(), "商品库存不足");
        order.setStatus(OrderStatusEnum.ORDER_STATUS_3.getValue());

        //创建出入库记录
        Inventory inventory = new Inventory();
        inventory.setGoodsId(goods.getId());
        inventory.setType(InventoryTypeEnum.INVENTORY_TYPE_1.getValue());
        inventory.setName(StringUtils.format("订单号:{},发货出库", order.getId()));
        inventory.setPrice(order.getTotalPrice());
        inventory.setNumbers(order.getNumbers());
        inventory.setInventory(new Date());
        inventory.setRemark(StringUtils.format("订单号:{},发货出库", order.getId()));
        inventory.setUserId(SecurityUtils.getUserId());
        inventoryService.insertInventory(inventory);
        return orderMapper.updateOrder(order);
    }

    @Override
    public int receiveOrder(Long id) {
        Long userId = SecurityUtils.getUserId();
        Order order = orderMapper.selectOrderById(id);
        ThrowUtils.throwIf(StringUtils.isNull(order), "订单不存在");
        ThrowUtils.throwIf(!order.getUserId().equals(userId), "订单不存在错误");
        ThrowUtils.throwIf(!order.getStatus().equals(OrderStatusEnum.ORDER_STATUS_3.getValue()), "订单状态错误");
        order.setStatus(OrderStatusEnum.ORDER_STATUS_4.getValue());
        return orderMapper.updateOrder(order);
    }

    @Override
        public void autoUpdateOrder() {
        //查询到十五分钟以前还没付款的订单
        List<Order> orderList = this.list(new LambdaQueryWrapper<Order>()
                .eq(Order::getStatus, OrderStatusEnum.ORDER_STATUS_1.getValue())
                .lt(Order::getCreateTime, DateUtils.addMinutes(new Date(), -15)));
        if (orderList.isEmpty()) {
            return;
        }
        for (Order order : orderList) {
            //取消订单
            order.setStatus(OrderStatusEnum.ORDER_STATUS_8.getValue());
        }
        this.updateBatchById(orderList);
    }

}
