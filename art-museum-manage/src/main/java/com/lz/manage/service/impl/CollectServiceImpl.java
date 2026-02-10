package com.lz.manage.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.lz.common.core.domain.entity.SysUser;
import com.lz.common.utils.DateUtils;
import com.lz.common.utils.StringUtils;
import com.lz.manage.mapper.CollectMapper;
import com.lz.manage.model.domain.Collect;
import com.lz.manage.model.domain.CollectionInfo;
import com.lz.manage.model.domain.NoticeInfo;
import com.lz.manage.model.dto.collect.CollectQuery;
import com.lz.manage.model.enums.CollectTypeEnum;
import com.lz.manage.model.vo.collect.CollectVo;
import com.lz.manage.service.ICollectService;
import com.lz.manage.service.ICollectionInfoService;
import com.lz.manage.service.INoticeInfoService;
import com.lz.system.service.ISysUserService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.*;
import java.util.stream.Collectors;

/**
 * 收藏信息Service业务层处理
 *
 * @author YY
 * @date 2026-02-09
 */
@Service
public class CollectServiceImpl extends ServiceImpl<CollectMapper, Collect> implements ICollectService {

    @Resource
    private CollectMapper collectMapper;

    @Resource
    private ICollectionInfoService collectionInfoService;

    @Resource
    private ISysUserService sysUserService;

    @Resource
    private INoticeInfoService noticeInfoService;
    //region mybatis代码

    /**
     * 查询收藏信息
     *
     * @param id 收藏信息主键
     * @return 收藏信息
     */
    @Override
    public Collect selectCollectById(Long id) {
        return collectMapper.selectCollectById(id);
    }

    /**
     * 查询收藏信息列表
     *
     * @param collect 收藏信息
     * @return 收藏信息
     */
    @Override
    public List<Collect> selectCollectList(Collect collect) {
        List<Collect> collects = collectMapper.selectCollectList(collect);
        for (Collect info : collects) {
            SysUser sysUser = sysUserService.selectUserById(info.getUserId());
            if (StringUtils.isNotNull(sysUser)) {
                info.setUserName(sysUser.getUserName());
            }
            if (info.getType().equals(CollectTypeEnum.COLLECT_TYPE_2.getValue())) {
                CollectionInfo collectionInfo = collectionInfoService.selectCollectionInfoById(info.getTargetId());
                if (StringUtils.isNotNull(collectionInfo)) {
                    info.setTargetName(collectionInfo.getName());
                }
            } else {
                NoticeInfo noticeInfo = noticeInfoService.selectNoticeInfoById(info.getTargetId());
                if (StringUtils.isNotNull(noticeInfo)) {
                    info.setTargetName(noticeInfo.getTitle());
                }
            }
        }
        return collects;
    }

    /**
     * 新增收藏信息
     *
     * @param collect 收藏信息
     * @return 结果
     */
    @Override
    public int insertCollect(Collect collect) {
        //首先查询是否已收藏
        LambdaQueryWrapper<Collect> queryWrapper = new LambdaQueryWrapper<Collect>()
                .eq(Collect::getUserId, collect.getUserId())
                .eq(Collect::getTargetId, collect.getTargetId())
                .eq(Collect::getType, collect.getType());
        Collect collectDb = collectMapper.selectOne(queryWrapper);
        boolean isCollect = StringUtils.isNull(collectDb);
        if (!isCollect) {
            collectMapper.delete(queryWrapper);
        } else {
            collect.setCreateTime(DateUtils.getNowDate());
            collectMapper.insertCollect(collect);
        }
        //如果是藏品，需要更新数量
        if (collect.getType().equals(CollectTypeEnum.COLLECT_TYPE_2.getValue())) {
            CollectionInfo collectionInfo = collectionInfoService.selectCollectionInfoById(collect.getTargetId());
            if (StringUtils.isNotNull(collectionInfo)) {
                long count = this.count(new LambdaQueryWrapper<Collect>()
                        .eq(Collect::getTargetId, collectionInfo.getId())
                        .eq(Collect::getType, CollectTypeEnum.COLLECT_TYPE_2.getValue()));

                collectionInfo.setCollectNumber(count);
                collectionInfoService.updateById(collectionInfo);
            }
        } else {
            NoticeInfo noticeInfo = noticeInfoService.selectNoticeInfoById(collect.getTargetId());
            if (StringUtils.isNotNull(noticeInfo)) {
                long count = this.count(new LambdaQueryWrapper<Collect>()
                        .eq(Collect::getTargetId, noticeInfo.getId())
                        .eq(Collect::getType, CollectTypeEnum.COLLECT_TYPE_1.getValue()));
                noticeInfo.setCollectNumber(count);
                noticeInfoService.updateById(noticeInfo);
            }
        }
        return 1;
    }

    /**
     * 修改收藏信息
     *
     * @param collect 收藏信息
     * @return 结果
     */
    @Override
    public int updateCollect(Collect collect) {
        return collectMapper.updateCollect(collect);
    }

    /**
     * 批量删除收藏信息
     *
     * @param ids 需要删除的收藏信息主键
     * @return 结果
     */
    @Override
    public int deleteCollectByIds(Long[] ids) {
        return collectMapper.deleteCollectByIds(ids);
    }

    /**
     * 删除收藏信息信息
     *
     * @param id 收藏信息主键
     * @return 结果
     */
    @Override
    public int deleteCollectById(Long id) {
        return collectMapper.deleteCollectById(id);
    }

    //endregion
    @Override
    public QueryWrapper<Collect> getQueryWrapper(CollectQuery collectQuery) {
        QueryWrapper<Collect> queryWrapper = new QueryWrapper<>();
        //如果不使用params可以删除
        Map<String, Object> params = collectQuery.getParams();
        if (StringUtils.isNull(params)) {
            params = new HashMap<>();
        }
        Long id = collectQuery.getId();
        queryWrapper.eq(StringUtils.isNotNull(id), "id", id);

        String type = collectQuery.getType();
        queryWrapper.eq(StringUtils.isNotEmpty(type), "type", type);

        Date createTime = collectQuery.getCreateTime();
        queryWrapper.between(StringUtils.isNotNull(params.get("beginCreateTime")) && StringUtils.isNotNull(params.get("endCreateTime")), "create_time", params.get("beginCreateTime"), params.get("endCreateTime"));

        return queryWrapper;
    }

    @Override
    public List<CollectVo> convertVoList(List<Collect> collectList) {
        if (StringUtils.isEmpty(collectList)) {
            return Collections.emptyList();
        }
        return collectList.stream().map(CollectVo::objToVo).collect(Collectors.toList());
    }

}
