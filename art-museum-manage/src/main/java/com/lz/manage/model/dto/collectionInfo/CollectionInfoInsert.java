package com.lz.manage.model.dto.collectionInfo;

import java.io.Serializable;
import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import org.springframework.beans.BeanUtils;
import com.lz.manage.model.domain.CollectionInfo;
/**
 * 藏品信息Vo对象 tb_collection_info
 *
 * @author YY
 * @date 2026-02-09
 */
@Data
public class CollectionInfoInsert implements Serializable
{
    private static final long serialVersionUID = 1L;

        /**
     * 分类
     */
    private Long categoryId;

    /** 名称 */
    private String name;

    /** 相关图片 */
    private String imageSrc;

    /** 状态 */
    private String status;

    /** 排序类型 */
    private String sortType;

    /** 作者 */
    private String author;

    /** 年代 */
    private String era;

    /** 材质 */
    private String material;

    /** 尺寸 */
    private String size;

    /** 简介 */
    private String introduction;

    /** 详细解读 */
    private String detailedInterpretation;

    /** 历史背景 */
    private String historicalBackground;

    /** 收藏数 */
    private Long collectNumber;

    /** 备注 */
    private String remark;

    /**
     * 对象转封装类
     *
     * @param collectionInfoInsert 插入对象
     * @return CollectionInfoInsert
     */
    public static CollectionInfo insertToObj(CollectionInfoInsert collectionInfoInsert) {
        if (collectionInfoInsert == null) {
            return null;
        }
        CollectionInfo collectionInfo = new CollectionInfo();
        BeanUtils.copyProperties(collectionInfoInsert, collectionInfo);
        return collectionInfo;
    }
}
