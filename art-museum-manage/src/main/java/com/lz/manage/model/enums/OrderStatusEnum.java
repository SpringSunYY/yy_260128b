package com.lz.manage.model.enums;

import lombok.Getter;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

/**
 * 订单状态枚举
 * 对应字典类型：order_status
 */
@Getter
public enum OrderStatusEnum {

    ORDER_STATUS_1("1", "待付款"),
    ORDER_STATUS_2("2", "待发货"),
    ORDER_STATUS_3("3", "已发货"),
    ORDER_STATUS_4("4", "已收货"),
    ORDER_STATUS_5("5", "售后中"),
    ORDER_STATUS_6("6", "已退款"),
    ORDER_STATUS_7("7", "已退货"),
    ORDER_STATUS_8("8", "已超时");

    private final String value;
    private final String label;

    OrderStatusEnum(String value, String label) {
        this.value = value;
        this.label = label;
    }

    private static final Map<String, OrderStatusEnum> VALUE_TO_ENUM = new HashMap<>();

    static {
        for (OrderStatusEnum item : values()) {
            VALUE_TO_ENUM.put(item.value, item);
        }
    }

    public static Optional<OrderStatusEnum> getEnumByValue(String value) {
        if (value == null || value.isEmpty()) {
            return Optional.empty();
        }
        return Optional.ofNullable(VALUE_TO_ENUM.get(value));
    }
}
