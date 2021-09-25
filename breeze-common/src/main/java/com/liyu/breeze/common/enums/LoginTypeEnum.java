package com.liyu.breeze.common.enums;

/**
 * @author gleiyu
 */

public enum LoginTypeEnum {
    /**
     * 未知
     */
    UNKNOWN("0", "未知"),
    /**
     * 登录
     */
    LOGIN("1", "登录"),
    /**
     * 登出
     */
    LOGOUT("2", "登出"),
    ;

    String value;
    String label;

    LoginTypeEnum(String value, String label) {
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
