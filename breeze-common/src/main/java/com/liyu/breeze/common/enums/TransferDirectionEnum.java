package com.liyu.breeze.common.enums;

/**
 * @author gleiyu
 */

public enum TransferDirectionEnum {
    /**
     * TARGET
     */
    TARGET("1", "TARGET"),
    /**
     * SOURCE
     */
    SOURCE("0", "SOURCE");

    String value;
    String label;

    TransferDirectionEnum(String value, String label) {
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
