package com.lz.manage.model.domain;

import java.io.Serializable;
import java.util.Map;
import java.util.Date;
import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import com.lz.common.annotation.Excel;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
/**
 * 售后信息对象 tb_after_sales
 *
 * @author YY
 * @date 2026-02-09
 */
@TableName("tb_after_sales")
@Data
public class AfterSales implements Serializable
{
    private static final long serialVersionUID = 1L;

    /** 编号 */
    @Excel(name = "编号")
    @TableId(value = "id", type = IdType.ASSIGN_ID)
    private Long id;

    /** 订单 */
    @Excel(name = "订单")
    private Long orderId;

    /** 商品 */
    @Excel(name = "商品",type = Excel.Type.IMPORT)
    private Long goodsId;
    @TableField(exist = false)
    @Excel(name = "商品名称",type = Excel.Type.EXPORT)
    private String goodsName;

    /** 用户 */
    @Excel(name = "用户",type = Excel.Type.IMPORT)
    private Long userId;
    @TableField(exist = false)
    @Excel(name = "用户名称",type = Excel.Type.EXPORT)
    private String userName;

    /** 售后类型 */
    @Excel(name = "售后类型", dictType = "after_sales_type")
    private String type;

    /** 申请理由 */
    @Excel(name = "申请理由")
    private String apply;

    /** 审核状态 */
    @Excel(name = "审核状态", dictType = "audit_status")
    private String auditStatus;

    /** 审核人 */
    @Excel(name = "审核人")
    private String auditBy;

    /** 审核时间 */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "审核时间", width = 30, dateFormat = "yyyy-MM-dd")
    private Date auditTime;

    /** 审核原因 */
    @Excel(name = "审核原因")
    private String auditDesc;

    /** 备注 */
    @Excel(name = "备注")
    private String remark;

    /** 创建时间 */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "创建时间", width = 30, dateFormat = "yyyy-MM-dd")
    private Date createTime;

    /** 更新人 */
    @Excel(name = "更新人")
    private String updateBy;

    /** 更新时间 */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "更新时间", width = 30, dateFormat = "yyyy-MM-dd")
    private Date updateTime;

    /** 请求参数 */
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    @TableField(exist = false)
    private Map<String, Object> params;
}
