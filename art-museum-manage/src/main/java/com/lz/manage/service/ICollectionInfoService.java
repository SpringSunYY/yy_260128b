package com.lz.manage.service;

import java.util.List;
import com.lz.manage.model.domain.CollectionInfo;
import com.lz.manage.model.vo.collectionInfo.CollectionInfoDetailVo;
import com.lz.manage.model.vo.collectionInfo.CollectionInfoVo;
import com.lz.manage.model.dto.collectionInfo.CollectionInfoQuery;

import com.baomidou.mybatisplus.extension.service.IService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
/**
 * 藏品信息Service接口
 *
 * @author YY
 * @date 2026-02-09
 */
public interface ICollectionInfoService extends IService<CollectionInfo>
{
    //region mybatis代码
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
     * 批量删除藏品信息
     *
     * @param ids 需要删除的藏品信息主键集合
     * @return 结果
     */
    public int deleteCollectionInfoByIds(Long[] ids);

    /**
     * 删除藏品信息信息
     *
     * @param id 藏品信息主键
     * @return 结果
     */
    public int deleteCollectionInfoById(Long id);
    //endregion
    /**
     * 获取查询条件
     *
     * @param collectionInfoQuery 查询条件对象
     * @return 查询条件
     */
    QueryWrapper<CollectionInfo> getQueryWrapper(CollectionInfoQuery collectionInfoQuery);

    /**
     * 转换vo
     *
     * @param collectionInfoList CollectionInfo集合
     * @return CollectionInfoVO集合
     */
    List<CollectionInfoVo> convertVoList(List<CollectionInfo> collectionInfoList);

    /**
     * 获取藏品详细信息
     *
     * @param id 藏品信息主键
     * @return 藏品详细信息
     */
    CollectionInfoDetailVo selectCollectionInfoDetailById(Long id);
}
