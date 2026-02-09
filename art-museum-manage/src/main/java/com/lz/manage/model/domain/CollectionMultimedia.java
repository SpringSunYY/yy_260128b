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
 * 藏品多媒体对象 tb_collection_multimedia
 *
 * @author YY
 * @date 2026-02-09
 */
@TableName("tb_collection_multimedia")
@Data
public class CollectionMultimedia implements Serializable
{
    private static final long serialVersionUID = 1L;

    /** 编号 */
    @Excel(name = "编号")
    @TableId(value = "id", type = IdType.ASSIGN_ID)
    private Long id;

    /** 藏品 */
    @Excel(name = "藏品")
    private Long collectionId;

    /** 名称 */
    @Excel(name = "名称")
    private String name;

    /** 状态 */
    @Excel(name = "状态", dictType = "collection_status")
    private String status;

    /** 类型 */
    @Excel(name = "类型", dictType = "collection_multimedia_type")
    private String type;

    /** 排序 */
    @Excel(name = "排序")
    private Long sortNum;

    /** 文件 */
    @Excel(name = "文件")
    private String fileUrl;

    /** 备注 */
    @Excel(name = "备注")
    private String remark;

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
