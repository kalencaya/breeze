package com.liyu.breeze.common.enums;

/**
 * @author gleiyu
 */

public enum JobTypeEnum {
    /**
     * 周期作业
     */
    BATCH("b", "周期作业"),
    /**
     * 实时作业
     */
    REALTIME("r", "实时作业"),
    ;

    String value;
    String label;

    JobTypeEnum(String value, String label) {
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
