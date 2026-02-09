package com.lz.manage.service;

import java.util.List;
import com.lz.manage.model.domain.RechargeHistory;
import com.lz.manage.model.vo.rechargeHistory.RechargeHistoryVo;
import com.lz.manage.model.dto.rechargeHistory.RechargeHistoryQuery;

import com.baomidou.mybatisplus.extension.service.IService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
/**
 * 充值记录Service接口
 * 
 * @author YY
 * @date 2026-02-09
 */
public interface IRechargeHistoryService extends IService<RechargeHistory>
{
    //region mybatis代码
    /**
     * 查询充值记录
     * 
     * @param id 充值记录主键
     * @return 充值记录
     */
    public RechargeHistory selectRechargeHistoryById(Long id);

    /**
     * 查询充值记录列表
     * 
     * @param rechargeHistory 充值记录
     * @return 充值记录集合
     */
    public List<RechargeHistory> selectRechargeHistoryList(RechargeHistory rechargeHistory);

    /**
     * 新增充值记录
     * 
     * @param rechargeHistory 充值记录
     * @return 结果
     */
    public int insertRechargeHistory(RechargeHistory rechargeHistory);

    /**
     * 修改充值记录
     * 
     * @param rechargeHistory 充值记录
     * @return 结果
     */
    public int updateRechargeHistory(RechargeHistory rechargeHistory);

    /**
     * 批量删除充值记录
     * 
     * @param ids 需要删除的充值记录主键集合
     * @return 结果
     */
    public int deleteRechargeHistoryByIds(Long[] ids);

    /**
     * 删除充值记录信息
     * 
     * @param id 充值记录主键
     * @return 结果
     */
    public int deleteRechargeHistoryById(Long id);
    //endregion
    /**
     * 获取查询条件
     *
     * @param rechargeHistoryQuery 查询条件对象
     * @return 查询条件
     */
    QueryWrapper<RechargeHistory> getQueryWrapper(RechargeHistoryQuery rechargeHistoryQuery);

    /**
     * 转换vo
     *
     * @param rechargeHistoryList RechargeHistory集合
     * @return RechargeHistoryVO集合
     */
    List<RechargeHistoryVo> convertVoList(List<RechargeHistory> rechargeHistoryList);
}
