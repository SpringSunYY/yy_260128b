package com.lz.manage.model.domain;

import java.io.Serializable;
import java.math.BigDecimal;
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
 * 商品信息对象 tb_goods
 *
 * @author YY
 * @date 2026-02-09
 */
@TableName("tb_goods")
@Data
public class Goods implements Serializable
{
    private static final long serialVersionUID = 1L;

    /** 编号 */
    @Excel(name = "编号")
    @TableId(value = "id", type = IdType.ASSIGN_ID)
    private Long id;

    /** 关联藏品 */
    @Excel(name = "关联藏品",type = Excel.Type.IMPORT)
    private Long collectionId;
    @TableField(exist = false)
    @Excel(name = "关联藏品",type = Excel.Type.EXPORT)
    private String collectionName;

    /** 名称 */
    @Excel(name = "名称")
    private String name;

    /** 价格 */
    @Excel(name = "价格")
    private BigDecimal price;

    /** 销量 */
    @Excel(name = "销量")
    private Long sales;

    /** 状态 */
    @Excel(name = "状态", dictType = "goods_status")
    private String status;

    /** 库存 */
    @Excel(name = "库存")
    private Long inventory;

    /** 主图 */
    @Excel(name = "主图")
    private String imageSrc;

    /** 描述 */
    @Excel(name = "描述")
    private String description;

    /** 备注 */
    @Excel(name = "备注")
    private String remark;

    /** 创建人 */
    @Excel(name = "创建人", type = Excel.Type.IMPORT)
    private Long userId;
    @TableField(exist = false)
    @Excel(name = "创建人", type = Excel.Type.EXPORT)
    private String userName;

    /** 更新人 */
    @Excel(name = "更新人")
    private String updateBy;

    /** 创建时间 */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "创建时间", width = 30, dateFormat = "yyyy-MM-dd")
    private Date createTime;

    /** 更新时间 */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "更新时间", width = 30, dateFormat = "yyyy-MM-dd")
    private Date updateTime;

    /** 请求参数 */
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    @TableField(exist = false)
    private Map<String, Object> params;
}
