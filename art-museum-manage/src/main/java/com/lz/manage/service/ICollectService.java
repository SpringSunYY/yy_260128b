package com.lz.manage.service;

import java.util.List;
import com.lz.manage.model.domain.Collect;
import com.lz.manage.model.vo.collect.CollectVo;
import com.lz.manage.model.dto.collect.CollectQuery;

import com.baomidou.mybatisplus.extension.service.IService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
/**
 * 收藏信息Service接口
 * 
 * @author YY
 * @date 2026-02-09
 */
public interface ICollectService extends IService<Collect>
{
    //region mybatis代码
    /**
     * 查询收藏信息
     * 
     * @param id 收藏信息主键
     * @return 收藏信息
     */
    public Collect selectCollectById(Long id);

    /**
     * 查询收藏信息列表
     * 
     * @param collect 收藏信息
     * @return 收藏信息集合
     */
    public List<Collect> selectCollectList(Collect collect);

    /**
     * 新增收藏信息
     * 
     * @param collect 收藏信息
     * @return 结果
     */
    public int insertCollect(Collect collect);

    /**
     * 修改收藏信息
     * 
     * @param collect 收藏信息
     * @return 结果
     */
    public int updateCollect(Collect collect);

    /**
     * 批量删除收藏信息
     * 
     * @param ids 需要删除的收藏信息主键集合
     * @return 结果
     */
    public int deleteCollectByIds(Long[] ids);

    /**
     * 删除收藏信息信息
     * 
     * @param id 收藏信息主键
     * @return 结果
     */
    public int deleteCollectById(Long id);
    //endregion
    /**
     * 获取查询条件
     *
     * @param collectQuery 查询条件对象
     * @return 查询条件
     */
    QueryWrapper<Collect> getQueryWrapper(CollectQuery collectQuery);

    /**
     * 转换vo
     *
     * @param collectList Collect集合
     * @return CollectVO集合
     */
    List<CollectVo> convertVoList(List<Collect> collectList);
}
