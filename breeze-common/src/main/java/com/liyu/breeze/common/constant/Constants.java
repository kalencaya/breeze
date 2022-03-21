package com.liyu.breeze.common.constant;

/**
 * @author gleiyu
 */
public class Constants {
    /**
     * 默认日期格式
     */
    public static final String DEFAULT_DATE_FORMAT = "yyyy-MM-dd";
    public static final String DEFAULT_DATETIME_FORMAT = "yyyy-MM-dd HH:mm:ss";
    public static final String MS_DATETIME_FORMAT = "yyyy-MM-dd HH:mm:ss.SSS";
    public static final String SEPARATOR = "-";
    public static final String PATH_SEPARATOR = "/";

    public static final String DEFAULT_CHARSET = "UTF-8";

    /**
     * 系统配置相关
     */
    public static final String CFG_EMAIL_CODE = "email";
    /**
     * 角色相关
     */
    public static final String USER_DEFINE_ROLE_PREFIX = "role_";
    public static final String ROLE_NORMAL = "sys_normal";
    public static final String ROLE_SYS_ADMIN = "sys_super_admin";
    /**
     * 验证码 key
     */
    public static final String AUTH_CODE_KEY = "authCode-key_";
    /**
     * 在线用户TOKEN标识
     */
    public static final String ONLINE_TOKEN_KEY = "online-token_";
    /**
     * 在线用户标识
     */
    public static final String ONLINE_USER_KEY = "online-user_";
    /**
     * 用户token key
     */
    public static final String TOKEN_KEY = "u_token";

    /**
     * schedule job and group
     */
    public static final String JOB_PREFIX = "job-";
    public static final String JOB_GROUP_PREFIX = "jobGrp-";
    public static final String TRIGGER_PREFIX = "trigger-";
    public static final String TRIGGER_GROUP_PREFIX = "triggerGrp-";
    public static final String INTERNAL_GROUP = "sysInternal";
    public static final String JOB_LOG_KEY = "traceLog";
    public static final String ETL_JOB_PREFIX = "job-";

    public static final String JOB_LINK_IN_PORT = "inPort";
    public static final String JOB_LINK_OUT_PORT = "outPort";
}
