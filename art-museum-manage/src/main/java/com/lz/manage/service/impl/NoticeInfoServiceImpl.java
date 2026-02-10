package com.lz.manage.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.lz.common.core.domain.entity.SysUser;
import com.lz.common.utils.DateUtils;
import com.lz.common.utils.SecurityUtils;
import com.lz.common.utils.StringUtils;
import com.lz.common.utils.ThrowUtils;
import com.lz.manage.mapper.CollectMapper;
import com.lz.manage.mapper.NoticeInfoMapper;
import com.lz.manage.model.domain.Collect;
import com.lz.manage.model.domain.CollectionInfo;
import com.lz.manage.model.domain.NoticeInfo;
import com.lz.manage.model.dto.noticeInfo.NoticeInfoQuery;
import com.lz.manage.model.enums.CollectTypeEnum;
import com.lz.manage.model.vo.noticeInfo.NoticeInfoVo;
import com.lz.manage.service.ICollectionInfoService;
import com.lz.manage.service.INoticeInfoService;
import com.lz.system.service.ISysUserService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.*;
import java.util.stream.Collectors;

/**
 * 咨询信息Service业务层处理
 *
 * @author YY
 * @date 2026-02-10
 */
@Service
public class NoticeInfoServiceImpl extends ServiceImpl<NoticeInfoMapper, NoticeInfo> implements INoticeInfoService {

    @Resource
    private NoticeInfoMapper noticeInfoMapper;

    @Resource
    private ICollectionInfoService collectionInfoService;

    @Resource
    private ISysUserService sysUserService;

    @Resource
    private CollectMapper collectMapper;

    //region mybatis代码

    /**
     * 查询咨询信息
     *
     * @param id 咨询信息主键
     * @return 咨询信息
     */
    @Override
    public NoticeInfo selectNoticeInfoById(Long id) {
        return noticeInfoMapper.selectNoticeInfoById(id);
    }

    @Override
    public NoticeInfoVo selectNoticeInfoDetailById(Long id) {
        NoticeInfo noticeInfo = this.selectNoticeInfoById(id);
        ThrowUtils.throwIf(StringUtils.isNull(noticeInfo), "资讯信息不存在");
        initData(noticeInfo);
        NoticeInfoVo noticeInfoVo = NoticeInfoVo.objToVo(noticeInfo);
        //查询是否收藏
        Collect collect = new Collect();
        collect.setUserId(SecurityUtils.getUserId());
        collect.setType(CollectTypeEnum.COLLECT_TYPE_1.getValue());
        collect.setTargetId(id);
        List<Collect> collects = collectMapper.selectCollectList(collect);
        noticeInfoVo.setIsCollect(StringUtils.isNotEmpty(collects));
        return noticeInfoVo;
    }

    private void initData(NoticeInfo noticeInfo) {
        SysUser sysUser = sysUserService.selectUserById(noticeInfo.getUserId());
        if (StringUtils.isNotNull(sysUser)) {
            noticeInfo.setUserName(sysUser.getUserName());
        }
        if (StringUtils.isNotEmpty(noticeInfo.getCollectionIds())) {
            Set<String> collectionIds = StringUtils.str2Set(noticeInfo.getCollectionIds(), ",");
            List<CollectionInfo> list = collectionInfoService.list(new LambdaQueryWrapper<CollectionInfo>()
                    .in(CollectionInfo::getId, collectionIds));
            noticeInfo.setCollectionNames(list.stream().map(CollectionInfo::getName).map(String::valueOf).collect(Collectors.joining(",")));
        }
    }

    /**
     * 查询咨询信息列表
     *
     * @param noticeInfo 咨询信息
     * @return 咨询信息
     */
    @Override
    public List<NoticeInfo> selectNoticeInfoList(NoticeInfo noticeInfo) {
        List<NoticeInfo> noticeInfos = noticeInfoMapper.selectNoticeInfoList(noticeInfo);
        for (NoticeInfo info : noticeInfos) {
            initData(info);
        }
        return noticeInfos;
    }

    /**
     * 新增咨询信息
     *
     * @param noticeInfo 咨询信息
     * @return 结果
     */
    @Override
    public int insertNoticeInfo(NoticeInfo noticeInfo) {
        noticeInfo.setCollectNumber(0L);
        //根据逗号分割in查询
        initNoticeCollection(noticeInfo);
        noticeInfo.setUserId(SecurityUtils.getUserId());
        noticeInfo.setCreateTime(DateUtils.getNowDate());
        return noticeInfoMapper.insertNoticeInfo(noticeInfo);
    }

    /**
     * 修改咨询信息
     *
     * @param noticeInfo 咨询信息
     * @return 结果
     */
    @Override
    public int updateNoticeInfo(NoticeInfo noticeInfo) {
        //根据逗号分割in查询
        initNoticeCollection(noticeInfo);
        noticeInfo.setUpdateBy(SecurityUtils.getUsername());
        noticeInfo.setUpdateTime(DateUtils.getNowDate());
        return noticeInfoMapper.updateNoticeInfo(noticeInfo);
    }

    private void initNoticeCollection(NoticeInfo noticeInfo) {
        if (StringUtils.isNotEmpty(noticeInfo.getCollectionIds())) {
            Set<String> collectionIds = StringUtils.str2Set(noticeInfo.getCollectionIds(), ",");
            List<CollectionInfo> list = collectionInfoService.list(new LambdaQueryWrapper<CollectionInfo>()
                    .in(CollectionInfo::getId, collectionIds));
            noticeInfo.setCollectionIds(list.stream().map(CollectionInfo::getId).map(String::valueOf).collect(Collectors.joining(",")));
        }
    }

    /**
     * 批量删除咨询信息
     *
     * @param ids 需要删除的咨询信息主键
     * @return 结果
     */
    @Override
    public int deleteNoticeInfoByIds(Long[] ids) {
        return noticeInfoMapper.deleteNoticeInfoByIds(ids);
    }

    /**
     * 删除咨询信息信息
     *
     * @param id 咨询信息主键
     * @return 结果
     */
    @Override
    public int deleteNoticeInfoById(Long id) {
        return noticeInfoMapper.deleteNoticeInfoById(id);
    }

    //endregion
    @Override
    public QueryWrapper<NoticeInfo> getQueryWrapper(NoticeInfoQuery noticeInfoQuery) {
        QueryWrapper<NoticeInfo> queryWrapper = new QueryWrapper<>();
        //如果不使用params可以删除
        Map<String, Object> params = noticeInfoQuery.getParams();
        if (StringUtils.isNull(params)) {
            params = new HashMap<>();
        }
        Long id = noticeInfoQuery.getId();
        queryWrapper.eq(StringUtils.isNotNull(id), "id", id);

        String title = noticeInfoQuery.getTitle();
        queryWrapper.like(StringUtils.isNotEmpty(title), "title", title);

        String type = noticeInfoQuery.getType();
        queryWrapper.eq(StringUtils.isNotEmpty(type), "type", type);

        String status = noticeInfoQuery.getStatus();
        queryWrapper.eq(StringUtils.isNotEmpty(status), "status", status);

        String collectionIds = noticeInfoQuery.getCollectionIds();
        queryWrapper.eq(StringUtils.isNotEmpty(collectionIds), "collection_ids", collectionIds);

        String updateBy = noticeInfoQuery.getUpdateBy();
        queryWrapper.between(StringUtils.isNotNull(params.get("beginUpdateBy")) && StringUtils.isNotNull(params.get("endUpdateBy")), "update_by", params.get("beginUpdateBy"), params.get("endUpdateBy"));

        return queryWrapper;
    }

    @Override
    public List<NoticeInfoVo> convertVoList(List<NoticeInfo> noticeInfoList) {
        if (StringUtils.isEmpty(noticeInfoList)) {
            return Collections.emptyList();
        }
        return noticeInfoList.stream().map(NoticeInfoVo::objToVo).collect(Collectors.toList());
    }

}
