package com.lz.manage.service.impl;

import java.util.*;
import java.util.List;
import java.util.Map;
import java.util.HashMap;
import java.util.stream.Collectors;
import javax.validation.Validator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.lz.common.utils.StringUtils;
import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.lz.common.utils.DateUtils;
import javax.annotation.Resource;
import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.lz.manage.mapper.GalleryInfoMapper;
import com.lz.manage.model.domain.GalleryInfo;
import com.lz.manage.service.IGalleryInfoService;
import com.lz.manage.model.dto.galleryInfo.GalleryInfoQuery;
import com.lz.manage.model.vo.galleryInfo.GalleryInfoVo;

/**
 * 图书馆信息Service业务层处理
 *
 * @author YY
 * @date 2026-02-28
 */
@Service
public class GalleryInfoServiceImpl extends ServiceImpl<GalleryInfoMapper, GalleryInfo> implements IGalleryInfoService
{

    @Resource
    private GalleryInfoMapper galleryInfoMapper;

    //region mybatis代码
    /**
     * 查询图书馆信息
     *
     * @param id 图书馆信息主键
     * @return 图书馆信息
     */
    @Override
    public GalleryInfo selectGalleryInfoById(Long id)
    {
        return galleryInfoMapper.selectGalleryInfoById(id);
    }

    /**
     * 查询图书馆信息列表
     *
     * @param galleryInfo 图书馆信息
     * @return 图书馆信息
     */
    @Override
    public List<GalleryInfo> selectGalleryInfoList(GalleryInfo galleryInfo)
    {
        return galleryInfoMapper.selectGalleryInfoList(galleryInfo);
    }

    /**
     * 新增图书馆信息
     *
     * @param galleryInfo 图书馆信息
     * @return 结果
     */
    @Override
    public int insertGalleryInfo(GalleryInfo galleryInfo)
    {
        galleryInfo.setCreateTime(DateUtils.getNowDate());
        return galleryInfoMapper.insertGalleryInfo(galleryInfo);
    }

    /**
     * 修改图书馆信息
     *
     * @param galleryInfo 图书馆信息
     * @return 结果
     */
    @Override
    public int updateGalleryInfo(GalleryInfo galleryInfo)
    {
        galleryInfo.setUpdateTime(DateUtils.getNowDate());
        return galleryInfoMapper.updateGalleryInfo(galleryInfo);
    }

    /**
     * 批量删除图书馆信息
     *
     * @param ids 需要删除的图书馆信息主键
     * @return 结果
     */
    @Override
    public int deleteGalleryInfoByIds(Long[] ids)
    {
        return galleryInfoMapper.deleteGalleryInfoByIds(ids);
    }

    /**
     * 删除图书馆信息信息
     *
     * @param id 图书馆信息主键
     * @return 结果
     */
    @Override
    public int deleteGalleryInfoById(Long id)
    {
        return galleryInfoMapper.deleteGalleryInfoById(id);
    }
    //endregion
    @Override
    public QueryWrapper<GalleryInfo> getQueryWrapper(GalleryInfoQuery galleryInfoQuery){
        QueryWrapper<GalleryInfo> queryWrapper = new QueryWrapper<>();
        //如果不使用params可以删除
        Map<String, Object> params = galleryInfoQuery.getParams();
        if (StringUtils.isNull(params)) {
            params = new HashMap<>();
        }
        Long id = galleryInfoQuery.getId();
        queryWrapper.eq( StringUtils.isNotNull(id),"id",id);

        String name = galleryInfoQuery.getName();
        queryWrapper.like(StringUtils.isNotEmpty(name) ,"name",name);

        String province = galleryInfoQuery.getProvince();
        queryWrapper.like(StringUtils.isNotEmpty(province) ,"province",province);

        String city = galleryInfoQuery.getCity();
        queryWrapper.like(StringUtils.isNotEmpty(city) ,"city",city);

        String address = galleryInfoQuery.getAddress();
        queryWrapper.like(StringUtils.isNotEmpty(address) ,"address",address);

        String status = galleryInfoQuery.getStatus();
        queryWrapper.eq(StringUtils.isNotEmpty(status) ,"status",status);

        String updateBy = galleryInfoQuery.getUpdateBy();
        queryWrapper.between(StringUtils.isNotNull(params.get("beginUpdateBy"))&&StringUtils.isNotNull(params.get("endUpdateBy")),"update_by",params.get("beginUpdateBy"),params.get("endUpdateBy"));

        return queryWrapper;
    }

    @Override
    public List<GalleryInfoVo> convertVoList(List<GalleryInfo> galleryInfoList) {
        if (StringUtils.isEmpty(galleryInfoList)) {
            return Collections.emptyList();
        }
        return galleryInfoList.stream().map(GalleryInfoVo::objToVo).collect(Collectors.toList());
    }

}
