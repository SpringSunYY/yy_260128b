package com.lz.manage.model.statistics.dto;

import lombok.Data;

import javax.validation.constraints.NotEmpty;

/**
 * 统计
 *
 * @Project: Lecture
 * @Author: YY
 * @CreateTime: 2026-01-18  19:27
 * @Version: 1.0
 */
@Data
public class StatisticsRequest {

    /**
     * 开始时间
     */
    @NotEmpty(message = "开始时间不能为空")
    private String startTime;
    /**
     * 结束时间
     */
    @NotEmpty(message = "结束时间不能为空")
    private String endTime;

    /**
     * 类型
     */
    private String type;
}
