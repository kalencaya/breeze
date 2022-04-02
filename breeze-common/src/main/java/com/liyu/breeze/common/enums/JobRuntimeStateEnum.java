package com.liyu.breeze.common.enums;

/**
 * @author gleiyu
 */

public enum JobRuntimeStateEnum {
    /**
     * 停止
     */
    STOP("1", "停止"),
    /**
     * 运行中
     */
    RUNNING("2", "运行中"),
    /**
     * 等待
     */
    WAIT("3", "等待");

    String value;
    String label;

    JobRuntimeStateEnum(String value, String label) {
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
