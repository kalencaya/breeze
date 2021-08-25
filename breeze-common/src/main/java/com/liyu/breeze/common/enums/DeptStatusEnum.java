package com.liyu.breeze.common.enums;

/**
 * @author gleiyu
 */

public enum DeptStatusEnum {
    /**
     * 正常
     */
    NORMAL("1", "normal"),
    /**
     * 禁用
     */
    DISABLE("2", "disable"),
    ;

    String value;
    String label;

    DeptStatusEnum(String value, String label) {
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
