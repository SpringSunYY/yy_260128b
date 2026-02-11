package com.lz.manage.model.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.lz.common.annotation.Excel;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.Map;

/**
 * 订单信息对象 tb_order
 *
 * @author YY
 * @date 2026-02-09
 */
@TableName("tb_order")
@Data
public class Order implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 编号
     */
    @Excel(name = "编号")
    @TableId(value = "id", type = IdType.ASSIGN_ID)
    private Long id;

    /**
     * 商品
     */
    @Excel(name = "商品", type = Excel.Type.IMPORT)
    private Long goodsId;
    @TableField(exist = false)
    @Excel(name = "商品", type = Excel.Type.EXPORT)
    private String goodsName;

    /**
     * 用户
     */
    @Excel(name = "用户", type = Excel.Type.IMPORT)
    private Long userId;
    @TableField(exist = false)
    @Excel(name = "用户", type = Excel.Type.EXPORT)
    private String userName;

    /**
     * 地址
     */
    @Excel(name = "地址", type = Excel.Type.IMPORT)
    private Long addressId;
    @TableField(exist = false)
    @Excel(name = "地址", type = Excel.Type.EXPORT)
    private String addressName;

    /**
     * 数量
     */
    @Excel(name = "数量")
    private Long numbers;

    /**
     * 价格
     */
    @Excel(name = "价格")
    private BigDecimal totalPrice;

    /**
     * 状态
     */
    @Excel(name = "状态", dictType = "order_status")
    private String status;

    /**
     * 备注
     */
    @Excel(name = "备注")
    private String remark;

    /**
     * 创建时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "创建时间", width = 30, dateFormat = "yyyy-MM-dd")
    private Date createTime;

    /**
     * 更新人
     */
    @Excel(name = "更新人")
    private String updateBy;

    /**
     * 更新时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "更新时间", width = 30, dateFormat = "yyyy-MM-dd")
    private Date updateTime;

    /**
     * 请求参数
     */
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    @TableField(exist = false)
    private Map<String, Object> params;
}
