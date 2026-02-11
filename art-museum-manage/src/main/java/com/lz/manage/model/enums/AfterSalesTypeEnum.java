package com.lz.manage.model.enums;

import lombok.Getter;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

/**
 * 售后类型枚举
 * 对应字典类型：after_sales_type
 */
@Getter
public enum AfterSalesTypeEnum {

    AFTER_SALES_TYPE_6("6", "退款"),
    AFTER_SALES_TYPE_7("7", "退货");

    private final String value;
    private final String label;

    AfterSalesTypeEnum(String value, String label) {
        this.value = value;
        this.label = label;
    }

    private static final Map<String, AfterSalesTypeEnum> VALUE_TO_ENUM = new HashMap<>();

    static {
        for (AfterSalesTypeEnum item : values()) {
            VALUE_TO_ENUM.put(item.value, item);
        }
    }

    public static Optional<AfterSalesTypeEnum> getEnumByValue(String value) {
        if (value == null || value.isEmpty()) {
            return Optional.empty();
        }
        return Optional.ofNullable(VALUE_TO_ENUM.get(value));
    }
}
