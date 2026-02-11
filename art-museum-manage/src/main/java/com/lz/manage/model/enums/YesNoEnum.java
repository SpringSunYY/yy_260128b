package com.lz.manage.model.enums;

import lombok.Getter;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

/**
 * 是否枚举
 * 对应字典类型：sys_yes_no
 */
@Getter
public enum YesNoEnum {

    SYS_YES_NO_Y("Y", "是"),
    SYS_YES_NO_N("N", "否");

    private final String value;
    private final String label;

    YesNoEnum(String value, String label) {
        this.value = value;
        this.label = label;
    }

    private static final Map<String, YesNoEnum> VALUE_MAP = new HashMap<>();

    static {
        for (YesNoEnum item : values()) {
            VALUE_MAP.put(item.value + "_" + item.name(), item);
        }
    }

    public static Optional<YesNoEnum> getEnumByValue(String value) {
        if (value == null || value.isEmpty()) {
            return Optional.empty();
        }
        for (YesNoEnum item : values()) {
            if (item.value.equals(value)) {
                return Optional.of(item);
            }
        }
        return Optional.empty();
    }
}
