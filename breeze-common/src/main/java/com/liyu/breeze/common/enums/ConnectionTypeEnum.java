package com.liyu.breeze.common.enums;

/**
 * @author gleiyu
 */
public enum ConnectionTypeEnum {
    /**
     * simple jdbc
     */
    JDBC("jdbc", "SIMPLE JDBC"),
    /**
     * 连接池
     */
    POOLED("pooled", "CONNECTION POOL"),

    JNDI("jndi", "JNDI");
    private final String code;

    private final String value;

    ConnectionTypeEnum(String code, String value) {
        this.code = code;
        this.value = value;
    }

    public String getCode() {
        return code;
    }

    public String getValue() {
        return value;
    }

    public static ConnectionTypeEnum valueOfName(String name) {
        if (JDBC.getCode().equals(name)) {
            return JDBC;
        } else if (POOLED.getCode().equals(name)) {
            return POOLED;
        } else if (JNDI.getCode().equals(name)) {
            return JNDI;
        } else {
            return null;
        }
    }

}
