package com.lz.manage.model.dto.galleryInfo;

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
import com.lz.manage.model.domain.GalleryInfo;
/**
 * 图书馆信息Query对象 tb_gallery_info
 *
 * @author YY
 * @date 2026-02-28
 */
@Data
public class GalleryInfoQuery implements Serializable
{
    private static final long serialVersionUID = 1L;

    /** 编号 */
    private Long id;

    /** 名称 */
    private String name;

    /** 省份 */
    private String province;

    /** 城市 */
    private String city;

    /** 详细地址 */
    private String address;

    /** 状态 */
    private String status;

    /** 更新人 */
    private String updateBy;

    /** 请求参数 */
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    @TableField(exist = false)
    private Map<String, Object> params;

    /**
     * 对象转封装类
     *
     * @param galleryInfoQuery 查询对象
     * @return GalleryInfo
     */
    public static GalleryInfo queryToObj(GalleryInfoQuery galleryInfoQuery) {
        if (galleryInfoQuery == null) {
            return null;
        }
        GalleryInfo galleryInfo = new GalleryInfo();
        BeanUtils.copyProperties(galleryInfoQuery, galleryInfo);
        return galleryInfo;
    }
}
