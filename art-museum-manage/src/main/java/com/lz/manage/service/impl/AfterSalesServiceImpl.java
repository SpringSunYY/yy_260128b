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
import com.lz.manage.mapper.AfterSalesMapper;
import com.lz.manage.model.domain.AfterSales;
import com.lz.manage.service.IAfterSalesService;
import com.lz.manage.model.dto.afterSales.AfterSalesQuery;
import com.lz.manage.model.vo.afterSales.AfterSalesVo;

/**
 * 售后信息Service业务层处理
 *
 * @author YY
 * @date 2026-02-09
 */
@Service
public class AfterSalesServiceImpl extends ServiceImpl<AfterSalesMapper, AfterSales> implements IAfterSalesService
{

    @Resource
    private AfterSalesMapper afterSalesMapper;

    //region mybatis代码
    /**
     * 查询售后信息
     *
     * @param id 售后信息主键
     * @return 售后信息
     */
    @Override
    public AfterSales selectAfterSalesById(Long id)
    {
        return afterSalesMapper.selectAfterSalesById(id);
    }

    /**
     * 查询售后信息列表
     *
     * @param afterSales 售后信息
     * @return 售后信息
     */
    @Override
    public List<AfterSales> selectAfterSalesList(AfterSales afterSales)
    {
        return afterSalesMapper.selectAfterSalesList(afterSales);
    }

    /**
     * 新增售后信息
     *
     * @param afterSales 售后信息
     * @return 结果
     */
    @Override
    public int insertAfterSales(AfterSales afterSales)
    {
        afterSales.setCreateTime(DateUtils.getNowDate());
        return afterSalesMapper.insertAfterSales(afterSales);
    }

    /**
     * 修改售后信息
     *
     * @param afterSales 售后信息
     * @return 结果
     */
    @Override
    public int updateAfterSales(AfterSales afterSales)
    {
        afterSales.setUpdateTime(DateUtils.getNowDate());
        return afterSalesMapper.updateAfterSales(afterSales);
    }

    /**
     * 批量删除售后信息
     *
     * @param ids 需要删除的售后信息主键
     * @return 结果
     */
    @Override
    public int deleteAfterSalesByIds(Long[] ids)
    {
        return afterSalesMapper.deleteAfterSalesByIds(ids);
    }

    /**
     * 删除售后信息信息
     *
     * @param id 售后信息主键
     * @return 结果
     */
    @Override
    public int deleteAfterSalesById(Long id)
    {
        return afterSalesMapper.deleteAfterSalesById(id);
    }
    //endregion
    @Override
    public QueryWrapper<AfterSales> getQueryWrapper(AfterSalesQuery afterSalesQuery){
        QueryWrapper<AfterSales> queryWrapper = new QueryWrapper<>();
        //如果不使用params可以删除
        Map<String, Object> params = afterSalesQuery.getParams();
        if (StringUtils.isNull(params)) {
            params = new HashMap<>();
        }
        Long id = afterSalesQuery.getId();
        queryWrapper.eq( StringUtils.isNotNull(id),"id",id);

        String type = afterSalesQuery.getType();
        queryWrapper.eq(StringUtils.isNotEmpty(type) ,"type",type);

        String auditStatus = afterSalesQuery.getAuditStatus();
        queryWrapper.eq( StringUtils.isNotNull(auditStatus),"audit_status",auditStatus);

        String auditBy = afterSalesQuery.getAuditBy();
        queryWrapper.like(StringUtils.isNotEmpty(auditBy) ,"audit_by",auditBy);

        Date auditTime = afterSalesQuery.getAuditTime();
        queryWrapper.between(StringUtils.isNotNull(params.get("beginAuditTime"))&&StringUtils.isNotNull(params.get("endAuditTime")),"audit_time",params.get("beginAuditTime"),params.get("endAuditTime"));

        Date createTime = afterSalesQuery.getCreateTime();
        queryWrapper.between(StringUtils.isNotNull(params.get("beginCreateTime"))&&StringUtils.isNotNull(params.get("endCreateTime")),"create_time",params.get("beginCreateTime"),params.get("endCreateTime"));

        String updateBy = afterSalesQuery.getUpdateBy();
        queryWrapper.like(StringUtils.isNotEmpty(updateBy) ,"update_by",updateBy);

        return queryWrapper;
    }

    @Override
    public List<AfterSalesVo> convertVoList(List<AfterSales> afterSalesList) {
        if (StringUtils.isEmpty(afterSalesList)) {
            return Collections.emptyList();
        }
        return afterSalesList.stream().map(AfterSalesVo::objToVo).collect(Collectors.toList());
    }

}
