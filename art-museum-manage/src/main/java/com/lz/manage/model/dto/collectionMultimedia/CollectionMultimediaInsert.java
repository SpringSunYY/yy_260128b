package com.lz.manage.model.dto.collectionMultimedia;

import java.io.Serializable;
import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import org.springframework.beans.BeanUtils;
import com.lz.manage.model.domain.CollectionMultimedia;
/**
 * 藏品多媒体Vo对象 tb_collection_multimedia
 *
 * @author YY
 * @date 2026-02-09
 */
@Data
public class CollectionMultimediaInsert implements Serializable
{
    private static final long serialVersionUID = 1L;

    /** 藏品 */
    private Long collectionId;

    /** 名称 */
    private String name;

    /** 状态 */
    private String status;

    /** 类型 */
    private String type;

    /** 排序 */
    private Long sortNum;

    /** 文件 */
    private String fileUrl;

    /** 备注 */
    private String remark;

    /**
     * 对象转封装类
     *
     * @param collectionMultimediaInsert 插入对象
     * @return CollectionMultimediaInsert
     */
    public static CollectionMultimedia insertToObj(CollectionMultimediaInsert collectionMultimediaInsert) {
        if (collectionMultimediaInsert == null) {
            return null;
        }
        CollectionMultimedia collectionMultimedia = new CollectionMultimedia();
        BeanUtils.copyProperties(collectionMultimediaInsert, collectionMultimedia);
        return collectionMultimedia;
    }
}
