package com.lz.manage.service;

import com.lz.manage.model.statistics.dto.StatisticsRequest;
import com.lz.manage.model.statistics.vo.BaseStatisticsVo;

import java.util.List;

/**
 * 统计 Service
 *
 * @Project: ArtMuseum
 * @Author: YY
 * @CreateTime: 2026-02-11  20:25
 * @Version: 1.0
 */
public interface IStatisticsService {
    /**
     * 收藏统计
     *
     * @param statisticsRequest
     * @return
     */
    List<BaseStatisticsVo<Long>> collectStatistics(StatisticsRequest statisticsRequest);

    /**
     * 收藏排行
     *
     * @param statisticsRequest
     * @return
     */
    List<BaseStatisticsVo<Long>> collectRankStatistics(StatisticsRequest statisticsRequest);

    List<BaseStatisticsVo<Long>> orderRatioStatistics(StatisticsRequest statisticsRequest);
}
