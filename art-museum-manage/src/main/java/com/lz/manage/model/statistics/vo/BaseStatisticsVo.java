package com.lz.manage.model.statistics.vo;

import lombok.Data;

/**
 * 基础统计VO
 */
@Data
public class BaseStatisticsVo<T> {
    private String name;
    private T value;

}
