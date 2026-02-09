package com.lz.manage.model.dto.collectionInfo;

import com.baomidou.mybatisplus.annotation.TableField;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.lz.manage.model.domain.CollectionInfo;
import lombok.Data;
import org.springframework.beans.BeanUtils;

import java.io.Serializable;
import java.util.Date;
import java.util.Map;

/**
 * 藏品信息Query对象 tb_collection_info
 *
 * @author YY
 * @date 2026-02-09
 */
@Data
public class CollectionInfoQuery implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 编号
     */
    private Long id;
    /**
     * 分类
     */
    private Long categoryId;
    /**
     * 名称
     */
    private String name;

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
     * 创建时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date createTime;

    /**
     * 请求参数
     */
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    @TableField(exist = false)
    private Map<String, Object> params;

    /**
     * 对象转封装类
     *
     * @param collectionInfoQuery 查询对象
     * @return CollectionInfo
     */
    public static CollectionInfo queryToObj(CollectionInfoQuery collectionInfoQuery) {
        if (collectionInfoQuery == null) {
            return null;
        }
        CollectionInfo collectionInfo = new CollectionInfo();
        BeanUtils.copyProperties(collectionInfoQuery, collectionInfo);
        return collectionInfo;
    }
}
