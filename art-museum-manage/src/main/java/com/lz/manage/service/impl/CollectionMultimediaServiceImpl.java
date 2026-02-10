package com.lz.manage.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.lz.common.core.domain.entity.SysUser;
import com.lz.common.utils.DateUtils;
import com.lz.common.utils.SecurityUtils;
import com.lz.common.utils.StringUtils;
import com.lz.common.utils.ThrowUtils;
import com.lz.manage.mapper.CollectionMultimediaMapper;
import com.lz.manage.model.domain.CollectionInfo;
import com.lz.manage.model.domain.CollectionMultimedia;
import com.lz.manage.model.dto.collectionMultimedia.CollectionMultimediaQuery;
import com.lz.manage.model.vo.collectionMultimedia.CollectionMultimediaVo;
import com.lz.manage.service.ICollectionInfoService;
import com.lz.manage.service.ICollectionMultimediaService;
import com.lz.system.service.ISysUserService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.*;
import java.util.stream.Collectors;

/**
 * 藏品多媒体Service业务层处理
 *
 * @author YY
 * @date 2026-02-09
 */
@Service
public class CollectionMultimediaServiceImpl extends ServiceImpl<CollectionMultimediaMapper, CollectionMultimedia> implements ICollectionMultimediaService {

    @Resource
    private CollectionMultimediaMapper collectionMultimediaMapper;

    @Resource
    private ICollectionInfoService collectionInfoService;

    @Resource
    private ISysUserService sysUserService;

    //region mybatis代码

    /**
     * 查询藏品多媒体
     *
     * @param id 藏品多媒体主键
     * @return 藏品多媒体
     */
    @Override
    public CollectionMultimedia selectCollectionMultimediaById(Long id) {
        return collectionMultimediaMapper.selectCollectionMultimediaById(id);
    }

    /**
     * 查询藏品多媒体列表
     *
     * @param collectionMultimedia 藏品多媒体
     * @return 藏品多媒体
     */
    @Override
    public List<CollectionMultimedia> selectCollectionMultimediaList(CollectionMultimedia collectionMultimedia) {
        List<CollectionMultimedia> collectionMultimediaList = collectionMultimediaMapper.selectCollectionMultimediaList(collectionMultimedia);
        for (CollectionMultimedia info : collectionMultimediaList) {
            SysUser sysUser = sysUserService.selectUserById(info.getUserId());
            if (StringUtils.isNotNull(sysUser)) {
                info.setUserName(sysUser.getUserName());
            }
            CollectionInfo collectionInfo = collectionInfoService.selectCollectionInfoById(info.getCollectionId());
            if (StringUtils.isNotNull(collectionInfo)) {
                info.setCollectionName(collectionInfo.getName());
            }
        }
        return collectionMultimediaList;
    }

    /**
     * 新增藏品多媒体
     *
     * @param collectionMultimedia 藏品多媒体
     * @return 结果
     */
    @Override
    public int insertCollectionMultimedia(CollectionMultimedia collectionMultimedia) {
        //查询是否已存在
        CollectionInfo collectionInfo = collectionInfoService.selectCollectionInfoById(collectionMultimedia.getCollectionId());
        ThrowUtils.throwIf(StringUtils.isNull(collectionInfo), "藏品不存在");
        collectionMultimedia.setUserId(SecurityUtils.getUserId());
        collectionMultimedia.setCreateTime(DateUtils.getNowDate());
        return collectionMultimediaMapper.insertCollectionMultimedia(collectionMultimedia);
    }

    /**
     * 修改藏品多媒体
     *
     * @param collectionMultimedia 藏品多媒体
     * @return 结果
     */
    @Override
    public int updateCollectionMultimedia(CollectionMultimedia collectionMultimedia) {
        //查询是否已存在
        CollectionInfo collectionInfo = collectionInfoService.selectCollectionInfoById(collectionMultimedia.getCollectionId());
        ThrowUtils.throwIf(StringUtils.isNull(collectionInfo), "藏品不存在");
        collectionMultimedia.setUpdateBy(SecurityUtils.getUsername());
        collectionMultimedia.setUpdateTime(DateUtils.getNowDate());
        return collectionMultimediaMapper.updateCollectionMultimedia(collectionMultimedia);
    }

    /**
     * 批量删除藏品多媒体
     *
     * @param ids 需要删除的藏品多媒体主键
     * @return 结果
     */
    @Override
    public int deleteCollectionMultimediaByIds(Long[] ids) {
        return collectionMultimediaMapper.deleteCollectionMultimediaByIds(ids);
    }

    /**
     * 删除藏品多媒体信息
     *
     * @param id 藏品多媒体主键
     * @return 结果
     */
    @Override
    public int deleteCollectionMultimediaById(Long id) {
        return collectionMultimediaMapper.deleteCollectionMultimediaById(id);
    }

    //endregion
    @Override
    public QueryWrapper<CollectionMultimedia> getQueryWrapper(CollectionMultimediaQuery collectionMultimediaQuery) {
        QueryWrapper<CollectionMultimedia> queryWrapper = new QueryWrapper<>();
        //如果不使用params可以删除
        Map<String, Object> params = collectionMultimediaQuery.getParams();
        if (StringUtils.isNull(params)) {
            params = new HashMap<>();
        }
        Long id = collectionMultimediaQuery.getId();
        queryWrapper.eq(StringUtils.isNotNull(id), "id", id);

        Long collectionId = collectionMultimediaQuery.getCollectionId();
        queryWrapper.eq(StringUtils.isNotNull(collectionId), "collection_id", collectionId);

        String name = collectionMultimediaQuery.getName();
        queryWrapper.like(StringUtils.isNotEmpty(name), "name", name);

        String status = collectionMultimediaQuery.getStatus();
        queryWrapper.eq(StringUtils.isNotEmpty(status), "status", status);

        String type = collectionMultimediaQuery.getType();
        queryWrapper.eq(StringUtils.isNotEmpty(type), "type", type);

        String fileUrl = collectionMultimediaQuery.getFileUrl();
        queryWrapper.eq(StringUtils.isNotEmpty(fileUrl), "file_url", fileUrl);

        Date createTime = collectionMultimediaQuery.getCreateTime();
        queryWrapper.between(StringUtils.isNotNull(params.get("beginCreateTime")) && StringUtils.isNotNull(params.get("endCreateTime")), "create_time", params.get("beginCreateTime"), params.get("endCreateTime"));

        return queryWrapper;
    }

    @Override
    public List<CollectionMultimediaVo> convertVoList(List<CollectionMultimedia> collectionMultimediaList) {
        if (StringUtils.isEmpty(collectionMultimediaList)) {
            return Collections.emptyList();
        }
        return collectionMultimediaList.stream().map(CollectionMultimediaVo::objToVo).collect(Collectors.toList());
    }

}
