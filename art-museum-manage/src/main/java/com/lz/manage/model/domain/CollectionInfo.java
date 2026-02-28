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
import java.util.Date;
import java.util.Map;

/**
 * 藏品信息对象 tb_collection_info
 *
 * @author YY
 * @date 2026-02-09
 */
@TableName("tb_collection_info")
@Data
public class CollectionInfo implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 编号
     */
    @Excel(name = "编号")
    @TableId(value = "id", type = IdType.ASSIGN_ID)
    private Long id;
    /**
     * 美术馆
     */
    @Excel(name = "美术馆", type = Excel.Type.IMPORT)
    private Long galleryId;
    @Excel(name = "美术馆", type = Excel.Type.EXPORT)
    @TableField(exist = false)
    private String galleryName;


    /**
     * 分类
     */
    @Excel(name = "分类", type = Excel.Type.IMPORT)
    private Long categoryId;
    @Excel(name = "分类", type = Excel.Type.EXPORT)
    @TableField(exist = false)
    private String categoryName;

    /**
     * 名称
     */
    @Excel(name = "名称")
    private String name;

    /**
     * 相关图片
     */
    @Excel(name = "相关图片")
    private String imageSrc;

    /**
     * 状态
     */
    @Excel(name = "状态", dictType = "collection_status")
    private String status;

    /**
     * 排序类型
     */
    @Excel(name = "排序类型", dictType = "collection_sort_type")
    private String sortType;

    /**
     * 作者
     */
    @Excel(name = "作者")
    private String author;

    /**
     * 年代
     */
    @Excel(name = "年代")
    private String era;

    /**
     * 材质
     */
    @Excel(name = "材质")
    private String material;

    /**
     * 尺寸
     */
    @Excel(name = "尺寸")
    private String size;

    /**
     * 简介
     */
    @Excel(name = "简介")
    private String introduction;

    /**
     * 详细解读
     */
    @Excel(name = "详细解读")
    private String detailedInterpretation;

    /**
     * 历史背景
     */
    @Excel(name = "历史背景")
    private String historicalBackground;

    /**
     * 收藏数
     */
    @Excel(name = "收藏数")
    private Long collectNumber;

    /**
     * 备注
     */
    @Excel(name = "备注")
    private String remark;

    /**
     * 创建人
     */
    @Excel(name = "创建人", type = Excel.Type.IMPORT)
    private Long userId;
    @Excel(name = "创建人", type = Excel.Type.EXPORT)
    @TableField(exist = false)
    private String userName;

    /**
     * 更新人
     */
    @Excel(name = "更新人")
    private String updateBy;

    /**
     * 创建时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "创建时间", width = 30, dateFormat = "yyyy-MM-dd")
    private Date createTime;

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
