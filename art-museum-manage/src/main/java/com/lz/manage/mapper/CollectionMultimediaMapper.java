package com.lz.manage.mapper;

import java.util.List;
import com.lz.manage.model.domain.CollectionMultimedia;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

/**
 * 藏品多媒体Mapper接口
 * 
 * @author YY
 * @date 2026-02-09
 */
public interface CollectionMultimediaMapper extends BaseMapper<CollectionMultimedia>
{
    /**
     * 查询藏品多媒体
     * 
     * @param id 藏品多媒体主键
     * @return 藏品多媒体
     */
    public CollectionMultimedia selectCollectionMultimediaById(Long id);

    /**
     * 查询藏品多媒体列表
     * 
     * @param collectionMultimedia 藏品多媒体
     * @return 藏品多媒体集合
     */
    public List<CollectionMultimedia> selectCollectionMultimediaList(CollectionMultimedia collectionMultimedia);

    /**
     * 新增藏品多媒体
     * 
     * @param collectionMultimedia 藏品多媒体
     * @return 结果
     */
    public int insertCollectionMultimedia(CollectionMultimedia collectionMultimedia);

    /**
     * 修改藏品多媒体
     * 
     * @param collectionMultimedia 藏品多媒体
     * @return 结果
     */
    public int updateCollectionMultimedia(CollectionMultimedia collectionMultimedia);

    /**
     * 删除藏品多媒体
     * 
     * @param id 藏品多媒体主键
     * @return 结果
     */
    public int deleteCollectionMultimediaById(Long id);

    /**
     * 批量删除藏品多媒体
     * 
     * @param ids 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteCollectionMultimediaByIds(Long[] ids);
}
