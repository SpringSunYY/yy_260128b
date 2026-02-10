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
 * 咨询信息对象 tb_notice_info
 *
 * @author YY
 * @date 2026-02-10
 */
@TableName("tb_notice_info")
@Data
public class NoticeInfo implements Serializable
{
    private static final long serialVersionUID = 1L;

    /** 编号 */
    @Excel(name = "编号")
    @TableId(value = "id", type = IdType.ASSIGN_ID)
    private Long id;

    /** 标题 */
    @Excel(name = "标题")
    private String title;

    /** 类型 */
    @Excel(name = "类型", dictType = "notion_type")
    private String type;

    /** 排序 */
    @Excel(name = "排序")
    private Long sortNum;

    /** 状态 */
    @Excel(name = "状态", dictType = "sys_notice_status")
    private String status;

    /** 关联藏品 */
    @Excel(name = "关联藏品", type = Excel.Type.IMPORT)
    private String collectionIds;
    @TableField(exist = false)
    @Excel(name = "关联藏品", type = Excel.Type.EXPORT)
    private String collectionNames;
    /** 内容 */
    @Excel(name = "内容")
    private String content;

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
