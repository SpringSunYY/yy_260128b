package com.lz.manage.controller;

import com.lz.common.core.controller.BaseController;
import com.lz.common.core.domain.AjaxResult;
import com.lz.manage.model.statistics.dto.StatisticsRequest;
import com.lz.manage.service.IStatisticsService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * TODO
 *
 * @Project: ArtMuseum
 * @Author: YY
 * @CreateTime: 2026-02-11  20:24
 * @Version: 1.0
 */
@RestController
@RequestMapping("/manage/statistics")
public class StatisticsController extends BaseController {

    @Resource
    private IStatisticsService statisticsService;

    /**
     * 收藏统计
     *
     * @param statisticsRequest
     * @return
     */
    @PreAuthorize("@ss.hasPermi('manage:statistics:art')")
    @GetMapping("/collect")
    public AjaxResult collectStatistics(StatisticsRequest statisticsRequest) {
        return success(statisticsService.collectStatistics(statisticsRequest));
    }

    /**
     * 收藏排行
     *
     * @param statisticsRequest
     * @return
     */
    @PreAuthorize("@ss.hasPermi('manage:statistics:art')")
    @GetMapping("/collect/rank")
    public AjaxResult collectRank(StatisticsRequest statisticsRequest) {
        return success(statisticsService.collectRankStatistics(statisticsRequest));
    }

    /**
     * 订单成交比例
     */
    @PreAuthorize("@ss.hasPermi('manage:statistics:goods')")
    @GetMapping("/order/ratio")
    public AjaxResult orderRatioStatistics(StatisticsRequest statisticsRequest) {
        return success(statisticsService.orderRatioStatistics(statisticsRequest));
    }

    /**
     * 订单金额
     */
    @PreAuthorize("@ss.hasPermi('manage:statistics:goods')")
    @GetMapping("/order/amount")
    public AjaxResult orderAmountStatistics(StatisticsRequest statisticsRequest) {
        return success(statisticsService.orderAmountStatistics(statisticsRequest));
    }

}
