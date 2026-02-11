package com.lz.manage.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.lz.common.annotation.DataScope;
import com.lz.common.core.domain.entity.SysUser;
import com.lz.common.utils.DateUtils;
import com.lz.common.utils.SecurityUtils;
import com.lz.common.utils.StringUtils;
import com.lz.common.utils.ThrowUtils;
import com.lz.manage.mapper.RechargeHistoryMapper;
import com.lz.manage.model.domain.RechargeHistory;
import com.lz.manage.model.domain.UserBalance;
import com.lz.manage.model.dto.rechargeHistory.RechargeHistoryQuery;
import com.lz.manage.model.enums.AuditStatusEnum;
import com.lz.manage.model.vo.rechargeHistory.RechargeHistoryVo;
import com.lz.manage.service.IRechargeHistoryService;
import com.lz.manage.service.IUserBalanceService;
import com.lz.system.service.ISysUserService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.*;
import java.util.stream.Collectors;

/**
 * 充值记录Service业务层处理
 *
 * @author YY
 * @date 2026-02-09
 */
@Service
public class RechargeHistoryServiceImpl extends ServiceImpl<RechargeHistoryMapper, RechargeHistory> implements IRechargeHistoryService {

    @Resource
    private RechargeHistoryMapper rechargeHistoryMapper;

    @Resource
    private ISysUserService sysUserService;


    @Resource
    private IUserBalanceService userBalanceService;
    //region mybatis代码

    /**
     * 查询充值记录
     *
     * @param id 充值记录主键
     * @return 充值记录
     */
    @Override
    public RechargeHistory selectRechargeHistoryById(Long id) {
        return rechargeHistoryMapper.selectRechargeHistoryById(id);
    }

    /**
     * 查询充值记录列表
     *
     * @param rechargeHistory 充值记录
     * @return 充值记录
     */
    @Override
    @DataScope(deptAlias = "tb_recharge_history", userAlias = "tb_recharge_history")
    public List<RechargeHistory> selectRechargeHistoryList(RechargeHistory rechargeHistory) {
        List<RechargeHistory> rechargeHistories = rechargeHistoryMapper.selectRechargeHistoryList(rechargeHistory);
        for (RechargeHistory info : rechargeHistories) {
            SysUser sysUser = sysUserService.selectUserById(info.getUserId());
            if (StringUtils.isNotNull(sysUser)) {
                info.setUserName(sysUser.getUserName());
            }
        }
        return rechargeHistories;
    }

    /**
     * 新增充值记录
     *
     * @param rechargeHistory 充值记录
     * @return 结果
     */
    @Override
    public int insertRechargeHistory(RechargeHistory rechargeHistory) {
        rechargeHistory.setAuditStatus(AuditStatusEnum.AUDIT_STATUS_1.getValue());
        rechargeHistory.setUserId(SecurityUtils.getUserId());
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
    public int updateRechargeHistory(RechargeHistory rechargeHistory) {
        //首先查询数据库中数据
        RechargeHistory rechargeHistoryOld = selectRechargeHistoryById(rechargeHistory.getId());
        ThrowUtils.throwIf(StringUtils.isNull(rechargeHistoryOld), "充值记录不存在");
        ThrowUtils.throwIf(rechargeHistoryOld.getAuditStatus().equals(AuditStatusEnum.AUDIT_STATUS_2.getValue()),
                "充值记录已经审核通过不可修改");
        rechargeHistory.setUpdateBy(SecurityUtils.getUsername());
        rechargeHistory.setUpdateTime(DateUtils.getNowDate());
        return rechargeHistoryMapper.updateRechargeHistory(rechargeHistory);
    }

    @Transactional
    @Override
    public int auditRechargeHistory(RechargeHistory rechargeHistory) {
        //首先查询数据库中数据
        RechargeHistory rechargeHistoryOld = selectRechargeHistoryById(rechargeHistory.getId());
        ThrowUtils.throwIf(StringUtils.isNull(rechargeHistoryOld), "充值记录不存在");
        ThrowUtils.throwIf(rechargeHistoryOld.getAuditStatus().equals(AuditStatusEnum.AUDIT_STATUS_2.getValue()),
                "充值记录已经审核通过不可修改");
        rechargeHistory.setAuditBy(SecurityUtils.getUsername());
        rechargeHistory.setAuditTime(DateUtils.getNowDate());
        //如果是同意
        if (rechargeHistory.getAuditStatus().equals(AuditStatusEnum.AUDIT_STATUS_2.getValue())) {
            //首先查询用户是否有余额
            UserBalance userBalance = userBalanceService.getOne(new LambdaQueryWrapper<UserBalance>().eq(UserBalance::getUserId, rechargeHistoryOld.getUserId()));
            if (StringUtils.isNull(userBalance)) {
                userBalance = new UserBalance();
                userBalance.setUserId(rechargeHistoryOld.getUserId());
                userBalance.setBalance(rechargeHistoryOld.getRechargePrice());
                userBalanceService.insertUserBalance(userBalance);
            } else {
                userBalance.setBalance(userBalance.getBalance().add(rechargeHistoryOld.getRechargePrice()));
                userBalanceService.updateUserBalance(userBalance);
            }
        }
        return rechargeHistoryMapper.updateRechargeHistory(rechargeHistory);
    }

    /**
     * 批量删除充值记录
     *
     * @param ids 需要删除的充值记录主键
     * @return 结果
     */
    @Override
    public int deleteRechargeHistoryByIds(Long[] ids) {
        return rechargeHistoryMapper.deleteRechargeHistoryByIds(ids);
    }

    /**
     * 删除充值记录信息
     *
     * @param id 充值记录主键
     * @return 结果
     */
    @Override
    public int deleteRechargeHistoryById(Long id) {
        return rechargeHistoryMapper.deleteRechargeHistoryById(id);
    }

    //endregion
    @Override
    public QueryWrapper<RechargeHistory> getQueryWrapper(RechargeHistoryQuery rechargeHistoryQuery) {
        QueryWrapper<RechargeHistory> queryWrapper = new QueryWrapper<>();
        //如果不使用params可以删除
        Map<String, Object> params = rechargeHistoryQuery.getParams();
        if (StringUtils.isNull(params)) {
            params = new HashMap<>();
        }
        Long id = rechargeHistoryQuery.getId();
        queryWrapper.eq(StringUtils.isNotNull(id), "id", id);

        Long userId = rechargeHistoryQuery.getUserId();
        queryWrapper.eq(StringUtils.isNotNull(userId), "user_id", userId);

        String auditStatus = rechargeHistoryQuery.getAuditStatus();
        queryWrapper.eq(StringUtils.isNotNull(auditStatus), "audit_status", auditStatus);

        String auditBy = rechargeHistoryQuery.getAuditBy();
        queryWrapper.like(StringUtils.isNotEmpty(auditBy), "audit_by", auditBy);

        Date auditTime = rechargeHistoryQuery.getAuditTime();
        queryWrapper.between(StringUtils.isNotNull(params.get("beginAuditTime")) && StringUtils.isNotNull(params.get("endAuditTime")), "audit_time", params.get("beginAuditTime"), params.get("endAuditTime"));

        Date createTime = rechargeHistoryQuery.getCreateTime();
        queryWrapper.between(StringUtils.isNotNull(params.get("beginCreateTime")) && StringUtils.isNotNull(params.get("endCreateTime")), "create_time", params.get("beginCreateTime"), params.get("endCreateTime"));

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
