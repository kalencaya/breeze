package com.liyu.breeze.common.enums;

/**
 * @author gleiyu
 */
public enum DataSourceTypeEnum {
    /**
     * simple jdbc
     */
    MYSQL("mysql", "Mysql"),
    /**
     * 连接池
     */
    ORACLE("oracle", "Oracle");
    private final String code;

    private final String value;

    DataSourceTypeEnum(String code, String value) {
        this.code = code;
        this.value = value;
    }

    public String getCode() {
        return code;
    }

    public String getValue() {
        return value;
    }

    public static DataSourceTypeEnum valueOfName(String name) {
        if (MYSQL.getCode().equals(name)) {
            return MYSQL;
        } else if (ORACLE.getCode().equals(name)) {
            return ORACLE;
        } else {
            return null;
        }
    }
}
