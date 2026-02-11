package com.lz.manage.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.IService;
import com.lz.manage.model.domain.AfterSales;
import com.lz.manage.model.dto.afterSales.AfterSalesQuery;
import com.lz.manage.model.vo.afterSales.AfterSalesVo;

import java.util.List;

/**
 * 售后信息Service接口
 *
 * @author YY
 * @date 2026-02-09
 */
public interface IAfterSalesService extends IService<AfterSales> {
    //region mybatis代码

    /**
     * 查询售后信息
     *
     * @param id 售后信息主键
     * @return 售后信息
     */
    public AfterSales selectAfterSalesById(Long id);

    /**
     * 查询售后信息列表
     *
     * @param afterSales 售后信息
     * @return 售后信息集合
     */
    public List<AfterSales> selectAfterSalesList(AfterSales afterSales);

    /**
     * 新增售后信息
     *
     * @param afterSales 售后信息
     * @return 结果
     */
    public int insertAfterSales(AfterSales afterSales);

    /**
     * 审核售后信息
     *
     * @param afterSales 售后信息
     * @return 售后信息
     */
    int auditAfterSales(AfterSales afterSales);

    /**
     * 修改售后信息
     *
     * @param afterSales 售后信息
     * @return 结果
     */
    public int updateAfterSales(AfterSales afterSales);

    /**
     * 批量删除售后信息
     *
     * @param ids 需要删除的售后信息主键集合
     * @return 结果
     */
    public int deleteAfterSalesByIds(Long[] ids);

    /**
     * 删除售后信息信息
     *
     * @param id 售后信息主键
     * @return 结果
     */
    public int deleteAfterSalesById(Long id);
    //endregion

    /**
     * 获取查询条件
     *
     * @param afterSalesQuery 查询条件对象
     * @return 查询条件
     */
    QueryWrapper<AfterSales> getQueryWrapper(AfterSalesQuery afterSalesQuery);

    /**
     * 转换vo
     *
     * @param afterSalesList AfterSales集合
     * @return AfterSalesVO集合
     */
    List<AfterSalesVo> convertVoList(List<AfterSales> afterSalesList);
}
