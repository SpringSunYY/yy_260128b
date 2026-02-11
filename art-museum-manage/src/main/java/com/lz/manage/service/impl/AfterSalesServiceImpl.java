package com.lz.manage.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.lz.common.core.domain.entity.SysUser;
import com.lz.common.utils.DateUtils;
import com.lz.common.utils.SecurityUtils;
import com.lz.common.utils.StringUtils;
import com.lz.common.utils.ThrowUtils;
import com.lz.manage.mapper.AfterSalesMapper;
import com.lz.manage.model.domain.AfterSales;
import com.lz.manage.model.domain.Goods;
import com.lz.manage.model.domain.Order;
import com.lz.manage.model.dto.afterSales.AfterSalesQuery;
import com.lz.manage.model.enums.AuditStatusEnum;
import com.lz.manage.model.enums.OrderStatusEnum;
import com.lz.manage.model.vo.afterSales.AfterSalesVo;
import com.lz.manage.service.IAfterSalesService;
import com.lz.manage.service.IGoodsService;
import com.lz.manage.service.IOrderService;
import com.lz.system.service.ISysUserService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.*;
import java.util.stream.Collectors;

/**
 * 售后信息Service业务层处理
 *
 * @author YY
 * @date 2026-02-09
 */
@Service
public class AfterSalesServiceImpl extends ServiceImpl<AfterSalesMapper, AfterSales> implements IAfterSalesService {

    @Resource
    private AfterSalesMapper afterSalesMapper;

    @Resource
    private IGoodsService goodsService;

    @Resource
    private IOrderService orderService;

    @Resource
    private ISysUserService sysUserService;

    //region mybatis代码

    /**
     * 查询售后信息
     *
     * @param id 售后信息主键
     * @return 售后信息
     */
    @Override
    public AfterSales selectAfterSalesById(Long id) {
        return afterSalesMapper.selectAfterSalesById(id);
    }

    /**
     * 查询售后信息列表
     *
     * @param afterSales 售后信息
     * @return 售后信息
     */
    @Override
    public List<AfterSales> selectAfterSalesList(AfterSales afterSales) {
        List<AfterSales> afterSalesList = afterSalesMapper.selectAfterSalesList(afterSales);
        for (AfterSales info : afterSalesList) {
            SysUser sysUser = sysUserService.selectUserById(info.getUserId());
            if (StringUtils.isNotNull(sysUser)) {
                info.setUserName(sysUser.getUserName());
            }
            Goods goods = goodsService.selectGoodsById(info.getGoodsId());
            if (StringUtils.isNotNull(goods)) {
                info.setGoodsName(goods.getName());
            }
        }
        return afterSalesList;
    }

    /**
     * 新增售后信息
     *
     * @param afterSales 售后信息
     * @return 结果
     */
    @Transactional
    @Override
    public int insertAfterSales(AfterSales afterSales) {
        afterSales.setUserId(SecurityUtils.getUserId());
        //先查询订单
        Order order = orderService.selectOrderById(afterSales.getOrderId());
        ThrowUtils.throwIf(StringUtils.isNull(order), "订单不存在");
        ThrowUtils.throwIf(!order.getUserId().equals(SecurityUtils.getUserId()), "订单不存在错误");
        ThrowUtils.throwIf(order.getStatus().equals(OrderStatusEnum.ORDER_STATUS_1.getValue()), "订单状态错误,尚未付款");

        //如果这个订单已经有售后
        AfterSales afterSalesByOrder = afterSalesMapper.selectOne(new LambdaQueryWrapper<AfterSales>().eq(AfterSales::getOrderId, afterSales.getOrderId()));
        ThrowUtils.throwIf(StringUtils.isNotNull(afterSalesByOrder), "订单已经售后");

        afterSales.setGoodsId(order.getGoodsId());
        afterSales.setCreateTime(DateUtils.getNowDate());
        afterSales.setAuditStatus(AuditStatusEnum.AUDIT_STATUS_1.getValue());

        order.setStatus(OrderStatusEnum.ORDER_STATUS_5.getValue());
        orderService.updateById(order);

        return afterSalesMapper.insertAfterSales(afterSales);
    }

    /**
     * 修改售后信息
     *
     * @param afterSales 售后信息
     * @return 结果
     */
    @Override
    public int updateAfterSales(AfterSales afterSales) {
        afterSales.setUpdateTime(DateUtils.getNowDate());
        return afterSalesMapper.updateAfterSales(afterSales);
    }

    /**
     * 批量删除售后信息
     *
     * @param ids 需要删除的售后信息主键
     * @return 结果
     */
    @Override
    public int deleteAfterSalesByIds(Long[] ids) {
        return afterSalesMapper.deleteAfterSalesByIds(ids);
    }

    /**
     * 删除售后信息信息
     *
     * @param id 售后信息主键
     * @return 结果
     */
    @Override
    public int deleteAfterSalesById(Long id) {
        return afterSalesMapper.deleteAfterSalesById(id);
    }

    //endregion
    @Override
    public QueryWrapper<AfterSales> getQueryWrapper(AfterSalesQuery afterSalesQuery) {
        QueryWrapper<AfterSales> queryWrapper = new QueryWrapper<>();
        //如果不使用params可以删除
        Map<String, Object> params = afterSalesQuery.getParams();
        if (StringUtils.isNull(params)) {
            params = new HashMap<>();
        }
        Long id = afterSalesQuery.getId();
        queryWrapper.eq(StringUtils.isNotNull(id), "id", id);

        String type = afterSalesQuery.getType();
        queryWrapper.eq(StringUtils.isNotEmpty(type), "type", type);

        String auditStatus = afterSalesQuery.getAuditStatus();
        queryWrapper.eq(StringUtils.isNotNull(auditStatus), "audit_status", auditStatus);

        String auditBy = afterSalesQuery.getAuditBy();
        queryWrapper.like(StringUtils.isNotEmpty(auditBy), "audit_by", auditBy);

        Date auditTime = afterSalesQuery.getAuditTime();
        queryWrapper.between(StringUtils.isNotNull(params.get("beginAuditTime")) && StringUtils.isNotNull(params.get("endAuditTime")), "audit_time", params.get("beginAuditTime"), params.get("endAuditTime"));

        Date createTime = afterSalesQuery.getCreateTime();
        queryWrapper.between(StringUtils.isNotNull(params.get("beginCreateTime")) && StringUtils.isNotNull(params.get("endCreateTime")), "create_time", params.get("beginCreateTime"), params.get("endCreateTime"));

        String updateBy = afterSalesQuery.getUpdateBy();
        queryWrapper.like(StringUtils.isNotEmpty(updateBy), "update_by", updateBy);

        return queryWrapper;
    }

    @Override
    public List<AfterSalesVo> convertVoList(List<AfterSales> afterSalesList) {
        if (StringUtils.isEmpty(afterSalesList)) {
            return Collections.emptyList();
        }
        return afterSalesList.stream().map(AfterSalesVo::objToVo).collect(Collectors.toList());
    }

}
