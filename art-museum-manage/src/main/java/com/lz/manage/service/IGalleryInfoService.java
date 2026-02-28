package com.lz.manage.service;

import java.util.List;
import com.lz.manage.model.domain.GalleryInfo;
import com.lz.manage.model.vo.galleryInfo.GalleryInfoVo;
import com.lz.manage.model.dto.galleryInfo.GalleryInfoQuery;

import com.baomidou.mybatisplus.extension.service.IService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
/**
 * 图书馆信息Service接口
 * 
 * @author YY
 * @date 2026-02-28
 */
public interface IGalleryInfoService extends IService<GalleryInfo>
{
    //region mybatis代码
    /**
     * 查询图书馆信息
     * 
     * @param id 图书馆信息主键
     * @return 图书馆信息
     */
    public GalleryInfo selectGalleryInfoById(Long id);

    /**
     * 查询图书馆信息列表
     * 
     * @param galleryInfo 图书馆信息
     * @return 图书馆信息集合
     */
    public List<GalleryInfo> selectGalleryInfoList(GalleryInfo galleryInfo);

    /**
     * 新增图书馆信息
     * 
     * @param galleryInfo 图书馆信息
     * @return 结果
     */
    public int insertGalleryInfo(GalleryInfo galleryInfo);

    /**
     * 修改图书馆信息
     * 
     * @param galleryInfo 图书馆信息
     * @return 结果
     */
    public int updateGalleryInfo(GalleryInfo galleryInfo);

    /**
     * 批量删除图书馆信息
     * 
     * @param ids 需要删除的图书馆信息主键集合
     * @return 结果
     */
    public int deleteGalleryInfoByIds(Long[] ids);

    /**
     * 删除图书馆信息信息
     * 
     * @param id 图书馆信息主键
     * @return 结果
     */
    public int deleteGalleryInfoById(Long id);
    //endregion
    /**
     * 获取查询条件
     *
     * @param galleryInfoQuery 查询条件对象
     * @return 查询条件
     */
    QueryWrapper<GalleryInfo> getQueryWrapper(GalleryInfoQuery galleryInfoQuery);

    /**
     * 转换vo
     *
     * @param galleryInfoList GalleryInfo集合
     * @return GalleryInfoVO集合
     */
    List<GalleryInfoVo> convertVoList(List<GalleryInfo> galleryInfoList);
}
