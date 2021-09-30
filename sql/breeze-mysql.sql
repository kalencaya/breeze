/* 数据字典类型表 */
drop table if exists t_dict_type;
create table t_dict_type (
    id bigint not null auto_increment comment '自增主键',
    dict_type_code varchar(32) not null comment '字典类型编码',
    dict_type_name varchar(128) not null comment '字典类型名称',
    remark varchar(256) comment '备注',
    creator varchar(32) comment '创建人',
    create_time timestamp default current_timestamp comment '创建时间',
    editor varchar(32) comment '修改人',
    update_time timestamp default current_timestamp on update current_timestamp comment '修改时间',
    primary key (id),
    unique key (dict_type_code),
    key (dict_type_name)
) engine = innodb comment '数据字典类型';
-- init data
insert into t_dict_type(dict_type_code, dict_type_name, creator, editor) values ('gender', '性别', 'sys', 'sys');
insert into t_dict_type(dict_type_code, dict_type_name, creator, editor) values ('is_valid', '是否有效', 'sys', 'sys');
insert into t_dict_type(dict_type_code, dict_type_name, creator, editor) values ('is_delete', '是否删除', 'sys', 'sys');
insert into t_dict_type(dict_type_code, dict_type_name, creator, editor) values ('id_card_type', '证件类型', 'sys', 'sys');
insert into t_dict_type(dict_type_code, dict_type_name, creator, editor) values ('nation', '国家', 'sys', 'sys');
insert into t_dict_type(dict_type_code, dict_type_name, creator, editor) values ('user_status', '用户状态', 'sys', 'sys');
insert into t_dict_type(dict_type_code, dict_type_name, creator, editor) values ('register_channel', '注册渠道', 'sys', 'sys');
insert into t_dict_type(dict_type_code, dict_type_name, creator, editor) values ('role_type', '角色类型', 'sys', 'sys');
insert into t_dict_type(dict_type_code, dict_type_name, creator, editor) values ('role_status', '角色状态', 'sys', 'sys');
insert into t_dict_type(dict_type_code, dict_type_name, creator, editor) values ('dept_status', '部门状态', 'sys', 'sys');
insert into t_dict_type(dict_type_code, dict_type_name, creator, editor) values ('resource_type', '权限资源类型', 'sys', 'sys');
insert into t_dict_type(dict_type_code, dict_type_name, creator, editor) values ('login_type', '登录类型', 'sys', 'sys');


/* 数据字典表 */
drop table if exists t_dict;
create table t_dict (
    id bigint not null auto_increment comment '自增主键',
    dict_type_code varchar(32) not null comment '字典类型编码',
    dict_code varchar(32) not null comment '字典编码',
    dict_value varchar(128) not null comment '字典值',
    remark varchar(256) comment '备注',
    is_valid varchar(1) default '1' comment '是否有效',
    creator varchar(32) comment '创建人',
    create_time timestamp default current_timestamp comment '创建时间',
    editor varchar(32) comment '修改人',
    update_time timestamp default current_timestamp on update current_timestamp comment '修改时间',
    primary key (id),
    unique key (dict_type_code, dict_code),
    key (update_time)
) engine = innodb comment = '数据字典表';
-- init data
insert into t_dict(dict_type_code, dict_code, dict_value, creator, editor) values ('gender', '0', '未知', 'sys', 'sys');
insert into t_dict(dict_type_code, dict_code, dict_value, creator, editor) values ('gender', '1', '男', 'sys', 'sys');
insert into t_dict(dict_type_code, dict_code, dict_value, creator, editor) values ('gender', '2', '女', 'sys', 'sys');
insert into t_dict(dict_type_code, dict_code, dict_value, creator, editor) values ('is_valid', '1', '是', 'sys', 'sys');
insert into t_dict(dict_type_code, dict_code, dict_value, creator, editor) values ('is_valid', '0', '否', 'sys', 'sys');
insert into t_dict(dict_type_code, dict_code, dict_value, creator, editor) values ('is_delete', '1', '是', 'sys', 'sys');
insert into t_dict(dict_type_code, dict_code, dict_value, creator, editor) values ('is_delete', '0', '否', 'sys', 'sys');
insert into t_dict(dict_type_code, dict_code, dict_value, creator, editor) values ('id_card_type', '111', '居民身份证', 'sys', 'sys');
insert into t_dict(dict_type_code, dict_code, dict_value, creator, editor) values ('id_card_type', '113', '户口簿', 'sys', 'sys');
insert into t_dict(dict_type_code, dict_code, dict_value, creator, editor) values ('id_card_type', '414', '普通护照', 'sys', 'sys');
insert into t_dict(dict_type_code, dict_code, dict_value, creator, editor) values ('nation', 'cn', '中国', 'sys', 'sys');
insert into t_dict(dict_type_code, dict_code, dict_value, creator, editor) values ('nation', 'us', '美国', 'sys', 'sys');
insert into t_dict(dict_type_code, dict_code, dict_value, creator, editor) values ('nation', 'gb', '英国', 'sys', 'sys');
insert into t_dict(dict_type_code, dict_code, dict_value, creator, editor) values ('nation', 'de', '德国', 'sys', 'sys');
insert into t_dict(dict_type_code, dict_code, dict_value, creator, editor) values ('user_status', '10', '未绑定邮箱', 'sys', 'sys');
insert into t_dict(dict_type_code, dict_code, dict_value, creator, editor) values ('user_status', '11', '已绑定邮箱', 'sys', 'sys');
insert into t_dict(dict_type_code, dict_code, dict_value, creator, editor) values ('user_status', '90', '禁用', 'sys', 'sys');
insert into t_dict(dict_type_code, dict_code, dict_value, creator, editor) values ('user_status', '91', '注销', 'sys', 'sys');
insert into t_dict(dict_type_code, dict_code, dict_value, creator, editor) values ('register_channel', '01', '用户注册', 'sys', 'sys');
insert into t_dict(dict_type_code, dict_code, dict_value, creator, editor) values ('register_channel', '02', '后台导入', 'sys', 'sys');
insert into t_dict(dict_type_code, dict_code, dict_value, creator, editor) values ('role_type', '01', '系统角色', 'sys', 'sys');
insert into t_dict(dict_type_code, dict_code, dict_value, creator, editor) values ('role_type', '02', '用户定义', 'sys', 'sys');
insert into t_dict(dict_type_code, dict_code, dict_value, creator, editor) values ('role_status', '1', '正常', 'sys', 'sys');
insert into t_dict(dict_type_code, dict_code, dict_value, creator, editor) values ('role_status', '0', '禁用', 'sys', 'sys');
insert into t_dict(dict_type_code, dict_code, dict_value, creator, editor) values ('dept_status', '1', '正常', 'sys', 'sys');
insert into t_dict(dict_type_code, dict_code, dict_value, creator, editor) values ('dept_status', '0', '禁用', 'sys', 'sys');
insert into t_dict(dict_type_code, dict_code, dict_value, creator, editor) values ('resource_type', '0', '菜单权限', 'sys', 'sys');
insert into t_dict(dict_type_code, dict_code, dict_value, creator, editor) values ('resource_type', '1', '操作权限', 'sys', 'sys');
insert into t_dict(dict_type_code, dict_code, dict_value, creator, editor) values ('resource_type', '2', '数据权限', 'sys', 'sys');
insert into t_dict(dict_type_code, dict_code, dict_value, creator, editor) values ('login_type', '0', '未知', 'sys', 'sys');
insert into t_dict(dict_type_code, dict_code, dict_value, creator, editor) values ('login_type', '1', '登录', 'sys', 'sys');
insert into t_dict(dict_type_code, dict_code, dict_value, creator, editor) values ('login_type', '2', '登出', 'sys', 'sys');

/*用户基本信息表 */
drop table if exists t_user;
create table t_user (
    id bigint not null auto_increment comment '自增主键',
    user_name varchar(60) not null comment '用户名',
    nick_name varchar(50) comment '昵称',
    email varchar(128) not null comment '邮箱',
    password varchar(64) not null comment '密码',
    real_name varchar(64) comment '真实姓名',
    id_card_type varchar(4) comment '证件类型',
    id_card_no varchar(18) comment '证件号码',
    gender varchar(4) default '0' comment '性别',
    nation varchar(4) comment '民族',
    birthday date comment '出生日期',
    qq varchar(18) comment 'qq号码',
    wechat varchar(64) comment '微信号码',
    mobile_phone varchar(16) comment '手机号码',
    user_status varchar(4) not null comment '用户状态',
    summary varchar(512) comment '用户简介',
    register_channel varchar(4) not null comment '注册渠道',
    register_time timestamp default current_timestamp comment '注册时间',
    register_ip varchar(16) comment '注册ip',
    creator varchar(32) comment '创建人',
    create_time timestamp default current_timestamp comment '创建时间',
    editor varchar(32) comment '修改人',
    update_time timestamp default current_timestamp on update current_timestamp comment '更新时间',
    primary key (id),
    unique key (user_name),
    unique key (email),
    unique key (mobile_phone),
    key (update_time)
) engine = innodb comment = '用户基本信息表';

/* 角色表 */
drop table if exists t_role;
create table t_role (
    id bigint not null auto_increment comment '角色id',
    role_code varchar(32) not null comment '角色编码',
    role_name varchar(64) not null comment '角色名称',
    role_type varchar(4) not null comment '角色类型',
    role_status varchar(4) not null comment '角色状态',
    role_desc varchar(128) comment '角色备注',
    creator varchar(32) comment '创建人',
    create_time timestamp default current_timestamp comment '创建时间',
    editor varchar(32) comment '修改人',
    update_time timestamp default current_timestamp on update current_timestamp comment '更新时间',
    primary key (id),
    unique key (role_code),
    key (update_time)
) engine = innodb comment = '角色表';

insert into t_role (role_code,role_name,role_type,role_status) values ('sys_super_admin','超级系统管理员','01','1') ;
insert into t_role (role_code,role_name,role_type,role_status) values ('sys_admin','系统管理员','01','1') ;
insert into t_role (role_code,role_name,role_type,role_status) values ('sys_normal','普通用户','01','1') ;


/* 权限表 */
drop table if exists t_privilege;
create table t_privilege (
    id bigint not null auto_increment comment '自增主键',
    privilege_code varchar(64) not null comment '权限标识',
    privilege_name varchar(128) not null comment '权限名称',
    resource_type varchar(4) default '1' comment '资源类型',
    resource_path varchar(64) comment '资源路径',
    pid bigint not null default '0' comment '上级权限id',
    creator varchar(32) comment '创建人',
    create_time timestamp default current_timestamp comment '创建时间',
    editor varchar(32) comment '修改人',
    update_time timestamp default current_timestamp on update current_timestamp comment '修改时间',
    primary key (id),
    unique key (privilege_code),
    key (update_time)
) engine = innodb comment = '权限表';
/* init privilege */
-- delete from t_privilege;
insert into t_privilege (id,privilege_code,privilege_name,resource_type,resource_path,pid,creator,editor) values(1,'padm0','系统管理','0','',0,'sys','sys');
insert into t_privilege (id,privilege_code,privilege_name,resource_type,resource_path,pid,creator,editor) values(2,'pusr0','用户管理','0','',1,'sys','sys');
insert into t_privilege (id,privilege_code,privilege_name,resource_type,resource_path,pid,creator,editor) values(3,'ppvg0','权限管理','0','',1,'sys','sys');
insert into t_privilege (id,privilege_code,privilege_name,resource_type,resource_path,pid,creator,editor) values(4,'pdic0','数据字典','0','',1,'sys','sys');
insert into t_privilege (id,privilege_code,privilege_name,resource_type,resource_path,pid,creator,editor) values(5,'pset0','系统设置','0','',1,'sys','sys');
insert into t_privilege (id,privilege_code,privilege_name,resource_type,resource_path,pid,creator,editor) values(100001,'pdct4','字典类型','1','',0,'sys','sys');
insert into t_privilege (id,privilege_code,privilege_name,resource_type,resource_path,pid,creator,editor) values(100002,'pdct1','新增字典类型','1','',100001,'sys','sys');
insert into t_privilege (id,privilege_code,privilege_name,resource_type,resource_path,pid,creator,editor) values(100003,'pdct3','删除字典类型','1','',100001,'sys','sys');
insert into t_privilege (id,privilege_code,privilege_name,resource_type,resource_path,pid,creator,editor) values(100004,'pdct2','修改字典类型','1','',100001,'sys','sys');
insert into t_privilege (id,privilege_code,privilege_name,resource_type,resource_path,pid,creator,editor) values(100005,'pdcd4','数据字典','1','',0,'sys','sys');
insert into t_privilege (id,privilege_code,privilege_name,resource_type,resource_path,pid,creator,editor) values(100006,'pdcd1','新增数据字典','1','',100005,'sys','sys');
insert into t_privilege (id,privilege_code,privilege_name,resource_type,resource_path,pid,creator,editor) values(100007,'pdcd3','删除数据字典','1','',100005,'sys','sys');
insert into t_privilege (id,privilege_code,privilege_name,resource_type,resource_path,pid,creator,editor) values(100008,'pdcd2','修改数据字典','1','',100005,'sys','sys');
insert into t_privilege (id,privilege_code,privilege_name,resource_type,resource_path,pid,creator,editor) values(100009,'pusr4','用户管理','1','',0,'sys','sys');
insert into t_privilege (id,privilege_code,privilege_name,resource_type,resource_path,pid,creator,editor) values(100010,'pusr1','新增用户管理','1','',100009,'sys','sys');
insert into t_privilege (id,privilege_code,privilege_name,resource_type,resource_path,pid,creator,editor) values(100011,'pusr3','删除用户管理','1','',100009,'sys','sys');
insert into t_privilege (id,privilege_code,privilege_name,resource_type,resource_path,pid,creator,editor) values(100012,'pusr2','修改用户管理','1','',100009,'sys','sys');
insert into t_privilege (id,privilege_code,privilege_name,resource_type,resource_path,pid,creator,editor) values(100013,'prol4','角色管理','1','',0,'sys','sys');
insert into t_privilege (id,privilege_code,privilege_name,resource_type,resource_path,pid,creator,editor) values(100014,'prol1','新增角色管理','1','',100013,'sys','sys');
insert into t_privilege (id,privilege_code,privilege_name,resource_type,resource_path,pid,creator,editor) values(100015,'prol3','删除角色管理','1','',100013,'sys','sys');
insert into t_privilege (id,privilege_code,privilege_name,resource_type,resource_path,pid,creator,editor) values(100016,'prol2','修改角色管理','1','',100013,'sys','sys');
insert into t_privilege (id,privilege_code,privilege_name,resource_type,resource_path,pid,creator,editor) values(100017,'prol5','角色授权管理','1','',100013,'sys','sys');
insert into t_privilege (id,privilege_code,privilege_name,resource_type,resource_path,pid,creator,editor) values(100018,'pdep4','部门管理','1','',0,'sys','sys');
insert into t_privilege (id,privilege_code,privilege_name,resource_type,resource_path,pid,creator,editor) values(100019,'pdep1','新增部门管理','1','',100018,'sys','sys');
insert into t_privilege (id,privilege_code,privilege_name,resource_type,resource_path,pid,creator,editor) values(100020,'pdep3','删除部门管理','1','',100018,'sys','sys');
insert into t_privilege (id,privilege_code,privilege_name,resource_type,resource_path,pid,creator,editor) values(100021,'pdep2','修改部门管理','1','',100018,'sys','sys');
insert into t_privilege (id,privilege_code,privilege_name,resource_type,resource_path,pid,creator,editor) values(100022,'pdep5','部门授权管理','1','',100018,'sys','sys');

/* 角色权限关联表 */
drop table if exists t_role_privilege;

create table t_role_privilege (
    id bigint not null auto_increment comment '自增主键',
    role_id bigint not null comment '角色id',
    privilege_id bigint not null comment '权限id',
    creator varchar(32) comment '创建人',
    create_time timestamp default current_timestamp comment '创建时间',
    editor varchar(32) comment '修改人',
    update_time timestamp default current_timestamp on update current_timestamp comment '修改时间',
    primary key (id),
    unique key (role_id, privilege_id),
    key (update_time)
) engine = innodb comment = '角色权限关联表';

/* 部门表 */
drop table if exists t_dept;
create table t_dept (
    id bigint not null auto_increment comment '部门id',
    dept_code varchar(32) not null comment '部门编号',
    dept_name varchar(64) not null comment '部门名称',
    pid bigint not null default '0' comment '上级部门',
    dept_status varchar(1) not null default '1' comment '部门状态',
    creator varchar(32) comment '创建人',
    create_time timestamp default current_timestamp comment '创建时间',
    editor varchar(32) comment '修改人',
    update_time timestamp default current_timestamp on update current_timestamp comment '修改时间',
    primary key (id),
    unique (dept_code),
    unique (dept_name),
    key (pid)
) engine = innodb comment = '部门表';

/*用户和部门关联表 */
drop table if exists t_user_dept;
create table t_user_dept (
    id bigint not null auto_increment comment '自增主键',
    user_id bigint not null comment '用户id',
    dept_id bigint not null comment '部门id',
    creator varchar(32) comment '创建人',
    create_time timestamp default current_timestamp comment '创建时间',
    editor varchar(32) comment '修改人',
    update_time timestamp default current_timestamp on update current_timestamp comment '修改时间',
    primary key (id),
    unique key (dept_id, user_id),
    key (update_time)
) engine = innodb comment = '用户和部门关联表';

/* 用户角色关联表 */
drop table if exists t_user_role;
create table t_user_role (
    id bigint not null auto_increment comment '自增主键',
    user_id bigint not null comment '用户id',
    role_id bigint not null comment '角色id',
    creator varchar(32) comment '创建人',
    create_time timestamp default current_timestamp comment '创建时间',
    editor varchar(32) comment '修改人',
    update_time timestamp default current_timestamp on update current_timestamp comment '更新时间',
    primary key (id),
    unique key (user_id, role_id),
    key (update_time)
) engine = innodb comment = '用户角色关联表';

/* 部门角色关联表 */
drop table if exists t_dept_role;
create table t_dept_role (
    id bigint not null auto_increment comment '自增主键',
    dept_id bigint not null comment '部门id',
    role_id bigint not null comment '角色id',
    creator varchar(32) comment '创建人',
    create_time timestamp default current_timestamp comment '创建时间',
    editor varchar(32) comment '修改人',
    update_time timestamp default current_timestamp on update current_timestamp comment '修改时间',
    primary key (id),
    unique key (dept_id, role_id),
    key (update_time)
) engine = innodb comment = '部门角色关联表';

/* 用户登录登出日志 */
drop table if exists t_log_login;

create table t_log_login (
    id bigint auto_increment comment '自增主键',
    user_name varchar(60) comment '用户名',
    login_time timestamp not null comment '登录时间',
    ip_address varchar(16) comment 'ip地址',
    login_type varchar(4) not null comment '登录类型 1-登录，2-登出，0-未知',
    client_info varchar(512) comment '客户端信息',
    os_info varchar(128) comment '操作系统信息',
    browser_info varchar(512) comment '浏览器信息',
    action_info text comment '接口执行信息，包含请求结果',
    creator varchar(32) comment '创建人',
    create_time timestamp default current_timestamp comment '创建时间',
    editor varchar(32) comment '修改人',
    update_time timestamp default current_timestamp on update current_timestamp comment '修改时间',
    primary key (id),
    key (update_time),
    key (ip_address),
    key (login_time)
) engine = innodb comment = '用户登录登出日志' comment '用户登录登出日志';

/* 用户操作日志 */
drop table if exists t_log_action;

create table t_log_action (
    id bigint auto_increment comment '自增主键',
    user_name varchar(60) comment '用户名',
    action_time timestamp not null comment '操作时间',
    ip_address varchar(16) comment 'ip地址',
    action_url varchar(128) not null comment '操作接口地址',
    token varchar(64) comment '会话token字符串',
    client_info varchar(512) comment '客户端信息',
    os_info varchar(128) comment '操作系统信息',
    browser_info varchar(512) comment '浏览器信息',
    action_info text comment '接口执行信息，包含请求结果',
    creator varchar(32) comment '创建人',
    create_time timestamp default current_timestamp comment '创建时间',
    editor varchar(32) comment '修改人',
    update_time timestamp default current_timestamp on update current_timestamp comment '修改时间',
    primary key (id),
    key (update_time),
    key (ip_address),
    key (action_time)
) engine = innodb comment = '用户操作日志';


/*站内信内容表 */
drop table if exists t_message_text;

create table t_message_text (
    id bigint auto_increment comment '自增主键',
    title varchar(128) not null default '' comment '标题',
    message_type varchar(4) not null comment '消息类型',
    sender varchar(32) not null comment '发送人',
    content longtext comment '内容',
    creator varchar(32) comment '创建人',
    create_time timestamp default current_timestamp comment '创建时间',
    editor varchar(32) comment '修改人',
    update_time timestamp default current_timestamp on update current_timestamp comment '修改时间',
    primary key (id),
    key (message_type),
    key (sender),
    key (update_time)
) engine = innodb comment = '站内信内容表';

/*站内信表 */
drop table if exists t_message;

create table t_message (
    id bigint auto_increment comment '自增主键',
    message_text_id bigint not null comment '消息内容id',
    receiver varchar(32) not null comment '收件人',
    is_read varchar(1) not null default '0' comment '是否已读',
    is_delete varchar(1) not null default '0' comment '是否删除',
    creator varchar(32) comment '创建人',
    create_time timestamp default current_timestamp comment '创建时间',
    editor varchar(32) comment '修改人',
    update_time timestamp default current_timestamp on update current_timestamp comment '修改时间',
    primary key (id),
    key (message_text_id),
    key (receiver),
    key (update_time)
) engine = innodb comment = '站内信表';

/*用户邮箱激活日志表*/
drop table if exists t_user_active;
create table t_user_active (
    id bigint not null auto_increment comment '自增主键',
    user_name varchar(60) not null comment '用户名',
    active_code varchar(36) not null comment '激活码',
    expiry_time bigint not null comment '激活码过期时间戳',
    active_time timestamp comment '激活时间',
    creator varchar(32) comment '创建人',
    create_time timestamp default current_timestamp comment '创建时间',
    editor varchar(32) comment '修改人',
    update_time timestamp default current_timestamp on update current_timestamp comment '修改时间',
    primary key (id),
    unique key (active_code),
    key (user_name),
    key (update_time)
) engine = innodb comment = '用户邮箱激活日志表';