package com.liyu.breeze.common.constant;

import com.liyu.breeze.common.annotation.Desc;

/**
 * 权限编码
 * 1位前缀+3位模块code
 *
 * @author gleiyu
 */
public class PrivilegeConstants {
    /**
     * 权限编码前缀
     */
    public static final String PRIVILEGE_PREFIX = "p";
    /**
     * 角色编码前缀
     */
    public static final String ROLE_PREFIX = "r";

    /**
     * 系统菜单
     */
    public interface ModuleCode {
        /**
         * 系统管理
         */
        String ADMIN = "adm";
        /**
         * 数据字典
         */
        String DICT = "dic";
        /**
         * 字典类型管理
         */
        String DICT_TYPE = "dct";
        /**
         * 数据字典管理
         */
        String DICT_DATA = "dcd";
        /**
         * 用户管理
         */
        String USER = "usr";
        /**
         * 角色管理
         */
        String ROLE = "rol";
        /**
         * 权限管理
         */
        String PRIVILEGE = "pvg";
        /**
         * 部门管理
         */
        String DEPT = "dep";

        /**
         * 参数设置
         */
        String SETTING = "set";
        /**
         * 日志管理
         */
        String LOG = "log";
        /**
         * 操作日志
         */
        String ACTION_LOG = "atl";
        /**
         * 登录日志
         */
        String LOGIN_LOG = "lgl";
        /**
         * 元数据管理
         */
        String META = "mta";
        /**
         * 元数据-数据源管理
         */
        String DATASOURCE = "dts";
        /**
         * 元数据-业务系统管理
         */
        String SYSTEM_META = "stm";
        /**
         * 元数据-参考数据类型管理
         */
        String META_DATA_SET_TYPE = "mdt";
        /**
         * 元数据-参考数据管理
         */
        String META_DATA_SET = "mds";
        /**
         * 元数据-参考数据映射管理
         */
        String META_DATA_MAP = "mdm";
        /**
         * 元数据-数据元管理
         */
        String META_DATA_ELEMENT = "mde";

    }

    /**
     * 操作权限
     */
    public interface ActionCode {
        /**
         * 菜单显示
         */
        String SHOW = "0";
        /**
         * 新增
         */
        String ADD = "1";
        /**
         * 修改
         */
        String EDIT = "2";
        /**
         * 删除
         */
        String DELETE = "3";
        /**
         * 查询
         */
        String SELECT = "4";
        /**
         * 授权
         */
        String GRANT = "5";
        /**
         * 查看加密敏感信息
         */
        String SECURITY = "6";
    }

    /**
     * 0-菜单权限
     */
    @Desc("{\"id\":1,\"privilegeName\":\"系统管理\",\"resourceType\":0, \"resourcePath\":\"\",\"pid\":0}")
    public static final String ADMIN_SHOW = PRIVILEGE_PREFIX + ModuleCode.ADMIN + ActionCode.SHOW;
    @Desc("{\"id\":2,\"privilegeName\":\"用户管理\",\"resourceType\":0, \"resourcePath\":\"\",\"pid\":1}")
    public static final String USER_SHOW = PRIVILEGE_PREFIX + ModuleCode.USER + ActionCode.SHOW;
    @Desc("{\"id\":3,\"privilegeName\":\"权限管理\",\"resourceType\":0, \"resourcePath\":\"\",\"pid\":1}")
    public static final String PRIVILEGE_SHOW = PRIVILEGE_PREFIX + ModuleCode.PRIVILEGE + ActionCode.SHOW;
    @Desc("{\"id\":4,\"privilegeName\":\"数据字典\",\"resourceType\":0, \"resourcePath\":\"\",\"pid\":1}")
    public static final String DICT_SHOW = PRIVILEGE_PREFIX + ModuleCode.DICT + ActionCode.SHOW;
    @Desc("{\"id\":5,\"privilegeName\":\"系统设置\",\"resourceType\":0, \"resourcePath\":\"\",\"pid\":1}")
    public static final String SETTING_SHOW = PRIVILEGE_PREFIX + ModuleCode.SETTING + ActionCode.SHOW;
    @Desc("{\"id\":6,\"privilegeName\":\"元数据\",\"resourceType\":0, \"resourcePath\":\"\",\"pid\":0}")
    public static final String META_SHOW = PRIVILEGE_PREFIX + ModuleCode.META + ActionCode.SHOW;
    @Desc("{\"id\":7,\"privilegeName\":\"数据源\",\"resourceType\":0, \"resourcePath\":\"\",\"pid\":0}")
    public static final String DATASOURCE_SHOW = PRIVILEGE_PREFIX + ModuleCode.DATASOURCE + ActionCode.SHOW;
    /**
     * 1-操作权限
     */
    @Desc("{\"id\":100001,\"privilegeName\":\"字典类型\",\"resourceType\":1, \"resourcePath\":\"\",\"pid\":1}")
    public static final String DICT_TYPE_SELECT = PRIVILEGE_PREFIX + ModuleCode.DICT_TYPE + ActionCode.SELECT;
    @Desc("{\"id\":100002,\"privilegeName\":\"新增字典类型\",\"resourceType\":1, \"resourcePath\":\"\",\"pid\":100001}")
    public static final String DICT_TYPE_ADD = PRIVILEGE_PREFIX + ModuleCode.DICT_TYPE + ActionCode.ADD;
    @Desc("{\"id\":100003,\"privilegeName\":\"删除字典类型\",\"resourceType\":1, \"resourcePath\":\"\",\"pid\":100001}")
    public static final String DICT_TYPE_DELETE = PRIVILEGE_PREFIX + ModuleCode.DICT_TYPE + ActionCode.DELETE;
    @Desc("{\"id\":100004,\"privilegeName\":\"修改字典类型\",\"resourceType\":1, \"resourcePath\":\"\",\"pid\":100001}")
    public static final String DICT_TYPE_EDIT = PRIVILEGE_PREFIX + ModuleCode.DICT_TYPE + ActionCode.EDIT;

    @Desc("{\"id\":100005,\"privilegeName\":\"数据字典\",\"resourceType\":1, \"resourcePath\":\"\",\"pid\":1}")
    public static final String DICT_DATA_SELECT = PRIVILEGE_PREFIX + ModuleCode.DICT_DATA + ActionCode.SELECT;
    @Desc("{\"id\":100006,\"privilegeName\":\"新增数据字典\",\"resourceType\":1, \"resourcePath\":\"\",\"pid\":100005}")
    public static final String DICT_DATA_ADD = PRIVILEGE_PREFIX + ModuleCode.DICT_DATA + ActionCode.ADD;
    @Desc("{\"id\":100007,\"privilegeName\":\"删除数据字典\",\"resourceType\":1, \"resourcePath\":\"\",\"pid\":100005}")
    public static final String DICT_DATA_DELETE = PRIVILEGE_PREFIX + ModuleCode.DICT_DATA + ActionCode.DELETE;
    @Desc("{\"id\":100008,\"privilegeName\":\"修改数据字典\",\"resourceType\":1, \"resourcePath\":\"\",\"pid\":100005}")
    public static final String DICT_DATA_EDIT = PRIVILEGE_PREFIX + ModuleCode.DICT_DATA + ActionCode.EDIT;

    @Desc("{\"id\":100009,\"privilegeName\":\"用户管理\",\"resourceType\":1, \"resourcePath\":\"\",\"pid\":1}")
    public static final String USER_SELECT = PRIVILEGE_PREFIX + ModuleCode.USER + ActionCode.SELECT;
    @Desc("{\"id\":100010,\"privilegeName\":\"新增用户\",\"resourceType\":1, \"resourcePath\":\"\",\"pid\":100009}")
    public static final String USER_ADD = PRIVILEGE_PREFIX + ModuleCode.USER + ActionCode.ADD;
    @Desc("{\"id\":100011,\"privilegeName\":\"删除用户\",\"resourceType\":1, \"resourcePath\":\"\",\"pid\":100009}")
    public static final String USER_DELETE = PRIVILEGE_PREFIX + ModuleCode.USER + ActionCode.DELETE;
    @Desc("{\"id\":100012,\"privilegeName\":\"修改用户\",\"resourceType\":1, \"resourcePath\":\"\",\"pid\":100009}")
    public static final String USER_EDIT = PRIVILEGE_PREFIX + ModuleCode.USER + ActionCode.EDIT;

    @Desc("{\"id\":100013,\"privilegeName\":\"角色管理\",\"resourceType\":1, \"resourcePath\":\"\",\"pid\":1}")
    public static final String ROLE_SELECT = PRIVILEGE_PREFIX + ModuleCode.ROLE + ActionCode.SELECT;
    @Desc("{\"id\":100014,\"privilegeName\":\"新增角色\",\"resourceType\":1, \"resourcePath\":\"\",\"pid\":100013}")
    public static final String ROLE_ADD = PRIVILEGE_PREFIX + ModuleCode.ROLE + ActionCode.ADD;
    @Desc("{\"id\":100015,\"privilegeName\":\"删除角色\",\"resourceType\":1, \"resourcePath\":\"\",\"pid\":100013}")
    public static final String ROLE_DELETE = PRIVILEGE_PREFIX + ModuleCode.ROLE + ActionCode.DELETE;
    @Desc("{\"id\":100016,\"privilegeName\":\"修改角色\",\"resourceType\":1, \"resourcePath\":\"\",\"pid\":100013}")
    public static final String ROLE_EDIT = PRIVILEGE_PREFIX + ModuleCode.ROLE + ActionCode.EDIT;
    @Desc("{\"id\":100017,\"privilegeName\":\"角色授权\",\"resourceType\":1, \"resourcePath\":\"\",\"pid\":100013}")
    public static final String ROLE_GRANT = PRIVILEGE_PREFIX + ModuleCode.ROLE + ActionCode.GRANT;

    @Desc("{\"id\":100018,\"privilegeName\":\"部门管理\",\"resourceType\":1, \"resourcePath\":\"\",\"pid\":1}")
    public static final String DEPT_SELECT = PRIVILEGE_PREFIX + ModuleCode.DEPT + ActionCode.SELECT;
    @Desc("{\"id\":100019,\"privilegeName\":\"新增部门\",\"resourceType\":1, \"resourcePath\":\"\",\"pid\":100018}")
    public static final String DEPT_ADD = PRIVILEGE_PREFIX + ModuleCode.DEPT + ActionCode.ADD;
    @Desc("{\"id\":100020,\"privilegeName\":\"删除部门\",\"resourceType\":1, \"resourcePath\":\"\",\"pid\":100018}")
    public static final String DEPT_DELETE = PRIVILEGE_PREFIX + ModuleCode.DEPT + ActionCode.DELETE;
    @Desc("{\"id\":100021,\"privilegeName\":\"修改部门\",\"resourceType\":1, \"resourcePath\":\"\",\"pid\":100018}")
    public static final String DEPT_EDIT = PRIVILEGE_PREFIX + ModuleCode.DEPT + ActionCode.EDIT;
    @Desc("{\"id\":100022,\"privilegeName\":\"部门授权\",\"resourceType\":1, \"resourcePath\":\"\",\"pid\":100018}")
    public static final String DEPT_GRANT = PRIVILEGE_PREFIX + ModuleCode.DEPT + ActionCode.GRANT;


    @Desc("{\"id\":100023,\"privilegeName\":\"数据源\",\"resourceType\":1, \"resourcePath\":\"\",\"pid\":6}")
    public static final String DATASOURCE_SELECT = PRIVILEGE_PREFIX + ModuleCode.DATASOURCE + ActionCode.SELECT;
    @Desc("{\"id\":100024,\"privilegeName\":\"新增数据源\",\"resourceType\":1, \"resourcePath\":\"\",\"pid\":100023}")
    public static final String DATASOURCE_ADD = PRIVILEGE_PREFIX + ModuleCode.DATASOURCE + ActionCode.ADD;
    @Desc("{\"id\":100025,\"privilegeName\":\"删除数据源\",\"resourceType\":1, \"resourcePath\":\"\",\"pid\":100023}")
    public static final String DATASOURCE_DELETE = PRIVILEGE_PREFIX + ModuleCode.DATASOURCE + ActionCode.DELETE;
    @Desc("{\"id\":100026,\"privilegeName\":\"修改数据源\",\"resourceType\":1, \"resourcePath\":\"\",\"pid\":100023}")
    public static final String DATASOURCE_EDIT = PRIVILEGE_PREFIX + ModuleCode.DATASOURCE + ActionCode.EDIT;
    @Desc("{\"id\":100027,\"privilegeName\":\"查看密码\",\"resourceType\":1, \"resourcePath\":\"\",\"pid\":100023}")
    public static final String DATASOURCE_SECURITY = PRIVILEGE_PREFIX + ModuleCode.DATASOURCE + ActionCode.SECURITY;
}
