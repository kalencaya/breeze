package com.liyu.breeze.common.enums;

/**
 * SILENT = 0,
 * WARN_MESSAGE = 1,
 * ERROR_MESSAGE = 2,
 * NOTIFICATION = 4,
 * REDIRECT = 9,
 *
 * @author gleiyu
 */
public enum ErrorShowTypeEnum {
    /**
     * SILENT
     */
    SILENT(0, "SILENT"),
    /**
     * WARN_MESSAGE
     */
    WARN_MESSAGE(1, "WARN_MESSAGE"),
    /**
     * ERROR_MESSAGE
     */
    ERROR_MESSAGE(2, "ERROR_MESSAGE"),
    /**
     * NOTIFICATION
     */
    NOTIFICATION(4, "NOTIFICATION"),
    /**
     * REDIRECT
     */
    REDIRECT(9, "REDIRECT");
    private final Integer code;
    private final String value;

    ErrorShowTypeEnum(Integer code, String value) {
        this.code = code;
        this.value = value;
    }

    public Integer getCode() {
        return code;
    }

    public String getValue() {
        return value;
    }
}
