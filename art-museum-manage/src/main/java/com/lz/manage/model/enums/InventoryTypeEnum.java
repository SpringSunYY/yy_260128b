package com.lz.manage.model.enums;

import lombok.Getter;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

/**
 * 库存变动类型枚举
 */
@Getter
public enum InventoryTypeEnum {

    /**
     * 出库
     */
    INVENTORY_TYPE_1("1", "出库"),

    /**
     * 入库
     */
    INVENTORY_TYPE_2("2", "入库");

    private static final Map<String, InventoryTypeEnum> VALUE_TO_ENUM = new HashMap<>();

    static {
        for (InventoryTypeEnum item : values()) {
            VALUE_TO_ENUM.put(item.value, item);
        }
    }

    private final String value;
    private final String label;

    InventoryTypeEnum(String value, String label) {
        this.value = value;
        this.label = label;
    }

    /**
     * 根据 value 获取对应枚举
     *
     * @param value 数据库存储值
     * @return 对应枚举（Optional）
     */
    public static Optional<InventoryTypeEnum> getEnumByValue(String value) {
        if (value == null || value.isEmpty()) {
            return Optional.empty();
        }
        return Optional.ofNullable(VALUE_TO_ENUM.get(value));
    }
}
