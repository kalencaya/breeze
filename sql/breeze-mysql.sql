create database if not exists breeze default character set utf8mb4;
use breeze;
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
insert into t_dict_type(dict_type_code, dict_type_name, creator, editor) values ('yes_or_no', '是否', 'sys', 'sys');
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
insert into t_dict_type(dict_type_code, dict_type_name, creator, editor) values ('message_type', '消息类型', 'sys', 'sys');
insert into t_dict_type(dict_type_code, dict_type_name, creator, editor) values ('task_result', '任务运行结果', 'sys', 'sys');
insert into t_dict_type(dict_type_code, dict_type_name, creator, editor) values ('datasource_type', '数据源类型', 'sys', 'sys');
insert into t_dict_type(dict_type_code, dict_type_name, creator, editor) values ('connection_type', '连接类型', 'sys', 'sys');


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
insert into t_dict(dict_type_code, dict_code, dict_value, creator, editor) values ('yes_or_no', '1', '是', 'sys', 'sys');
insert into t_dict(dict_type_code, dict_code, dict_value, creator, editor) values ('yes_or_no', '0', '否', 'sys', 'sys');
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
insert into t_dict(dict_type_code, dict_code, dict_value, creator, editor) values ('message_type', '1', '系统消息', 'sys', 'sys');
insert into t_dict(dict_type_code, dict_code, dict_value, creator, editor) values ('task_result', 'success', '成功', 'sys', 'sys');
insert into t_dict(dict_type_code, dict_code, dict_value, creator, editor) values ('task_result', 'failure', '失败', 'sys', 'sys');
insert into t_dict(dict_type_code, dict_code, dict_value, creator, editor) values ('datasource_type', 'mysql', 'Mysql', 'sys', 'sys');
insert into t_dict(dict_type_code, dict_code, dict_value, creator, editor) values ('datasource_type', 'oracle', 'Oracle', 'sys', 'sys');
insert into t_dict(dict_type_code, dict_code, dict_value, creator, editor) values ('connection_type', 'jdbc', 'SIMPLE JDBC', 'sys', 'sys');
insert into t_dict(dict_type_code, dict_code, dict_value, creator, editor) values ('connection_type', 'pooled', 'CONNECTION POOL', 'sys', 'sys');
insert into t_dict(dict_type_code, dict_code, dict_value, creator, editor) values ('connection_type', 'jndi', 'JNDI', 'sys', 'sys');



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
-- init data
insert into t_user (id,user_name,nick_name,email,password,real_name,id_card_type,id_card_no,gender,nation,birthday,qq,wechat,mobile_phone,user_status,summary,register_channel,register_time,register_ip,creator,editor) VALUES (1,'sys_admin','超级管理员','test@admin.com','$2a$10$QX2DBrOBGLuhEmboliW66ulvQ5Hiy9GCdhsqqs1HgJVgslYhZEC6q',NULL,NULL,NULL,'0',NULL,NULL,NULL,NULL,NULL,'10',NULL,'01','2021-12-25 21:51:17','127.0.0.1','sys','sys');


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
insert into t_privilege (id,privilege_code,privilege_name,resource_type,resource_path,pid,creator,editor) values(6,'pmta0','元数据','0','',0,'sys','sys');
insert into t_privilege (id,privilege_code,privilege_name,resource_type,resource_path,pid,creator,editor) values(7,'pdts0','数据源','0','',6,'sys','sys');
insert into t_privilege (id,privilege_code,privilege_name,resource_type,resource_path,pid,creator,editor) values(8,'pmrd0','参考数据','0','',6,'sys','sys');
insert into t_privilege (id,privilege_code,privilege_name,resource_type,resource_path,pid,creator,editor) values(9,'pmde0','数据元','0','',6,'sys','sys');
insert into t_privilege (id,privilege_code,privilege_name,resource_type,resource_path,pid,creator,editor) values(10,'pmst0','业务系统','0','',6,'sys','sys');
insert into t_privilege (id,privilege_code,privilege_name,resource_type,resource_path,pid,creator,editor) values(100001,'pdct4','字典类型','1','',0,'sys','sys');
insert into t_privilege (id,privilege_code,privilege_name,resource_type,resource_path,pid,creator,editor) values(100002,'pdct1','新增字典类型','1','',100001,'sys','sys');
insert into t_privilege (id,privilege_code,privilege_name,resource_type,resource_path,pid,creator,editor) values(100003,'pdct3','删除字典类型','1','',100001,'sys','sys');
insert into t_privilege (id,privilege_code,privilege_name,resource_type,resource_path,pid,creator,editor) values(100004,'pdct2','修改字典类型','1','',100001,'sys','sys');
insert into t_privilege (id,privilege_code,privilege_name,resource_type,resource_path,pid,creator,editor) values(100005,'pdcd4','数据字典','1','',0,'sys','sys');
insert into t_privilege (id,privilege_code,privilege_name,resource_type,resource_path,pid,creator,editor) values(100006,'pdcd1','新增数据字典','1','',100005,'sys','sys');
insert into t_privilege (id,privilege_code,privilege_name,resource_type,resource_path,pid,creator,editor) values(100007,'pdcd3','删除数据字典','1','',100005,'sys','sys');
insert into t_privilege (id,privilege_code,privilege_name,resource_type,resource_path,pid,creator,editor) values(100008,'pdcd2','修改数据字典','1','',100005,'sys','sys');
insert into t_privilege (id,privilege_code,privilege_name,resource_type,resource_path,pid,creator,editor) values(100009,'pusr4','用户管理','1','',0,'sys','sys');
insert into t_privilege (id,privilege_code,privilege_name,resource_type,resource_path,pid,creator,editor) values(100010,'pusr1','新增用户','1','',100009,'sys','sys');
insert into t_privilege (id,privilege_code,privilege_name,resource_type,resource_path,pid,creator,editor) values(100011,'pusr3','删除用户','1','',100009,'sys','sys');
insert into t_privilege (id,privilege_code,privilege_name,resource_type,resource_path,pid,creator,editor) values(100012,'pusr2','修改用户','1','',100009,'sys','sys');
insert into t_privilege (id,privilege_code,privilege_name,resource_type,resource_path,pid,creator,editor) values(100013,'prol4','角色管理','1','',0,'sys','sys');
insert into t_privilege (id,privilege_code,privilege_name,resource_type,resource_path,pid,creator,editor) values(100014,'prol1','新增角色','1','',100013,'sys','sys');
insert into t_privilege (id,privilege_code,privilege_name,resource_type,resource_path,pid,creator,editor) values(100015,'prol3','删除角色','1','',100013,'sys','sys');
insert into t_privilege (id,privilege_code,privilege_name,resource_type,resource_path,pid,creator,editor) values(100016,'prol2','修改角色','1','',100013,'sys','sys');
insert into t_privilege (id,privilege_code,privilege_name,resource_type,resource_path,pid,creator,editor) values(100017,'prol5','角色授权','1','',100013,'sys','sys');
insert into t_privilege (id,privilege_code,privilege_name,resource_type,resource_path,pid,creator,editor) values(100018,'pdep4','部门管理','1','',0,'sys','sys');
insert into t_privilege (id,privilege_code,privilege_name,resource_type,resource_path,pid,creator,editor) values(100019,'pdep1','新增部门','1','',100018,'sys','sys');
insert into t_privilege (id,privilege_code,privilege_name,resource_type,resource_path,pid,creator,editor) values(100020,'pdep3','删除部门','1','',100018,'sys','sys');
insert into t_privilege (id,privilege_code,privilege_name,resource_type,resource_path,pid,creator,editor) values(100021,'pdep2','修改部门','1','',100018,'sys','sys');
insert into t_privilege (id,privilege_code,privilege_name,resource_type,resource_path,pid,creator,editor) values(100022,'pdep5','部门授权','1','',100018,'sys','sys');
insert into t_privilege (id,privilege_code,privilege_name,resource_type,resource_path,pid,creator,editor) values(100023,'pdts4','数据源','1','',0,'sys','sys');
insert into t_privilege (id,privilege_code,privilege_name,resource_type,resource_path,pid,creator,editor) values(100024,'pdts1','新增数据源','1','',100023,'sys','sys');
insert into t_privilege (id,privilege_code,privilege_name,resource_type,resource_path,pid,creator,editor) values(100025,'pdts3','删除数据源','1','',100023,'sys','sys');
insert into t_privilege (id,privilege_code,privilege_name,resource_type,resource_path,pid,creator,editor) values(100026,'pdts2','修改数据源','1','',100023,'sys','sys');
insert into t_privilege (id,privilege_code,privilege_name,resource_type,resource_path,pid,creator,editor) values(100027,'pdts6','查看密码','1','',100023,'sys','sys');
insert into t_privilege (id,privilege_code,privilege_name,resource_type,resource_path,pid,creator,editor) values(100028,'pmst4','业务系统','1','',0,'sys','sys');
insert into t_privilege (id,privilege_code,privilege_name,resource_type,resource_path,pid,creator,editor) values(100029,'pmst1','新增业务系统','1','',100028,'sys','sys');
insert into t_privilege (id,privilege_code,privilege_name,resource_type,resource_path,pid,creator,editor) values(100030,'pmst3','删除业务系统','1','',100028,'sys','sys');
insert into t_privilege (id,privilege_code,privilege_name,resource_type,resource_path,pid,creator,editor) values(100031,'pmst2','修改业务系统','1','',100028,'sys','sys');

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

-- init data
insert into t_user_role (id,user_id,role_id,creator,editor) VALUES (1,1,1,'sys','sys');
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


/*站内信表 */
drop table if exists t_message;
create table t_message (
    id bigint auto_increment comment '自增主键',
    title varchar(128) not null default '' comment '标题',
    message_type varchar(4) not null comment '消息类型',
    receiver varchar(32) not null comment '收件人',
    sender varchar(32) not null comment '发送人',
    content longtext comment '内容',
    is_read varchar(1) not null default '0' comment '是否已读',
    is_delete varchar(1) not null default '0' comment '是否删除',
    creator varchar(32) comment '创建人',
    create_time timestamp default current_timestamp comment '创建时间',
    editor varchar(32) comment '修改人',
    update_time timestamp default current_timestamp on update current_timestamp comment '修改时间',
    primary key (id),
    key (receiver,message_type),
    key (sender,message_type),
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

/*系统配置信息表 */
drop table if exists t_system_config;
create table t_system_config(
    id bigint not null auto_increment comment '自增主键',
    cfg_code varchar(60) not null comment '配置编码',
    cfg_value text comment '配置信息',
    creator varchar(32) comment '创建人',
    create_time timestamp default current_timestamp comment '创建时间',
    editor varchar(32) comment '修改人',
    update_time timestamp default current_timestamp on update current_timestamp comment '修改时间',
    primary key (id),
    unique key (cfg_code)
) engine = innodb comment = '系统配置信息表' ;

/*定时任务运行日志表*/
drop table if exists t_schedule_log;
create table t_schedule_log
(
    id bigint auto_increment comment '自增主键',
    task_group varchar(128) not null comment '任务组',
    task_name varchar(128) not null comment '任务名称',
    start_time datetime comment '开始时间',
    end_time datetime comment '结束时间',
    trace_log longtext comment '日志内容明细',
    result varchar(12) comment '任务结果 成功/失败',
    creator varchar(32) comment '创建人',
    create_time timestamp default current_timestamp comment '创建时间',
    editor varchar(32) comment '修改人',
    update_time timestamp default current_timestamp on update current_timestamp comment '修改时间',
    primary key(id),
    key(task_name,task_group),
    key(start_time),
    key(end_time),
    key(update_time)
) engine = innodb comment '定时任务运行日志表';


/* 元数据-数据源连接信息 */
drop table if exists meta_datasource;
create table meta_datasource (
    id bigint auto_increment comment '自增主键',
    datasource_name varchar(64) not null comment '数据源名称',
    datasource_type varchar(12) not null comment '数据源类型',
    connection_type varchar(12) not null comment '数据源连接类型',
    host_name varchar(256) comment '主机地址',
    database_name varchar(50) comment '数据库名称',
    port int comment '端口号',
    user_name varchar(128) comment '用户名',
    password varchar(256) comment '密码',
    remark varchar(256) comment '备注描述',
    props text comment '属性信息，包含常规属性、jdbc连接属性、连接池属性',
    creator varchar(32) comment '创建人',
    create_time timestamp default current_timestamp comment '创建时间',
    editor varchar(32) comment '修改人',
    update_time timestamp default current_timestamp on update current_timestamp comment '修改时间',
    primary key (id),
    unique key (datasource_name),
    key (datasource_type),
    key (database_name),
    key (update_time)
) engine = innodb comment '元数据-数据源连接信息';

/*元数据-数据表信息*/
drop table if exists meta_table;
create table meta_table (
    id bigint auto_increment comment '自增主键',
    datasource_id bigint not null comment '数据源id',
    table_catalog varchar(64) comment '表目录',
    table_schema varchar(64) not null comment '表模式',
    table_name varchar(128) not null comment '表名',
    table_type varchar(12) not null default 'TABLE' comment '表类型',
    table_space VARCHAR(64) comment '表空间',
    table_comment VARCHAR(1024) COMMENT '表描述',
    table_rows bigint comment '表数据行数',
    data_bytes bigint comment '数据空间大小，单位(byte)',
    index_bytes bigint comment '索引空间大小，单位(byte)',
    table_create_time datetime comment '表创建时间',
    last_ddl_time datetime comment '最后ddl操作时间',
    last_access_time datetime comment '最后数据访问时间',
    life_cycle int comment '生命周期，单位(天)',
    is_partitioned varchar(1) default '0' comment '是否分区表,0否1是',
    attrs varchar(1024) comment '表扩展属性',
    creator varchar(32) comment '创建人',
    create_time timestamp default current_timestamp comment '创建时间',
    editor varchar(32) comment '修改人',
    update_time timestamp default current_timestamp on update current_timestamp comment '修改时间',
    primary key (id),
    unique (datasource_id, table_schema, table_name),
    key (update_time)
) engine = innodb comment '元数据-数据表信息';

/*元数据-数据表字段信息*/
drop table if exists meta_column;
create table meta_column (
    id bigint auto_increment comment '自增主键',
    table_id bigint not null comment '数据表id',
    column_name varchar(64) not null comment '列名',
    data_type varchar(32) not null comment '数据类型',
    data_length bigint comment '长度',
    data_precision int comment '数据精度，有效位',
    data_scale int comment '小数位数',
    nullable varchar(1) not null default '0' comment '是否可以为空,1-是;0-否',
    data_default varchar(512) comment '默认值',
    low_value varchar(512) comment '最小值',
    high_value varchar(512) comment '最大值',
    column_ordinal int comment '列顺序',
    column_comment varchar(1024) comment '列描述',
    is_primary_key varchar(4) comment '是否主键',
    creator varchar(32) comment '创建人',
    create_time timestamp default current_timestamp comment '创建时间',
    editor varchar(32) comment '修改人',
    update_time timestamp default current_timestamp on update current_timestamp comment '修改时间',
    primary key (id),
    unique (table_id, column_name),
    key (update_time)
) engine = innodb comment '元数据-数据表字段信息';

/* 元数据-数据元信息 */
drop table if exists meta_data_element;
create table meta_data_element (
    id bigint auto_increment comment '自增主键',
    element_code varchar(32) not null comment '数据元标识',
    element_name varchar(256) not null comment '数据元名称',
    data_type varchar(10) not null comment '数据类型',
    data_length bigint comment '长度',
    data_precision int comment '数据精度，有效位',
    data_scale int comment '小数位数',
    nullable varchar(1) not null default '0' comment '是否可以为空,1-是;0-否',
    data_default varchar(512) comment '默认值',
    low_value varchar(512) comment '最小值',
    high_value varchar(512) comment '最大值',
    data_set_type_id bigint comment '代码集类型id',
    creator varchar(32) comment '创建人',
    create_time timestamp default current_timestamp comment '创建时间',
    editor varchar(32) comment '修改人',
    update_time timestamp default current_timestamp on update current_timestamp comment '修改时间',
    primary key (id),
    unique key (element_code),
    key (element_name),
    key (update_time)
) engine = innodb comment '元数据-数据元信息';

/* 元数据-业务系统信息 */
drop table if exists meta_system;
create table meta_system (
    id bigint auto_increment comment '系统id',
    system_code varchar(32) not null comment '系统编码',
    system_name varchar(128) not null comment '系统名称',
    contacts varchar(24) comment '联系人',
    contacts_phone varchar(15) comment '联系人手机号码',
    remark varchar(256) comment '备注',
    creator varchar(32) comment '创建人',
    create_time timestamp default current_timestamp comment '创建时间',
    editor varchar(32) comment '修改人',
    update_time timestamp default current_timestamp on update current_timestamp comment '修改时间',
    primary key (id),
    unique key (system_code),
    key (update_time)
) engine = innodb comment '元数据-业务系统信息';

/* 元数据-参考数据类型 */
drop table if exists meta_data_set_type;
create table meta_data_set_type (
    id bigint auto_increment comment '自增主键',
    data_set_type_code varchar(32) not null comment '代码集类型编码',
    data_set_type_name varchar(128) not null comment '代码集类型名称',
    remark varchar(256) comment '备注',
    creator varchar(32) comment '创建人',
    create_time timestamp default current_timestamp comment '创建时间',
    editor varchar(32) comment '修改人',
    update_time timestamp default current_timestamp on update current_timestamp comment '修改时间',
    primary key (id),
    unique key (data_set_type_code),
    key (data_set_type_name),
    key (update_time)
) engine = innodb comment '元数据-参考数据类型';

/* 元数据-参考数据 */
drop table if exists meta_data_set;
create table meta_data_set (
    id bigint auto_increment comment '自增主键',
    data_set_type_id bigint not null comment '数据代码集类型id',
    data_set_code varchar(32) not null comment '代码code',
    data_set_value varchar(128) not null comment '代码值',
    system_id bigint comment '业务系统id',
    is_standard varchar(1) not null comment '是否标准代码集',
    remark varchar(256) comment '备注',
    creator varchar(32) comment '创建人',
    create_time timestamp default current_timestamp comment '创建时间',
    editor varchar(32) comment '修改人',
    update_time timestamp default current_timestamp on update current_timestamp comment '修改时间',
    primary key (id),
    unique key (data_set_code, data_set_type_id, system_id),
    key (update_time)
) engine = innodb comment '元数据-参考数据';

/* 元数据-参考数据映射 */
drop table if exists meta_data_map;
create table meta_data_map (
    id bigint auto_increment comment '自增主键',
    src_data_set_id bigint not null comment '原始数据代码',
    tgt_data_set_id bigint not null comment '目标数据代码',
    remark varchar(256) comment '备注',
    creator varchar(32) comment '创建人',
    create_time timestamp default current_timestamp comment '创建时间',
    editor varchar(32) comment '修改人',
    update_time timestamp default current_timestamp on update current_timestamp comment '修改时间',
    primary key (id),
    unique (src_data_set_id, tgt_data_set_id),
    key (tgt_data_set_id),
    key (update_time)
) engine = innodb comment '元数据-参考数据映射';

/* 参考数据视图 */
create or replace view v_meta_data_map as
select
    lower(st1.data_set_type_code) as code_type,
    st1.data_set_type_name as code_type_name,
    s1.data_set_code as src_code_value,
    s1.data_set_value as src_code_value_name,
    st2.data_set_type_code as tgt_code_type,
    st2.data_set_type_name as tgt_code_type_name,
    s2.data_set_code as tgt_code_value,
    s2.data_set_value as tgt_code_value_name,
    t.remark as description,
    t.creator as creator,
    t.create_time as create_time,
    t.editor as editor,
    t.update_time as editor_time
from meta_data_map t
join meta_data_set s1
on t.src_data_set_id = s1.id
join meta_data_set_type st1
on s1.data_set_type_id = st1.id
join meta_data_set s2
on t.tgt_data_set_id = s2.id
join meta_data_set_type st2
on s2.data_set_type_id = st2.id
union
select
    lower(st1.data_set_type_code) as code_type,
    st1.data_set_type_name as code_type_name,
    s1.data_set_code as src_code_value,
    s1.data_set_value as src_code_value_name,
    st1.data_set_type_code as tgt_code_type,
    st1.data_set_type_name as tgt_code_type_name,
    s1.data_set_code as tgt_code_value,
    s1.data_set_value as tgt_code_value_name,
    '' as description,
    s1.creator as creator,
    s1.create_time as create_time,
    s1.editor as editor,
    s1.update_time as editor_time
from meta_data_set s1
join meta_data_set_type st1
on s1.data_set_type_id = st1.id
where not exists (select 1 from meta_data_map t1 where t1.src_data_set_id = s1.id)
and trim(s1.data_set_code) <> ''