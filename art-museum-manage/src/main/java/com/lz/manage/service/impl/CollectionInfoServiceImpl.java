package com.lz.manage.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.lz.common.core.domain.entity.SysUser;
import com.lz.common.utils.DateUtils;
import com.lz.common.utils.SecurityUtils;
import com.lz.common.utils.StringUtils;
import com.lz.common.utils.ThrowUtils;
import com.lz.manage.mapper.CollectMapper;
import com.lz.manage.mapper.CollectionInfoMapper;
import com.lz.manage.mapper.CollectionMultimediaMapper;
import com.lz.manage.mapper.GoodsMapper;
import com.lz.manage.model.domain.*;
import com.lz.manage.model.dto.collectionInfo.CollectionInfoQuery;
import com.lz.manage.model.enums.CollectTypeEnum;
import com.lz.manage.model.enums.CollectionStatusEnum;
import com.lz.manage.model.enums.GoodsStatusEnum;
import com.lz.manage.model.vo.collectionInfo.CollectionInfoDetailVo;
import com.lz.manage.model.vo.collectionInfo.CollectionInfoVo;
import com.lz.manage.service.ICategoryService;
import com.lz.manage.service.ICollectionInfoService;
import com.lz.system.service.ISysUserService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.*;
import java.util.stream.Collectors;

/**
 * 藏品信息Service业务层处理
 *
 * @author YY
 * @date 2026-02-09
 */
@Service
public class CollectionInfoServiceImpl extends ServiceImpl<CollectionInfoMapper, CollectionInfo> implements ICollectionInfoService {

    @Resource
    private CollectionInfoMapper collectionInfoMapper;

    @Resource
    private ISysUserService sysUserService;

    @Resource
    private ICategoryService categoryService;

    @Resource
    private CollectMapper collectMapper;

    @Resource
    private GoodsMapper goodsMapper;

    @Resource
    private CollectionMultimediaMapper collectionMultimediaMapper;

    //region mybatis代码

    /**
     * 查询藏品信息
     *
     * @param id 藏品信息主键
     * @return 藏品信息
     */
    @Override
    public CollectionInfo selectCollectionInfoById(Long id) {
        return collectionInfoMapper.selectCollectionInfoById(id);
    }

    /**
     * 查询藏品信息列表
     *
     * @param collectionInfo 藏品信息
     * @return 藏品信息
     */
    @Override
    public List<CollectionInfo> selectCollectionInfoList(CollectionInfo collectionInfo) {
        List<CollectionInfo> collectionInfos = collectionInfoMapper.selectCollectionInfoList(collectionInfo);
        for (CollectionInfo info : collectionInfos) {
            SysUser sysUser = sysUserService.selectUserById(info.getUserId());
            if (StringUtils.isNotNull(sysUser)) {
                info.setUserName(sysUser.getUserName());
            }
            Category category = categoryService.selectCategoryById(info.getCategoryId());
            if (StringUtils.isNotNull(category)) {
                info.setCategoryName(category.getName());
            }
        }
        return collectionInfos;
    }

    /**
     * 新增藏品信息
     *
     * @param collectionInfo 藏品信息
     * @return 结果
     */
    @Override
    public int insertCollectionInfo(CollectionInfo collectionInfo) {
        //查询分类是否存在
        Category category = categoryService.selectCategoryById(collectionInfo.getCategoryId());
        ThrowUtils.throwIf(StringUtils.isNull(category), "分类不存在");
        collectionInfo.setCollectNumber(0L);
        collectionInfo.setUserId(SecurityUtils.getUserId());
        collectionInfo.setCreateTime(DateUtils.getNowDate());
        return collectionInfoMapper.insertCollectionInfo(collectionInfo);
    }

    /**
     * 修改藏品信息
     *
     * @param collectionInfo 藏品信息
     * @return 结果
     */
    @Override
    public int updateCollectionInfo(CollectionInfo collectionInfo) {
        //查询分类是否存在
        Category category = categoryService.selectCategoryById(collectionInfo.getCategoryId());
        ThrowUtils.throwIf(StringUtils.isNull(category), "分类不存在");
        collectionInfo.setUpdateBy(SecurityUtils.getUsername());
        collectionInfo.setUpdateTime(DateUtils.getNowDate());
        return collectionInfoMapper.updateCollectionInfo(collectionInfo);
    }

    /**
     * 批量删除藏品信息
     *
     * @param ids 需要删除的藏品信息主键
     * @return 结果
     */
    @Override
    public int deleteCollectionInfoByIds(Long[] ids) {
        return collectionInfoMapper.deleteCollectionInfoByIds(ids);
    }

    /**
     * 删除藏品信息信息
     *
     * @param id 藏品信息主键
     * @return 结果
     */
    @Override
    public int deleteCollectionInfoById(Long id) {
        return collectionInfoMapper.deleteCollectionInfoById(id);
    }

    //endregion
    @Override
    public QueryWrapper<CollectionInfo> getQueryWrapper(CollectionInfoQuery collectionInfoQuery) {
        QueryWrapper<CollectionInfo> queryWrapper = new QueryWrapper<>();
        //如果不使用params可以删除
        Map<String, Object> params = collectionInfoQuery.getParams();
        if (StringUtils.isNull(params)) {
            params = new HashMap<>();
        }
        Long id = collectionInfoQuery.getId();
        queryWrapper.eq(StringUtils.isNotNull(id), "id", id);

        String name = collectionInfoQuery.getName();
        queryWrapper.like(StringUtils.isNotEmpty(name), "name", name);

        String status = collectionInfoQuery.getStatus();
        queryWrapper.eq(StringUtils.isNotEmpty(status), "status", status);

        String sortType = collectionInfoQuery.getSortType();
        queryWrapper.eq(StringUtils.isNotEmpty(sortType), "sort_type", sortType);

        String author = collectionInfoQuery.getAuthor();
        queryWrapper.like(StringUtils.isNotEmpty(author), "author", author);

        String era = collectionInfoQuery.getEra();
        queryWrapper.like(StringUtils.isNotEmpty(era), "era", era);

        Date createTime = collectionInfoQuery.getCreateTime();
        queryWrapper.between(StringUtils.isNotNull(params.get("beginCreateTime")) && StringUtils.isNotNull(params.get("endCreateTime")), "create_time", params.get("beginCreateTime"), params.get("endCreateTime"));

        return queryWrapper;
    }

    @Override
    public List<CollectionInfoVo> convertVoList(List<CollectionInfo> collectionInfoList) {
        if (StringUtils.isEmpty(collectionInfoList)) {
            return Collections.emptyList();
        }
        return collectionInfoList.stream().map(CollectionInfoVo::objToVo).collect(Collectors.toList());
    }

    @Override
    public CollectionInfoDetailVo selectCollectionInfoDetailById(Long id) {
        //查询藏品信息
        CollectionInfo collectionInfo = collectionInfoMapper.selectCollectionInfoById(id);
        ThrowUtils.throwIf(StringUtils.isNull(collectionInfo), "藏品信息不存在");
        //如果不是正常的
        ThrowUtils.throwIf(!CollectionStatusEnum.COLLECTION_STATUS_1.getValue().equals(collectionInfo.getStatus()), "藏品信息当前不可见");
        CollectionInfoDetailVo collectionInfoDetailVo = CollectionInfoDetailVo.objToVo(collectionInfo);
        //查询分类
        Category category = categoryService.selectCategoryById(collectionInfo.getCategoryId());
        if (StringUtils.isNotNull(category)) {
            collectionInfoDetailVo.setCategoryName(category.getName());
        }
        //查询是否收藏
        Collect collect = new Collect();
        collect.setUserId(SecurityUtils.getUserId());
        collect.setType(CollectTypeEnum.COLLECT_TYPE_2.getValue());
        collect.setTargetId(id);
        List<Collect> collects = collectMapper.selectCollectList(collect);
        collectionInfoDetailVo.setIsCollect(StringUtils.isNotEmpty(collects));

        //查询商品
        Goods goods = new Goods();
        goods.setCollectionId(id);
        goods.setStatus(GoodsStatusEnum.GOODS_STATUS_1.getValue());
        List<Goods> goodsList = goodsMapper.selectGoodsList(goods);
        collectionInfoDetailVo.setGoodsList(goodsList);

        //查询媒体
        CollectionMultimedia media = new CollectionMultimedia();
        media.setCollectionId(id);
        media.setStatus(CollectionStatusEnum.COLLECTION_STATUS_1.getValue());
        List<CollectionMultimedia> mediaList = collectionMultimediaMapper.selectCollectionMultimediaList(media);
        collectionInfoDetailVo.setMultimediaList(mediaList);

        return collectionInfoDetailVo;
    }

}
