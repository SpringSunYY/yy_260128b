package com.lz.manage.model.vo.order;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.lz.manage.model.domain.Order;
import lombok.Data;
import org.springframework.beans.BeanUtils;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * 订单信息Vo对象 tb_order
 *
 * @author YY
 * @date 2026-02-09
 */
@Data
public class OrderVo implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 编号
     */
    private Long id;

    /**
     * 商品
     */
    private Long goodsId;
    /**
     * 商品名称
     */
    private String goodsName;

    /**
     * 用户
     */
    private Long userId;
    /**
     * 用户名称
     */
    private String userName;

    /**
     * 地址
     */
    private Long addressId;
    /**
     * 地址名称
     */
    private String addressName;

    /**
     * 数量
     */
    private Long numbers;

    /**
     * 价格
     */
    private BigDecimal totalPrice;

    /**
     * 状态
     */
    private String status;

    /**
     * 备注
     */
    private String remark;

    /**
     * 创建时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date createTime;

    /**
     * 更新人
     */
    private String updateBy;

    /**
     * 更新时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date updateTime;


    /**
     * 对象转封装类
     *
     * @param order Order实体对象
     * @return OrderVo
     */
    public static OrderVo objToVo(Order order) {
        if (order == null) {
            return null;
        }
        OrderVo orderVo = new OrderVo();
        BeanUtils.copyProperties(order, orderVo);
        return orderVo;
    }
}
