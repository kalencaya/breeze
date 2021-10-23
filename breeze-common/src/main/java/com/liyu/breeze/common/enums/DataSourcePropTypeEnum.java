package com.liyu.breeze.common.enums;

/**
 * @author gleiyu
 */
public enum DataSourcePropTypeEnum {
    /**
     * general
     */
    GENERAL("general", "General Properties"),
    /**
     * jdbc connection prop
     */
    JDBC("jdbc", "JDBC Connection Properties"),
    /**
     * connection pool prop
     */
    POOL("pool", "Connection Pool Properties");
    private final String code;

    private final String value;

    DataSourcePropTypeEnum(String code, String value) {
        this.code = code;
        this.value = value;
    }

    public String getCode() {
        return code;
    }

    public String getValue() {
        return value;
    }

}
