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
import com.lz.manage.mapper.CollectionInfoMapper;
import com.lz.manage.model.domain.CollectionInfo;
import com.lz.manage.service.ICollectionInfoService;
import com.lz.manage.model.dto.collectionInfo.CollectionInfoQuery;
import com.lz.manage.model.vo.collectionInfo.CollectionInfoVo;

/**
 * 藏品信息Service业务层处理
 *
 * @author YY
 * @date 2026-02-09
 */
@Service
public class CollectionInfoServiceImpl extends ServiceImpl<CollectionInfoMapper, CollectionInfo> implements ICollectionInfoService
{

    @Resource
    private CollectionInfoMapper collectionInfoMapper;

    //region mybatis代码
    /**
     * 查询藏品信息
     *
     * @param id 藏品信息主键
     * @return 藏品信息
     */
    @Override
    public CollectionInfo selectCollectionInfoById(Long id)
    {
        return collectionInfoMapper.selectCollectionInfoById(id);
    }

    /**
     * 查询藏品信息列表
     *
     * @param collectionInfo 藏品信息
     * @return 藏品信息
     */
    @Override
    public List<CollectionInfo> selectCollectionInfoList(CollectionInfo collectionInfo)
    {
        return collectionInfoMapper.selectCollectionInfoList(collectionInfo);
    }

    /**
     * 新增藏品信息
     *
     * @param collectionInfo 藏品信息
     * @return 结果
     */
    @Override
    public int insertCollectionInfo(CollectionInfo collectionInfo)
    {
        collectionInfo.setCreateTime(DateUtils.getNowDate());
        return collectionInfoMapper.insertCollectionInfo(collectionInfo);
    }

    /**
     * 修改藏品信息
     *
     * @param collectionInfo 藏品信息
     * @return 结果
     */
    @Override
    public int updateCollectionInfo(CollectionInfo collectionInfo)
    {
        collectionInfo.setUpdateTime(DateUtils.getNowDate());
        return collectionInfoMapper.updateCollectionInfo(collectionInfo);
    }

    /**
     * 批量删除藏品信息
     *
     * @param ids 需要删除的藏品信息主键
     * @return 结果
     */
    @Override
    public int deleteCollectionInfoByIds(Long[] ids)
    {
        return collectionInfoMapper.deleteCollectionInfoByIds(ids);
    }

    /**
     * 删除藏品信息信息
     *
     * @param id 藏品信息主键
     * @return 结果
     */
    @Override
    public int deleteCollectionInfoById(Long id)
    {
        return collectionInfoMapper.deleteCollectionInfoById(id);
    }
    //endregion
    @Override
    public QueryWrapper<CollectionInfo> getQueryWrapper(CollectionInfoQuery collectionInfoQuery){
        QueryWrapper<CollectionInfo> queryWrapper = new QueryWrapper<>();
        //如果不使用params可以删除
        Map<String, Object> params = collectionInfoQuery.getParams();
        if (StringUtils.isNull(params)) {
            params = new HashMap<>();
        }
        Long id = collectionInfoQuery.getId();
        queryWrapper.eq( StringUtils.isNotNull(id),"id",id);

        String name = collectionInfoQuery.getName();
        queryWrapper.like(StringUtils.isNotEmpty(name) ,"name",name);

        String status = collectionInfoQuery.getStatus();
        queryWrapper.eq(StringUtils.isNotEmpty(status) ,"status",status);

        String sortType = collectionInfoQuery.getSortType();
        queryWrapper.eq(StringUtils.isNotEmpty(sortType) ,"sort_type",sortType);

        String author = collectionInfoQuery.getAuthor();
        queryWrapper.like(StringUtils.isNotEmpty(author) ,"author",author);

        String era = collectionInfoQuery.getEra();
        queryWrapper.like(StringUtils.isNotEmpty(era) ,"era",era);

        Date createTime = collectionInfoQuery.getCreateTime();
        queryWrapper.between(StringUtils.isNotNull(params.get("beginCreateTime"))&&StringUtils.isNotNull(params.get("endCreateTime")),"create_time",params.get("beginCreateTime"),params.get("endCreateTime"));

        return queryWrapper;
    }

    @Override
    public List<CollectionInfoVo> convertVoList(List<CollectionInfo> collectionInfoList) {
        if (StringUtils.isEmpty(collectionInfoList)) {
            return Collections.emptyList();
        }
        return collectionInfoList.stream().map(CollectionInfoVo::objToVo).collect(Collectors.toList());
    }

}
