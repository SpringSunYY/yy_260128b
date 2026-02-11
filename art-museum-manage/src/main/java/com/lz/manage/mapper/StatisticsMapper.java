package com.lz.manage.mapper;

import com.lz.manage.model.statistics.dto.StatisticsRequest;
import com.lz.manage.model.statistics.po.StatisticsPo;

import java.util.List;

public interface StatisticsMapper {
    List<StatisticsPo<Long>> collectStatistics(StatisticsRequest statisticsRequest);

    List<StatisticsPo<Long>> collectRankStatistics(StatisticsRequest statisticsRequest);

    List<StatisticsPo<Long>> orderRatioStatistics(StatisticsRequest statisticsRequest);

    List<StatisticsPo<Float>> orderAmountStatistics(StatisticsRequest statisticsRequest);

    List<StatisticsPo<Long>> orderStatistics(StatisticsRequest statisticsRequest);

    List<StatisticsPo<Float>> orderGoodsRankStatistics(StatisticsRequest statisticsRequest);
}
