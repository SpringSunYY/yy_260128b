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
import com.lz.manage.mapper.RechargeHistoryMapper;
import com.lz.manage.model.domain.RechargeHistory;
import com.lz.manage.service.IRechargeHistoryService;
import com.lz.manage.model.dto.rechargeHistory.RechargeHistoryQuery;
import com.lz.manage.model.vo.rechargeHistory.RechargeHistoryVo;

/**
 * 充值记录Service业务层处理
 *
 * @author YY
 * @date 2026-02-09
 */
@Service
public class RechargeHistoryServiceImpl extends ServiceImpl<RechargeHistoryMapper, RechargeHistory> implements IRechargeHistoryService
{

    @Resource
    private RechargeHistoryMapper rechargeHistoryMapper;

    //region mybatis代码
    /**
     * 查询充值记录
     *
     * @param id 充值记录主键
     * @return 充值记录
     */
    @Override
    public RechargeHistory selectRechargeHistoryById(Long id)
    {
        return rechargeHistoryMapper.selectRechargeHistoryById(id);
    }

    /**
     * 查询充值记录列表
     *
     * @param rechargeHistory 充值记录
     * @return 充值记录
     */
    @Override
    public List<RechargeHistory> selectRechargeHistoryList(RechargeHistory rechargeHistory)
    {
        return rechargeHistoryMapper.selectRechargeHistoryList(rechargeHistory);
    }

    /**
     * 新增充值记录
     *
     * @param rechargeHistory 充值记录
     * @return 结果
     */
    @Override
    public int insertRechargeHistory(RechargeHistory rechargeHistory)
    {
        rechargeHistory.setCreateTime(DateUtils.getNowDate());
        return rechargeHistoryMapper.insertRechargeHistory(rechargeHistory);
    }

    /**
     * 修改充值记录
     *
     * @param rechargeHistory 充值记录
     * @return 结果
     */
    @Override
    public int updateRechargeHistory(RechargeHistory rechargeHistory)
    {
        rechargeHistory.setUpdateTime(DateUtils.getNowDate());
        return rechargeHistoryMapper.updateRechargeHistory(rechargeHistory);
    }

    /**
     * 批量删除充值记录
     *
     * @param ids 需要删除的充值记录主键
     * @return 结果
     */
    @Override
    public int deleteRechargeHistoryByIds(Long[] ids)
    {
        return rechargeHistoryMapper.deleteRechargeHistoryByIds(ids);
    }

    /**
     * 删除充值记录信息
     *
     * @param id 充值记录主键
     * @return 结果
     */
    @Override
    public int deleteRechargeHistoryById(Long id)
    {
        return rechargeHistoryMapper.deleteRechargeHistoryById(id);
    }
    //endregion
    @Override
    public QueryWrapper<RechargeHistory> getQueryWrapper(RechargeHistoryQuery rechargeHistoryQuery){
        QueryWrapper<RechargeHistory> queryWrapper = new QueryWrapper<>();
        //如果不使用params可以删除
        Map<String, Object> params = rechargeHistoryQuery.getParams();
        if (StringUtils.isNull(params)) {
            params = new HashMap<>();
        }
        Long id = rechargeHistoryQuery.getId();
        queryWrapper.eq( StringUtils.isNotNull(id),"id",id);

        Long userId = rechargeHistoryQuery.getUserId();
        queryWrapper.eq( StringUtils.isNotNull(userId),"user_id",userId);

        Long auditStatus = rechargeHistoryQuery.getAuditStatus();
        queryWrapper.eq( StringUtils.isNotNull(auditStatus),"audit_status",auditStatus);

        String auditBy = rechargeHistoryQuery.getAuditBy();
        queryWrapper.like(StringUtils.isNotEmpty(auditBy) ,"audit_by",auditBy);

        Date auditTime = rechargeHistoryQuery.getAuditTime();
        queryWrapper.between(StringUtils.isNotNull(params.get("beginAuditTime"))&&StringUtils.isNotNull(params.get("endAuditTime")),"audit_time",params.get("beginAuditTime"),params.get("endAuditTime"));

        Date createTime = rechargeHistoryQuery.getCreateTime();
        queryWrapper.between(StringUtils.isNotNull(params.get("beginCreateTime"))&&StringUtils.isNotNull(params.get("endCreateTime")),"create_time",params.get("beginCreateTime"),params.get("endCreateTime"));

        return queryWrapper;
    }

    @Override
    public List<RechargeHistoryVo> convertVoList(List<RechargeHistory> rechargeHistoryList) {
        if (StringUtils.isEmpty(rechargeHistoryList)) {
            return Collections.emptyList();
        }
        return rechargeHistoryList.stream().map(RechargeHistoryVo::objToVo).collect(Collectors.toList());
    }

}
