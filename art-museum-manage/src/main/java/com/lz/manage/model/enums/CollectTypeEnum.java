package com.lz.manage.model.enums;

import lombok.Getter;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

/**
 * 收藏类型枚举
 */
@Getter
public enum CollectTypeEnum {

    /**
     * 文章
     */
    COLLECT_TYPE_1("1", "文章"),

    /**
     * 藏品
     */
    COLLECT_TYPE_2("2", "藏品");

    private static final Map<String, CollectTypeEnum> VALUE_TO_ENUM = new HashMap<>();

    static {
        for (CollectTypeEnum item : values()) {
            VALUE_TO_ENUM.put(item.value, item);
        }
    }

    private final String value;
    private final String label;

    CollectTypeEnum(String value, String label) {
        this.value = value;
        this.label = label;
    }

    /**
     * 根据 value 获取枚举
     *
     * @param value 数据库存储值
     * @return 对应枚举（Optional）
     */
    public static Optional<CollectTypeEnum> getEnumByValue(String value) {
        if (value == null || value.isEmpty()) {
            return Optional.empty();
        }
        return Optional.ofNullable(VALUE_TO_ENUM.get(value));
    }
}
