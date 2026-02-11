package com.lz.manage.model.statistics.po;

import lombok.Data;

/**
 * 统计返回RO
 */
@Data
public class StatisticsPo<T> {
    private String name;
    private T value;
}
