package com.lz.manage.model.dto.order;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import org.springframework.beans.BeanUtils;
import com.lz.manage.model.domain.Order;
/**
 * 订单信息Vo对象 tb_order
 *
 * @author YY
 * @date 2026-02-09
 */
@Data
public class OrderEdit implements Serializable
{
    private static final long serialVersionUID = 1L;

    /** 编号 */
    private Long id;

    /** 商品 */
    private Long goodsId;

    /** 用户 */
    private Long userId;

    /** 地址 */
    private Long addressId;

    /** 数量 */
    private Long numbers;

    /** 价格 */
    private BigDecimal totalPrice;

    /** 状态 */
    private String status;

    /** 备注 */
    private String remark;

    /**
     * 对象转封装类
     *
     * @param orderEdit 编辑对象
     * @return Order
     */
    public static Order editToObj(OrderEdit orderEdit) {
        if (orderEdit == null) {
            return null;
        }
        Order order = new Order();
        BeanUtils.copyProperties(orderEdit, order);
        return order;
    }
}
