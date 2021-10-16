//package com.liyu.breeze.service.util;
//
//
//import com.alibaba.druid.pool.DruidDataSource;
//import com.alibaba.druid.pool.DruidDataSourceFactory;
//import com.magicdt.common.enums.ConnectionTypeEnum;
//import com.magicdt.common.enums.DataSourceEnum;
//import com.magicdt.common.enums.OperateInfoEnum;
//import com.magicdt.common.exception.CustomException;
//import com.magicdt.meta.database.AbstractDatabaseMeta;
//import com.magicdt.meta.database.MysqlDatabaseMeta;
//import com.magicdt.meta.database.OdpsDatabaseMeta;
//import com.magicdt.meta.database.OracleDatabaseMeta;
//import com.magicdt.service.dto.DataSourceMetaAttributeDTO;
//import com.magicdt.service.dto.DataSourceMetaDTO;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.util.DigestUtils;
//
//import javax.sql.DataSource;
//import java.sql.*;
//import java.util.Properties;
//import java.util.concurrent.ConcurrentHashMap;
//import java.util.concurrent.ConcurrentMap;
//
///**
// * JDBC工具类
// *
// * @author gleiyu
// */
//public class JdbcUtil {
//
//    private static final Logger log = LoggerFactory.getLogger(JdbcUtil.class);
//    /**
//     * datasource map
//     */
//    private static final ConcurrentMap<String, DruidDataSource> DATA_SOURCES = new ConcurrentHashMap<>();
//
//    /**
//     * 测试元数据信息是否可以连接到数据库
//     *
//     * @param meta 数据源信息
//     * @return boolean
//     * @throws Exception Exception
//     */
//    public static boolean testConnection(AbstractDatabaseMeta meta) throws Exception {
//        Connection conn = null;
//        if (meta != null && meta.getDataSourceMeta() != null) {
//            DataSourceMetaDTO dsMeta = meta.getDataSourceMeta();
//            //阿里云odps
//            if (DataSourceEnum.ODPS.getCode().equals(dsMeta.getDataSourceType().getCode())) {
//                return OdpsUtil.testConnection(dsMeta.getUserName(), dsMeta.getPassword(), dsMeta.getHostName(), dsMeta.getDatabaseName());
//            }
//            //jdbc rdbms
//            if (ConnectionTypeEnum.JDBC.getCode().equals(dsMeta.getConnectionType().getCode())) {
//                conn = getConnection(meta, ConnectionTypeEnum.JDBC);
//            } else if (ConnectionTypeEnum.POOLED.getCode().equals(dsMeta.getConnectionType().getCode())) {
//                conn = getConnection(meta, ConnectionTypeEnum.POOLED);
//            }
//            return conn != null;
//        } else {
//            return false;
//        }
//    }
//
//    public static AbstractDatabaseMeta getDataBaseMetaByType(DataSourceMetaDTO dsMeta) throws CustomException {
//        if (dsMeta == null || dsMeta.getDataSourceType() == null) {
//            throw new CustomException(OperateInfoEnum.ERROR_UNSUPPORTED_CONNECTION.getValue());
//        }
//        String dataSourceTypeCode = dsMeta.getDataSourceType().getCode();
//        if (DataSourceEnum.MYSQL.getCode().equals(dataSourceTypeCode)) {
//            return new MysqlDatabaseMeta(dsMeta);
//        } else if (DataSourceEnum.ORACLE.getCode().equals(dataSourceTypeCode)) {
//            return new OracleDatabaseMeta(dsMeta);
//        } else if (DataSourceEnum.ODPS.getCode().equals(dataSourceTypeCode)) {
//            return new OdpsDatabaseMeta(dsMeta);
//        } else {
//            throw new CustomException(OperateInfoEnum.ERROR_UNSUPPORTED_CONNECTION.getValue());
//        }
//    }
//
//    /**
//     * 获取数据库连接，如出现异常则捕获
//     *
//     * @param dataSource 数据源信息
//     * @return Connection
//     */
//    public static Connection getConnectionSilently(DataSource dataSource) {
//        try {
//            return dataSource.getConnection();
//        } catch (SQLException e) {
//            log.error("获取数据库连接异常！");
//            return null;
//        }
//    }
//
//    /**
//     * 使用默认jdbc获取连接
//     *
//     * @param meta 元数据信息
//     * @return connection
//     */
//    public static Connection getConnectionSilently(AbstractDatabaseMeta meta) {
//        return getConnectionSilently(meta, ConnectionTypeEnum.JDBC);
//    }
//
//    /**
//     * 获取数据库连接，如出现异常则捕获
//     *
//     * @param meta 数据源信息
//     * @param type 连接方式
//     * @return Connection
//     */
//    public static Connection getConnectionSilently(AbstractDatabaseMeta meta, ConnectionTypeEnum type) {
//        switch (type) {
//            case POOLED:
//                log.info("使用连接池创建连接");
//                DataSource dataSource = getDataSource(meta);
//                return getConnectionSilently(dataSource);
//            case JDBC:
//                log.info("使用jdbc创建连接");
//                try {
//                    Class.forName(meta.getDriverClass());
//                    Properties props = new Properties();
//                    for (DataSourceMetaAttributeDTO attr : meta.getAttributes()) {
//                        props.put(attr.getAttrCode(), attr.getAttrValue());
//                    }
//                    props.put("user", meta.getDataSourceMeta().getUserName());
//                    props.put("password", meta.getDataSourceMeta().getPassword());
//                    return DriverManager.getConnection(meta.getUrl(), props);
//                } catch (SQLException | ClassNotFoundException e) {
//                    log.error(e.getMessage());
//                }
//                break;
//            default:
//                log.error("不支持的数据源类型");
//                return null;
//        }
//        return null;
//    }
//
//    /**
//     * 获取数据库连接,如出现异常则抛出
//     *
//     * @param dataSource 数据源信息
//     * @return Connection
//     * @throws SQLException sql exception
//     */
//    public static Connection getConnection(DataSource dataSource) throws SQLException {
//        if (dataSource == null) {
//            return null;
//        }
//        return dataSource.getConnection();
//    }
//
//    /**
//     * 获取数据库连接
//     * 1.连接池方式
//     * 2.jdbc普通连接方式
//     *
//     * @param meta database meta
//     * @return Connection
//     */
//    public static Connection getConnection(AbstractDatabaseMeta meta, ConnectionTypeEnum type) throws SQLException, ClassNotFoundException {
//        switch (type) {
//            case POOLED:
//                log.info("使用连接池创建连接");
//                DataSource dataSource = getDataSource(meta);
//                return getConnection(dataSource);
//            case JDBC:
//                log.info("使用jdbc创建连接");
//                Class.forName(meta.getDriverClass());
//                Properties props = new Properties();
//                for (DataSourceMetaAttributeDTO attr : meta.getAttributes()) {
//                    props.put(attr.getAttrCode(), attr.getAttrValue());
//                }
//                props.put("user", meta.getDataSourceMeta().getUserName());
//                props.put("password", meta.getDataSourceMeta().getPassword());
//                return DriverManager.getConnection(meta.getUrl(), props);
//            default:
//                log.error("不支持的数据源类型");
//                return null;
//        }
//    }
//
//    /**
//     * 判断data source 是否已注册
//     *
//     * @param meta data base meta
//     * @return boolean
//     */
//    private static boolean isDataSourceRegistered(AbstractDatabaseMeta meta) {
//        String datasourceId = getDatasourceName(meta);
//        return DATA_SOURCES.containsKey(datasourceId);
//    }
//
//    /**
//     * 获取datasource的name
//     *
//     * @return datasource name md5 code
//     */
//    private static String getDatasourceName(AbstractDatabaseMeta meta) {
//        DataSourceMetaDTO ds = meta.getDataSourceMeta();
//        String type = ds.getDataSourceType().getCode();
//        String name = ds.getDataSourceName() != null ? ds.getDataSourceName() : "";
//        String hostName = ds.getHostName() != null ? ds.getHostName() : "";
//        String databaseName = ds.getDatabaseName() != null ? ds.getDatabaseName() : "";
//        String userName = ds.getUserName() != null ? ds.getUserName() : "";
//        String password = ds.getPassword() != null ? ds.getPassword() : "";
//        int port = ds.getPort() != null ? ds.getPort() : 0;
//        String decodeStr = type + name + hostName + databaseName + userName + password + port;
//        return DigestUtils.md5DigestAsHex(decodeStr.getBytes());
//    }
//
//    /**
//     * 获取datasource
//     *
//     * @param meta database meta
//     * @return datasource
//     */
//    public static DataSource getDataSource(AbstractDatabaseMeta meta) {
//        if (meta == null || meta.getDataSourceMeta() == null) {
//            return null;
//        } else if (isDataSourceRegistered(meta)) {
//            return DATA_SOURCES.get(getDatasourceName(meta));
//        } else {
//            DruidDataSource dataSource = new DruidDataSource();
//            dataSource.setName(meta.getDataSourceMeta().getDataSourceName());
//            dataSource.setUsername(meta.getDataSourceMeta().getUserName());
//            dataSource.setPassword(meta.getDataSourceMeta().getPassword());
//            dataSource.setDriverClassName(meta.getDriverClass());
//            dataSource.setUrl(meta.getUrl());
//            if (meta.getPooledAttributes().size() > 0) {
//                try {
//                    Properties props = new Properties();
//                    for (DataSourceMetaAttributeDTO attr : meta.getAttributes()) {
//                        props.put(attr.getAttrCode(), attr.getAttrValue());
//                    }
//                    DruidDataSourceFactory.config(dataSource, props);
//                } catch (SQLException e) {
//                    log.error(e.getMessage());
//                }
//            }
//            DATA_SOURCES.put(getDatasourceName(meta), dataSource);
//            return dataSource;
//        }
//    }
//
//    /**
//     * 关闭数据库连接
//     *
//     * @param conn Connection
//     */
//    public static void closeSilently(Connection conn) {
//        if (conn != null) {
//            try {
//                conn.close();
//            } catch (SQLException e) {
//                log.error("数据库连接关闭异常！");
//            }
//        }
//    }
//
//    /**
//     * 关闭Statement
//     *
//     * @param st Statement
//     */
//    public static void closeSilently(Statement st) {
//        if (st != null) {
//            try {
//                st.close();
//            } catch (SQLException e) {
//                log.error("Statement关闭异常！");
//            }
//        }
//    }
//
//    /**
//     * 关闭 PreparedStatement
//     *
//     * @param pst PreparedStatement
//     */
//    public static void closeSilently(PreparedStatement pst) {
//        if (pst != null) {
//            try {
//                pst.close();
//            } catch (SQLException e) {
//                log.error("PreparedStatement关闭异常！");
//            }
//        }
//    }
//
//    /**
//     * 关闭 result set
//     *
//     * @param rs ResultSet
//     */
//    public static void closeSilently(ResultSet rs) {
//        if (rs != null) {
//            try {
//                rs.close();
//            } catch (SQLException e) {
//                log.error("resultSet关闭异常！");
//            }
//        }
//    }
//
//    /**
//     * 关闭数据库连接
//     *
//     * @param conn Connection
//     * @param st   Statement
//     * @param rs   ResultSet
//     */
//    public static void closeSilently(Connection conn, Statement st, ResultSet rs) {
//        closeSilently(rs);
//        closeSilently(st);
//        closeSilently(conn);
//    }
//
//    /**
//     * 关闭数据库连接
//     *
//     * @param conn Connection
//     * @param pst  PreparedStatement
//     * @param rs   ResultSet
//     */
//    public static void closeSilently(Connection conn, PreparedStatement pst, ResultSet rs) {
//        closeSilently(rs);
//        closeSilently(pst);
//        closeSilently(conn);
//    }
//
//    /**
//     * 关闭数据库连接
//     *
//     * @param conn Connection
//     * @param pst  PreparedStatement
//     */
//    public static void close(Connection conn, PreparedStatement pst) {
//        closeSilently(pst);
//        closeSilently(conn);
//    }
//
//}
