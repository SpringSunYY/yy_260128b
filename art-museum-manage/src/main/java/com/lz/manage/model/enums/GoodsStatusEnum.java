package com.lz.manage.model.enums;

import lombok.Getter;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

/**
 * 商品状态枚举
 */
@Getter
public enum GoodsStatusEnum {

    /**
     * 上架
     */
    GOODS_STATUS_1("1", "上架"),

    /**
     * 下架
     */
    GOODS_STATUS_2("2", "下架");

    private static final Map<String, GoodsStatusEnum> VALUE_TO_ENUM = new HashMap<>();

    static {
        for (GoodsStatusEnum item : values()) {
            VALUE_TO_ENUM.put(item.value, item);
        }
    }

    private final String value;
    private final String label;

    GoodsStatusEnum(String value, String label) {
        this.value = value;
        this.label = label;
    }

    /**
     * 根据 value 获取对应枚举
     *
     * @param value 数据库存储值
     * @return 对应枚举（Optional）
     */
    public static Optional<GoodsStatusEnum> getEnumByValue(String value) {
        if (value == null || value.isEmpty()) {
            return Optional.empty();
        }
        return Optional.ofNullable(VALUE_TO_ENUM.get(value));
    }
}
