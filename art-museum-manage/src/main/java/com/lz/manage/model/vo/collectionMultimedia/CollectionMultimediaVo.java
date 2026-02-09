package com.lz.manage.model.vo.collectionMultimedia;

import java.io.Serializable;
import java.util.Date;
import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import com.lz.common.annotation.Excel;
import org.springframework.beans.BeanUtils;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.lz.manage.model.domain.CollectionMultimedia;
/**
 * 藏品多媒体Vo对象 tb_collection_multimedia
 *
 * @author YY
 * @date 2026-02-09
 */
@Data
public class CollectionMultimediaVo implements Serializable
{
    private static final long serialVersionUID = 1L;

    /** 编号 */
    private Long id;

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

    /** 创建人 */
    private Long userId;

    /** 更新人 */
    private String updateBy;

    /** 创建时间 */
    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date createTime;

    /** 更新时间 */
    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date updateTime;


     /**
     * 对象转封装类
     *
     * @param collectionMultimedia CollectionMultimedia实体对象
     * @return CollectionMultimediaVo
     */
    public static CollectionMultimediaVo objToVo(CollectionMultimedia collectionMultimedia) {
        if (collectionMultimedia == null) {
            return null;
        }
        CollectionMultimediaVo collectionMultimediaVo = new CollectionMultimediaVo();
        BeanUtils.copyProperties(collectionMultimedia, collectionMultimediaVo);
        return collectionMultimediaVo;
    }
}
