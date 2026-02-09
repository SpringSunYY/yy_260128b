package com.lz.manage.mapper;

import java.util.List;
import com.lz.manage.model.domain.RechargeHistory;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

/**
 * 充值记录Mapper接口
 * 
 * @author YY
 * @date 2026-02-09
 */
public interface RechargeHistoryMapper extends BaseMapper<RechargeHistory>
{
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
     * 删除充值记录
     * 
     * @param id 充值记录主键
     * @return 结果
     */
    public int deleteRechargeHistoryById(Long id);

    /**
     * 批量删除充值记录
     * 
     * @param ids 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteRechargeHistoryByIds(Long[] ids);
}
