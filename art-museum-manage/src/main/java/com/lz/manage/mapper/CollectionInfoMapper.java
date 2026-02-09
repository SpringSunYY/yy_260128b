package com.lz.manage.mapper;

import java.util.List;
import com.lz.manage.model.domain.CollectionInfo;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

/**
 * 藏品信息Mapper接口
 * 
 * @author YY
 * @date 2026-02-09
 */
public interface CollectionInfoMapper extends BaseMapper<CollectionInfo>
{
    /**
     * 查询藏品信息
     * 
     * @param id 藏品信息主键
     * @return 藏品信息
     */
    public CollectionInfo selectCollectionInfoById(Long id);

    /**
     * 查询藏品信息列表
     * 
     * @param collectionInfo 藏品信息
     * @return 藏品信息集合
     */
    public List<CollectionInfo> selectCollectionInfoList(CollectionInfo collectionInfo);

    /**
     * 新增藏品信息
     * 
     * @param collectionInfo 藏品信息
     * @return 结果
     */
    public int insertCollectionInfo(CollectionInfo collectionInfo);

    /**
     * 修改藏品信息
     * 
     * @param collectionInfo 藏品信息
     * @return 结果
     */
    public int updateCollectionInfo(CollectionInfo collectionInfo);

    /**
     * 删除藏品信息
     * 
     * @param id 藏品信息主键
     * @return 结果
     */
    public int deleteCollectionInfoById(Long id);

    /**
     * 批量删除藏品信息
     * 
     * @param ids 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteCollectionInfoByIds(Long[] ids);
}
