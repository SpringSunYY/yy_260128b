package com.lz.manage.service;

import java.util.List;
import com.lz.manage.model.domain.CollectionMultimedia;
import com.lz.manage.model.vo.collectionMultimedia.CollectionMultimediaVo;
import com.lz.manage.model.dto.collectionMultimedia.CollectionMultimediaQuery;

import com.baomidou.mybatisplus.extension.service.IService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
/**
 * 藏品多媒体Service接口
 * 
 * @author YY
 * @date 2026-02-09
 */
public interface ICollectionMultimediaService extends IService<CollectionMultimedia>
{
    //region mybatis代码
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
     * 批量删除藏品多媒体
     * 
     * @param ids 需要删除的藏品多媒体主键集合
     * @return 结果
     */
    public int deleteCollectionMultimediaByIds(Long[] ids);

    /**
     * 删除藏品多媒体信息
     * 
     * @param id 藏品多媒体主键
     * @return 结果
     */
    public int deleteCollectionMultimediaById(Long id);
    //endregion
    /**
     * 获取查询条件
     *
     * @param collectionMultimediaQuery 查询条件对象
     * @return 查询条件
     */
    QueryWrapper<CollectionMultimedia> getQueryWrapper(CollectionMultimediaQuery collectionMultimediaQuery);

    /**
     * 转换vo
     *
     * @param collectionMultimediaList CollectionMultimedia集合
     * @return CollectionMultimediaVO集合
     */
    List<CollectionMultimediaVo> convertVoList(List<CollectionMultimedia> collectionMultimediaList);
}
