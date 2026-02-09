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
 * 评价信息对象 tb_evaluate
 *
 * @author YY
 * @date 2026-02-09
 */
@TableName("tb_evaluate")
@Data
public class Evaluate implements Serializable
{
    private static final long serialVersionUID = 1L;

    /** 编号 */
    @Excel(name = "编号")
    @TableId(value = "id", type = IdType.ASSIGN_ID)
    private Long id;

    /** 藏品 */
    @Excel(name = "藏品")
    private Long collectionId;

    /** 状态 */
    @Excel(name = "状态", dictType = "evaluate_status")
    private String status;

    /** 标题 */
    @Excel(name = "标题")
    private String title;

    /** 评分 */
    @Excel(name = "评分")
    private Long score;

    /** 评价内容 */
    @Excel(name = "评价内容")
    private String content;

    /** 创建人 */
    @Excel(name = "创建人")
    private Long userId;

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
