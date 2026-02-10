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
import com.lz.manage.mapper.NoticeInfoMapper;
import com.lz.manage.model.domain.NoticeInfo;
import com.lz.manage.service.INoticeInfoService;
import com.lz.manage.model.dto.noticeInfo.NoticeInfoQuery;
import com.lz.manage.model.vo.noticeInfo.NoticeInfoVo;

/**
 * 咨询信息Service业务层处理
 *
 * @author YY
 * @date 2026-02-10
 */
@Service
public class NoticeInfoServiceImpl extends ServiceImpl<NoticeInfoMapper, NoticeInfo> implements INoticeInfoService
{

    @Resource
    private NoticeInfoMapper noticeInfoMapper;

    //region mybatis代码
    /**
     * 查询咨询信息
     *
     * @param id 咨询信息主键
     * @return 咨询信息
     */
    @Override
    public NoticeInfo selectNoticeInfoById(Long id)
    {
        return noticeInfoMapper.selectNoticeInfoById(id);
    }

    /**
     * 查询咨询信息列表
     *
     * @param noticeInfo 咨询信息
     * @return 咨询信息
     */
    @Override
    public List<NoticeInfo> selectNoticeInfoList(NoticeInfo noticeInfo)
    {
        return noticeInfoMapper.selectNoticeInfoList(noticeInfo);
    }

    /**
     * 新增咨询信息
     *
     * @param noticeInfo 咨询信息
     * @return 结果
     */
    @Override
    public int insertNoticeInfo(NoticeInfo noticeInfo)
    {
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
    public int updateNoticeInfo(NoticeInfo noticeInfo)
    {
        noticeInfo.setUpdateTime(DateUtils.getNowDate());
        return noticeInfoMapper.updateNoticeInfo(noticeInfo);
    }

    /**
     * 批量删除咨询信息
     *
     * @param ids 需要删除的咨询信息主键
     * @return 结果
     */
    @Override
    public int deleteNoticeInfoByIds(Long[] ids)
    {
        return noticeInfoMapper.deleteNoticeInfoByIds(ids);
    }

    /**
     * 删除咨询信息信息
     *
     * @param id 咨询信息主键
     * @return 结果
     */
    @Override
    public int deleteNoticeInfoById(Long id)
    {
        return noticeInfoMapper.deleteNoticeInfoById(id);
    }
    //endregion
    @Override
    public QueryWrapper<NoticeInfo> getQueryWrapper(NoticeInfoQuery noticeInfoQuery){
        QueryWrapper<NoticeInfo> queryWrapper = new QueryWrapper<>();
        //如果不使用params可以删除
        Map<String, Object> params = noticeInfoQuery.getParams();
        if (StringUtils.isNull(params)) {
            params = new HashMap<>();
        }
        Long id = noticeInfoQuery.getId();
        queryWrapper.eq( StringUtils.isNotNull(id),"id",id);

        String title = noticeInfoQuery.getTitle();
        queryWrapper.like(StringUtils.isNotEmpty(title) ,"title",title);

        String type = noticeInfoQuery.getType();
        queryWrapper.eq(StringUtils.isNotEmpty(type) ,"type",type);

        String status = noticeInfoQuery.getStatus();
        queryWrapper.eq(StringUtils.isNotEmpty(status) ,"status",status);

        String collectionIds = noticeInfoQuery.getCollectionIds();
        queryWrapper.eq(StringUtils.isNotEmpty(collectionIds) ,"collection_ids",collectionIds);

        String updateBy = noticeInfoQuery.getUpdateBy();
        queryWrapper.between(StringUtils.isNotNull(params.get("beginUpdateBy"))&&StringUtils.isNotNull(params.get("endUpdateBy")),"update_by",params.get("beginUpdateBy"),params.get("endUpdateBy"));

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
