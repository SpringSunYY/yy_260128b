package com.lz.manage.model.enums;

import lombok.Getter;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

/**
 * 审核状态枚举
 */
@Getter
public enum AuditStatusEnum {

    /**
     * 待审核
     */
    AUDIT_STATUS_1("1", "待审核"),

    /**
     * 同意
     */
    AUDIT_STATUS_2("2", "同意"),

    /**
     * 拒绝
     */
    AUDIT_STATUS_3("3", "拒绝");

    private static final Map<String, AuditStatusEnum> VALUE_TO_ENUM = new HashMap<>();

    static {
        for (AuditStatusEnum item : values()) {
            VALUE_TO_ENUM.put(item.value, item);
        }
    }

    private final String value;
    private final String label;

    AuditStatusEnum(String value, String label) {
        this.value = value;
        this.label = label;
    }

    /**
     * 根据 value 获取对应枚举
     */
    public static Optional<AuditStatusEnum> getEnumByValue(String value) {
        if (value == null || value.isEmpty()) {
            return Optional.empty();
        }
        return Optional.ofNullable(VALUE_TO_ENUM.get(value));
    }
}
