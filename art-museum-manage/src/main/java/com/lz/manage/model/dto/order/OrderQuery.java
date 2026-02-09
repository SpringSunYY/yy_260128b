package com.lz.manage.model.dto.order;

import java.util.Map;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import org.springframework.beans.BeanUtils;
import com.baomidou.mybatisplus.annotation.TableField;
import com.lz.manage.model.domain.Order;
/**
 * 订单信息Query对象 tb_order
 *
 * @author YY
 * @date 2026-02-09
 */
@Data
public class OrderQuery implements Serializable
{
    private static final long serialVersionUID = 1L;

    /** 编号 */
    private Long id;

    /** 商品 */
    private Long goodsId;

    /** 用户 */
    private Long userId;

    /** 状态 */
    private String status;

    /** 创建时间 */
    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date createTime;

    /** 请求参数 */
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    @TableField(exist = false)
    private Map<String, Object> params;

    /**
     * 对象转封装类
     *
     * @param orderQuery 查询对象
     * @return Order
     */
    public static Order queryToObj(OrderQuery orderQuery) {
        if (orderQuery == null) {
            return null;
        }
        Order order = new Order();
        BeanUtils.copyProperties(orderQuery, order);
        return order;
    }
}
