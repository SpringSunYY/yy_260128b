package com.lz.manage.mapper;

import java.util.List;
import com.lz.manage.model.domain.AfterSales;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

/**
 * 售后信息Mapper接口
 * 
 * @author YY
 * @date 2026-02-09
 */
public interface AfterSalesMapper extends BaseMapper<AfterSales>
{
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
     * 修改售后信息
     * 
     * @param afterSales 售后信息
     * @return 结果
     */
    public int updateAfterSales(AfterSales afterSales);

    /**
     * 删除售后信息
     * 
     * @param id 售后信息主键
     * @return 结果
     */
    public int deleteAfterSalesById(Long id);

    /**
     * 批量删除售后信息
     * 
     * @param ids 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteAfterSalesByIds(Long[] ids);
}
