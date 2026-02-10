package com.lz.manage.model.enums;

import lombok.Getter;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

/**
 * 收藏状态枚举
 */
@Getter
public enum CollectionStatusEnum {

    /**
     * 开启
     */
    COLLECTION_STATUS_1("1", "开启"),

    /**
     * 关闭
     */
    COLLECTION_STATUS_2("2", "关闭");

    private static final Map<String, CollectionStatusEnum> VALUE_TO_ENUM = new HashMap<>();

    static {
        for (CollectionStatusEnum item : values()) {
            VALUE_TO_ENUM.put(item.value, item);
        }
    }

    private final String value;
    private final String label;

    CollectionStatusEnum(String value, String label) {
        this.value = value;
        this.label = label;
    }

    /**
     * 根据 value 获取对应枚举
     *
     * @param value 数据库存储值
     * @return 对应枚举（Optional）
     */
    public static Optional<CollectionStatusEnum> getEnumByValue(String value) {
        if (value == null || value.isEmpty()) {
            return Optional.empty();
        }
        return Optional.ofNullable(VALUE_TO_ENUM.get(value));
    }
}
