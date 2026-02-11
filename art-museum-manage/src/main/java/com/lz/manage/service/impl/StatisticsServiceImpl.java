package com.lz.manage.service.impl;

import com.lz.common.utils.StringUtils;
import com.lz.manage.mapper.StatisticsMapper;
import com.lz.manage.model.domain.CollectionInfo;
import com.lz.manage.model.domain.NoticeInfo;
import com.lz.manage.model.enums.CollectTypeEnum;
import com.lz.manage.model.statistics.dto.StatisticsRequest;
import com.lz.manage.model.statistics.po.StatisticsPo;
import com.lz.manage.model.statistics.vo.BaseStatisticsVo;
import com.lz.manage.service.ICollectionInfoService;
import com.lz.manage.service.INoticeInfoService;
import com.lz.manage.service.IStatisticsService;
import jdk.nashorn.api.scripting.ScriptUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 统计
 *
 * @Project: ArtMuseum
 * @Author: YY
 * @CreateTime: 2026-02-11  20:25
 * @Version: 1.0
 */
@Service
public class StatisticsServiceImpl implements IStatisticsService {
    @Resource
    private StatisticsMapper statisticsMapper;

    @Resource
    private INoticeInfoService noticeInfoService;

    @Resource
    private ICollectionInfoService collectionInfoService;

    /**
     * 收藏统计
     *
     * @param statisticsRequest 查询条件
     * @return
     */
    @Override
    public List<BaseStatisticsVo<Long>> collectStatistics(StatisticsRequest statisticsRequest) {
        statisticsRequest.setStartTime(statisticsRequest.getStartTime() + " 00:00:00");
        statisticsRequest.setEndTime(statisticsRequest.getEndTime() + " 23:59:59");
        List<StatisticsPo<Long>> statisticsPos = statisticsMapper.collectStatistics(statisticsRequest);
        if (statisticsPos.isEmpty()) {
            return Collections.emptyList();
        }
        return statisticsPos.stream().map(statisticsPo -> {
            BaseStatisticsVo<Long> baseStatisticsVo = new BaseStatisticsVo<>();
            baseStatisticsVo.setName(statisticsPo.getName());
            baseStatisticsVo.setValue(statisticsPo.getValue());
            return baseStatisticsVo;
        }).collect(Collectors.toList());
    }

    @Override
    public List<BaseStatisticsVo<Long>> collectRank(StatisticsRequest statisticsRequest) {
        statisticsRequest.setStartTime(statisticsRequest.getStartTime() + " 00:00:00");
        statisticsRequest.setEndTime(statisticsRequest.getEndTime() + " 23:59:59");
        List<StatisticsPo<Long>> statisticsPos = statisticsMapper.collectRank(statisticsRequest);
        if (statisticsPos.isEmpty()) {
            return Collections.emptyList();
        }
        return statisticsPos.stream().map(statisticsPo -> {
            BaseStatisticsVo<Long> baseStatisticsVo = new BaseStatisticsVo<>();
            if (statisticsRequest.getType().equals(CollectTypeEnum.COLLECT_TYPE_1.getValue())) {
                NoticeInfo noticeInfo = noticeInfoService.selectNoticeInfoById(Long.parseLong(statisticsPo.getName()));
                if (StringUtils.isNotNull(noticeInfo)) {
                    baseStatisticsVo.setName(noticeInfo.getTitle());
                }else {
                    baseStatisticsVo.setName(statisticsPo.getName());
                }
            }else {
                CollectionInfo collectionInfo = collectionInfoService.selectCollectionInfoById(Long.parseLong(statisticsPo.getName()));
                if (StringUtils.isNotNull(collectionInfo)){
                    baseStatisticsVo.setName(collectionInfo.getName());
                }else {
                    baseStatisticsVo.setName(statisticsPo.getName());
                }
            }
            baseStatisticsVo.setValue(statisticsPo.getValue());
            return baseStatisticsVo;
        }).collect(Collectors.toList());
    }
}
