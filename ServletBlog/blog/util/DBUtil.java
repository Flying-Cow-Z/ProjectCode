package com.zhangbiao.blog.util;

import com.mysql.jdbc.jdbc2.optional.MysqlDataSource;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;

public class DBUtil {
    private static final DataSource dataSource;

    static {
        MysqlDataSource mysqlDataSource = new MysqlDataSource();
        mysqlDataSource.setUrl("jdbc:mysql://127.0.0.1:3306/personalblog?characterEncoding=utf8&useSSL=false&serverTimezone=Asia/Shanghai");
        mysqlDataSource.setUser("root");
        mysqlDataSource.setPassword("xatu6117.");
        dataSource = mysqlDataSource;
    }

    public static Connection connection() throws SQLException {
        return dataSource.getConnection();
    }
}