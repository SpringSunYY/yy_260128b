package com.lz.manage.model.dto.collectionMultimedia;

import java.util.Map;
import java.io.Serializable;
import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import org.springframework.beans.BeanUtils;
import com.baomidou.mybatisplus.annotation.TableField;
import com.lz.manage.model.domain.CollectionMultimedia;
/**
 * 藏品多媒体Query对象 tb_collection_multimedia
 *
 * @author YY
 * @date 2026-02-09
 */
@Data
public class CollectionMultimediaQuery implements Serializable
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

    /** 文件 */
    private String fileUrl;

    /** 创建时间 */
    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date createTime;

    /** 请求参数 */
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    @TableField(exist = false)
    private Map<String, Object> params;

    /**
     * 对象转封装类
     *
     * @param collectionMultimediaQuery 查询对象
     * @return CollectionMultimedia
     */
    public static CollectionMultimedia queryToObj(CollectionMultimediaQuery collectionMultimediaQuery) {
        if (collectionMultimediaQuery == null) {
            return null;
        }
        CollectionMultimedia collectionMultimedia = new CollectionMultimedia();
        BeanUtils.copyProperties(collectionMultimediaQuery, collectionMultimedia);
        return collectionMultimedia;
    }
}
