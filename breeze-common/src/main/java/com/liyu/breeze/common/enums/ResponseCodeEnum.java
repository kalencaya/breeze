package com.liyu.breeze.common.enums;

/**
 * @author gleiyu
 */
public enum ResponseCodeEnum {
    /**
     * 操作结果枚举类
     * 530 自定义异常
     */
    SUCCESS("204", "成功"),
    ERROR_NO_PRIVILEGE("403", "权限不足，禁止访问！"),
    ERROR_UNAUTHORIZED("401", "用户认证不通过！"),
    ERROR("500", "操作失败！请检查数据重试或者联系管理员。"),
    ERROR_CUSTOM("530", ""),
    ERROR_EMAIL("531", "邮件发送失败，请检查邮箱地址重试。"),
    ERROR_DUPLICATE_DATA("532", "数据重复,请检查数据重试！"),
    ERROR_UNSUPPORTED_CONNECTION("533", "不支持的数据源连接类型"),
    ERROR_CONNECTION("534", "连接错误，请检查配置重试！");

    private final String code;

    private final String value;

    ResponseCodeEnum(String code, String value) {
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
