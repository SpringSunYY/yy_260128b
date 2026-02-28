package com.lz.manage.model.vo.galleryInfo;

import java.io.Serializable;
import java.util.Date;
import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import com.lz.common.annotation.Excel;
import org.springframework.beans.BeanUtils;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.lz.manage.model.domain.GalleryInfo;
/**
 * 图书馆信息Vo对象 tb_gallery_info
 *
 * @author YY
 * @date 2026-02-28
 */
@Data
public class GalleryInfoVo implements Serializable
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
     * @param galleryInfo GalleryInfo实体对象
     * @return GalleryInfoVo
     */
    public static GalleryInfoVo objToVo(GalleryInfo galleryInfo) {
        if (galleryInfo == null) {
            return null;
        }
        GalleryInfoVo galleryInfoVo = new GalleryInfoVo();
        BeanUtils.copyProperties(galleryInfo, galleryInfoVo);
        return galleryInfoVo;
    }
}
