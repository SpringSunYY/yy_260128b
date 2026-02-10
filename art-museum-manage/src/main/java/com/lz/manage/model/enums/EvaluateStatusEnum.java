package com.lz.manage.model.enums;

import lombok.Getter;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

/**
 * 评价状态枚举
 */
@Getter
public enum EvaluateStatusEnum {

    /**
     * 正常
     */
    EVALUATE_STATUS_1("1", "正常"),

    /**
     * 异常
     */
    EVALUATE_STATUS_2("2", "异常");

    private static final Map<String, EvaluateStatusEnum> VALUE_TO_ENUM = new HashMap<>();

    static {
        for (EvaluateStatusEnum item : values()) {
            VALUE_TO_ENUM.put(item.value, item);
        }
    }

    private final String value;
    private final String label;

    EvaluateStatusEnum(String value, String label) {
        this.value = value;
        this.label = label;
    }

    /**
     * 根据 value 获取对应枚举
     *
     * @param value 数据库存储值
     * @return 对应枚举（Optional）
     */
    public static Optional<EvaluateStatusEnum> getEnumByValue(String value) {
        if (value == null || value.isEmpty()) {
            return Optional.empty();
        }
        return Optional.ofNullable(VALUE_TO_ENUM.get(value));
    }
}
