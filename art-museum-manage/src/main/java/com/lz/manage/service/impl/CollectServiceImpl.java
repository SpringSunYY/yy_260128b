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
import com.lz.manage.mapper.CollectMapper;
import com.lz.manage.model.domain.Collect;
import com.lz.manage.service.ICollectService;
import com.lz.manage.model.dto.collect.CollectQuery;
import com.lz.manage.model.vo.collect.CollectVo;

/**
 * 收藏信息Service业务层处理
 *
 * @author YY
 * @date 2026-02-09
 */
@Service
public class CollectServiceImpl extends ServiceImpl<CollectMapper, Collect> implements ICollectService
{

    @Resource
    private CollectMapper collectMapper;

    //region mybatis代码
    /**
     * 查询收藏信息
     *
     * @param id 收藏信息主键
     * @return 收藏信息
     */
    @Override
    public Collect selectCollectById(Long id)
    {
        return collectMapper.selectCollectById(id);
    }

    /**
     * 查询收藏信息列表
     *
     * @param collect 收藏信息
     * @return 收藏信息
     */
    @Override
    public List<Collect> selectCollectList(Collect collect)
    {
        return collectMapper.selectCollectList(collect);
    }

    /**
     * 新增收藏信息
     *
     * @param collect 收藏信息
     * @return 结果
     */
    @Override
    public int insertCollect(Collect collect)
    {
        collect.setCreateTime(DateUtils.getNowDate());
        return collectMapper.insertCollect(collect);
    }

    /**
     * 修改收藏信息
     *
     * @param collect 收藏信息
     * @return 结果
     */
    @Override
    public int updateCollect(Collect collect)
    {
        return collectMapper.updateCollect(collect);
    }

    /**
     * 批量删除收藏信息
     *
     * @param ids 需要删除的收藏信息主键
     * @return 结果
     */
    @Override
    public int deleteCollectByIds(Long[] ids)
    {
        return collectMapper.deleteCollectByIds(ids);
    }

    /**
     * 删除收藏信息信息
     *
     * @param id 收藏信息主键
     * @return 结果
     */
    @Override
    public int deleteCollectById(Long id)
    {
        return collectMapper.deleteCollectById(id);
    }
    //endregion
    @Override
    public QueryWrapper<Collect> getQueryWrapper(CollectQuery collectQuery){
        QueryWrapper<Collect> queryWrapper = new QueryWrapper<>();
        //如果不使用params可以删除
        Map<String, Object> params = collectQuery.getParams();
        if (StringUtils.isNull(params)) {
            params = new HashMap<>();
        }
        Long id = collectQuery.getId();
        queryWrapper.eq( StringUtils.isNotNull(id),"id",id);

        String type = collectQuery.getType();
        queryWrapper.eq(StringUtils.isNotEmpty(type) ,"type",type);

        Date createTime = collectQuery.getCreateTime();
        queryWrapper.between(StringUtils.isNotNull(params.get("beginCreateTime"))&&StringUtils.isNotNull(params.get("endCreateTime")),"create_time",params.get("beginCreateTime"),params.get("endCreateTime"));

        return queryWrapper;
    }

    @Override
    public List<CollectVo> convertVoList(List<Collect> collectList) {
        if (StringUtils.isEmpty(collectList)) {
            return Collections.emptyList();
        }
        return collectList.stream().map(CollectVo::objToVo).collect(Collectors.toList());
    }

}
