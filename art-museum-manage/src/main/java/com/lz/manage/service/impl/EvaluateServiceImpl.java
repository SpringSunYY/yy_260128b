package com.lz.manage.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.lz.common.core.domain.entity.SysUser;
import com.lz.common.utils.DateUtils;
import com.lz.common.utils.SecurityUtils;
import com.lz.common.utils.StringUtils;
import com.lz.common.utils.ThrowUtils;
import com.lz.manage.mapper.CollectionInfoMapper;
import com.lz.manage.mapper.EvaluateMapper;
import com.lz.manage.model.domain.CollectionInfo;
import com.lz.manage.model.domain.Evaluate;
import com.lz.manage.model.dto.evaluate.EvaluateQuery;
import com.lz.manage.model.enums.CollectionStatusEnum;
import com.lz.manage.model.enums.EvaluateStatusEnum;
import com.lz.manage.model.vo.evaluate.EvaluateVo;
import com.lz.manage.service.IEvaluateService;
import com.lz.system.service.ISysUserService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.*;
import java.util.stream.Collectors;

/**
 * 评价信息Service业务层处理
 *
 * @author YY
 * @date 2026-02-09
 */
@Service
public class EvaluateServiceImpl extends ServiceImpl<EvaluateMapper, Evaluate> implements IEvaluateService {

    @Resource
    private EvaluateMapper evaluateMapper;

    @Resource
    private CollectionInfoMapper collectionInfoMapper;

    @Resource
    private ISysUserService sysUserService;


    //region mybatis代码

    /**
     * 查询评价信息
     *
     * @param id 评价信息主键
     * @return 评价信息
     */
    @Override
    public Evaluate selectEvaluateById(Long id) {
        return evaluateMapper.selectEvaluateById(id);
    }

    /**
     * 查询评价信息列表
     *
     * @param evaluate 评价信息
     * @return 评价信息
     */
    @Override
    public List<Evaluate> selectEvaluateList(Evaluate evaluate) {
        return getEvaluateList(evaluate);
    }

    @Override
    public List<Evaluate> selectEvaluateCollectionList(Evaluate evaluate) {
        evaluate.setStatus(EvaluateStatusEnum.EVALUATE_STATUS_1.getValue());
        return getEvaluateList(evaluate);
    }

    private List<Evaluate> getEvaluateList(Evaluate evaluate) {
        List<Evaluate> evaluates = evaluateMapper.selectEvaluateList(evaluate);
        for (Evaluate info : evaluates) {
            SysUser sysUser = sysUserService.selectUserById(info.getUserId());
            if (StringUtils.isNotNull(sysUser)) {
                info.setUserName(sysUser.getUserName());
            }
            CollectionInfo collectionInfo = collectionInfoMapper.selectCollectionInfoById(info.getCollectionId());
            if (StringUtils.isNotNull(collectionInfo)) {
                info.setCollectionName(collectionInfo.getName());
            }
        }
        return evaluates;
    }

    /**
     * 新增评价信息
     *
     * @param evaluate 评价信息
     * @return 结果
     */
    @Override
    public int insertEvaluate(Evaluate evaluate) {
        //查询藏品信息
        CollectionInfo collectionInfo = collectionInfoMapper.selectCollectionInfoById(evaluate.getCollectionId());
        ThrowUtils.throwIf(StringUtils.isNull(collectionInfo), "藏品信息不存在");
        //如果不是正常的
        ThrowUtils.throwIf(!CollectionStatusEnum.COLLECTION_STATUS_1.getValue().equals(collectionInfo.getStatus()), "藏品信息当前不可见");
        evaluate.setStatus(EvaluateStatusEnum.EVALUATE_STATUS_1.getValue());
        evaluate.setUserId(SecurityUtils.getUserId());
        evaluate.setCreateTime(DateUtils.getNowDate());
        return evaluateMapper.insertEvaluate(evaluate);
    }

    /**
     * 修改评价信息
     *
     * @param evaluate 评价信息
     * @return 结果
     */
    @Override
    public int updateEvaluate(Evaluate evaluate) {
        //查询藏品信息
        CollectionInfo collectionInfo = collectionInfoMapper.selectCollectionInfoById(evaluate.getCollectionId());
        ThrowUtils.throwIf(StringUtils.isNull(collectionInfo), "藏品信息不存在");
        //如果不是正常的
        ThrowUtils.throwIf(!CollectionStatusEnum.COLLECTION_STATUS_1.getValue().equals(collectionInfo.getStatus()), "藏品信息当前不可见");
        evaluate.setUpdateBy(SecurityUtils.getUsername());
        evaluate.setUpdateTime(DateUtils.getNowDate());
        return evaluateMapper.updateEvaluate(evaluate);
    }

    /**
     * 批量删除评价信息
     *
     * @param ids 需要删除的评价信息主键
     * @return 结果
     */
    @Override
    public int deleteEvaluateByIds(Long[] ids) {
        return evaluateMapper.deleteEvaluateByIds(ids);
    }

    /**
     * 删除评价信息信息
     *
     * @param id 评价信息主键
     * @return 结果
     */
    @Override
    public int deleteEvaluateById(Long id) {
        return evaluateMapper.deleteEvaluateById(id);
    }

    //endregion
    @Override
    public QueryWrapper<Evaluate> getQueryWrapper(EvaluateQuery evaluateQuery) {
        QueryWrapper<Evaluate> queryWrapper = new QueryWrapper<>();
        //如果不使用params可以删除
        Map<String, Object> params = evaluateQuery.getParams();
        if (StringUtils.isNull(params)) {
            params = new HashMap<>();
        }
        Long id = evaluateQuery.getId();
        queryWrapper.eq(StringUtils.isNotNull(id), "id", id);

        Long collectionId = evaluateQuery.getCollectionId();
        queryWrapper.eq(StringUtils.isNotNull(collectionId), "collection_id", collectionId);

        String status = evaluateQuery.getStatus();
        queryWrapper.eq(StringUtils.isNotEmpty(status), "status", status);

        String title = evaluateQuery.getTitle();
        queryWrapper.like(StringUtils.isNotEmpty(title), "title", title);

        Date createTime = evaluateQuery.getCreateTime();
        queryWrapper.between(StringUtils.isNotNull(params.get("beginCreateTime")) && StringUtils.isNotNull(params.get("endCreateTime")), "create_time", params.get("beginCreateTime"), params.get("endCreateTime"));

        return queryWrapper;
    }

    @Override
    public List<EvaluateVo> convertVoList(List<Evaluate> evaluateList) {
        if (StringUtils.isEmpty(evaluateList)) {
            return Collections.emptyList();
        }
        return evaluateList.stream().map(EvaluateVo::objToVo).collect(Collectors.toList());
    }

}
