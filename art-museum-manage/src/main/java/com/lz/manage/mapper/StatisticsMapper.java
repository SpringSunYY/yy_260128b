package com.lz.manage.mapper;

import com.lz.manage.model.statistics.dto.StatisticsRequest;
import com.lz.manage.model.statistics.po.StatisticsPo;

import java.util.List;

public interface StatisticsMapper {
    List<StatisticsPo<Long>> collectStatistics(StatisticsRequest statisticsRequest);

    List<StatisticsPo<Long>> collectRank(StatisticsRequest statisticsRequest);
}
