package com.liyu.breeze.common.enums;

/**
 * @author gleiyu
 */

public enum RegisterChannelEnum {
    /**
     * 注册
     */
    REGISTER("01", "注册"),
    /**
     * 后台导入
     */
    BACKGROUND_IMPORT("02", "后台导入");

    String value;
    String label;

    RegisterChannelEnum(String value, String label) {
        this.value = value;
        this.label = label;
    }

    public String getValue() {
        return value;
    }

    public String getLabel() {
        return label;
    }
}
