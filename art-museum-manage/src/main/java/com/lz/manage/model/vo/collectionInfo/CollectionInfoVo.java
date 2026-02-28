package com.lz.manage.model.vo.collectionInfo;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.lz.manage.model.domain.CollectionInfo;
import lombok.Data;
import org.springframework.beans.BeanUtils;

import java.io.Serializable;
import java.util.Date;

/**
 * 藏品信息Vo对象 tb_collection_info
 *
 * @author YY
 * @date 2026-02-09
 */
@Data
public class CollectionInfoVo implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 编号
     */
    private Long id;

    /**
     * 美术馆
     */
    private Long galleryId;
    private String galleryName;

    /**
     * 分类
     */
    private Long categoryId;
    private String categoryName;

    /**
     * 名称
     */
    private String name;

    /**
     * 相关图片
     */
    private String imageSrc;

    /**
     * 状态
     */
    private String status;

    /**
     * 排序类型
     */
    private String sortType;

    /**
     * 作者
     */
    private String author;

    /**
     * 年代
     */
    private String era;

    /**
     * 材质
     */
    private String material;

    /**
     * 尺寸
     */
    private String size;

    /**
     * 简介
     */
    private String introduction;

    /**
     * 详细解读
     */
    private String detailedInterpretation;

    /**
     * 历史背景
     */
    private String historicalBackground;

    /**
     * 收藏数
     */
    private Long collectNumber;

    /**
     * 备注
     */
    private String remark;

    /**
     * 创建人
     */
    private Long userId;
    /**
     * 创建人名称
     */
    private String userName;

    /**
     * 更新人
     */
    private String updateBy;

    /**
     * 创建时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date createTime;

    /**
     * 更新时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date updateTime;


    /**
     * 对象转封装类
     *
     * @param collectionInfo CollectionInfo实体对象
     * @return CollectionInfoVo
     */
    public static CollectionInfoVo objToVo(CollectionInfo collectionInfo) {
        if (collectionInfo == null) {
            return null;
        }
        CollectionInfoVo collectionInfoVo = new CollectionInfoVo();
        BeanUtils.copyProperties(collectionInfo, collectionInfoVo);
        return collectionInfoVo;
    }
}
