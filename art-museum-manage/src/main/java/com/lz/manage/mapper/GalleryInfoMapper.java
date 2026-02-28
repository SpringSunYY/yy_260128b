package com.lz.manage.mapper;

import java.util.List;
import com.lz.manage.model.domain.GalleryInfo;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

/**
 * 美术馆信息Mapper接口
 * 
 * @author YY
 * @date 2026-02-28
 */
public interface GalleryInfoMapper extends BaseMapper<GalleryInfo>
{
    /**
     * 查询美术馆信息
     * 
     * @param id 美术馆信息主键
     * @return 美术馆信息
     */
    public GalleryInfo selectGalleryInfoById(Long id);

    /**
     * 查询美术馆信息列表
     * 
     * @param galleryInfo 美术馆信息
     * @return 美术馆信息集合
     */
    public List<GalleryInfo> selectGalleryInfoList(GalleryInfo galleryInfo);

    /**
     * 新增美术馆信息
     * 
     * @param galleryInfo 美术馆信息
     * @return 结果
     */
    public int insertGalleryInfo(GalleryInfo galleryInfo);

    /**
     * 修改美术馆信息
     * 
     * @param galleryInfo 美术馆信息
     * @return 结果
     */
    public int updateGalleryInfo(GalleryInfo galleryInfo);

    /**
     * 删除美术馆信息
     * 
     * @param id 美术馆信息主键
     * @return 结果
     */
    public int deleteGalleryInfoById(Long id);

    /**
     * 批量删除美术馆信息
     * 
     * @param ids 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteGalleryInfoByIds(Long[] ids);
}
