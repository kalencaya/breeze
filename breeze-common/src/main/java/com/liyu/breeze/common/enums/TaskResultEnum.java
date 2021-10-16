package com.liyu.breeze.common.enums;

/**
 * @author gleiyu
 */

public enum TaskResultEnum {
    /**
     * 成功/失败
     */
    SUCCESS("SUCCESS", "成功"),
    FAILURE("FAILURE", "失败");

    private final String code;

    private final String value;

    TaskResultEnum(String code, String value) {
        this.code = code;
        this.value = value;
    }

    public String getCode() {
        return this.code;
    }

    public String getValue() {
        return this.value;
    }
}
