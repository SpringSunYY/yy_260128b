package com.lz.manage.model.dto.galleryInfo;

import java.io.Serializable;
import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import org.springframework.beans.BeanUtils;
import com.lz.manage.model.domain.GalleryInfo;
/**
 * 图书馆信息Vo对象 tb_gallery_info
 *
 * @author YY
 * @date 2026-02-28
 */
@Data
public class GalleryInfoEdit implements Serializable
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

    /** 开放时间 */
    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date openingTime;

    /** 简介 */
    private String description;

    /** 封面 */
    private String coverUrl;

    /** 状态 */
    private String status;

    /** 备注 */
    private String remark;

    /**
     * 对象转封装类
     *
     * @param galleryInfoEdit 编辑对象
     * @return GalleryInfo
     */
    public static GalleryInfo editToObj(GalleryInfoEdit galleryInfoEdit) {
        if (galleryInfoEdit == null) {
            return null;
        }
        GalleryInfo galleryInfo = new GalleryInfo();
        BeanUtils.copyProperties(galleryInfoEdit, galleryInfo);
        return galleryInfo;
    }
}
