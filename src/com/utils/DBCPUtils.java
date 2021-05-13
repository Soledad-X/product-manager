package com.utils;

import org.apache.commons.dbcp2.BasicDataSourceFactory;

import javax.sql.DataSource;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Properties;

public class DBCPUtils {
    private static DataSource dataSource;

    //加载配置文件，创建数据库连接池
    static {
        try {
            InputStream is = DBCPUtils.class.getResourceAsStream("dbcpconfig.properties");
            Properties p = new Properties();
            p.load(is);
            dataSource = BasicDataSourceFactory.createDataSource(p);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //返回连接对象
    public static Connection getConnection() {
        try {
            return dataSource.getConnection();
        } catch (SQLException e) {
            throw new RuntimeException("数据连接获取失败！");
        }
    }

    //返回连接池对象
    public static DataSource getDataSource() {
        return dataSource;
    }

    // 释放连接，被连接池收回（Soledad:好像不需要运行释放连接）
    public static void releaseConnection(Connection conn, PreparedStatement pstmt, ResultSet rs) {
        try {
            if (rs != null) {
                rs.close();
            }
            if (pstmt != null) {
                pstmt.close();
            }
            if (conn != null) {
                conn.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // 利用main函数测一下是否连接成功
    public static void main(String[] args) {
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        try {
            conn = DBCPUtils.getConnection();
            pstmt = conn.prepareStatement("select * from user");
            rs = pstmt.executeQuery();
            while (rs.next()) {
                System.out.println(rs.getString(1) + " " + rs.getString(2) + " " + rs.getString(3) + " " + rs.getString(4));
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            DBCPUtils.releaseConnection(conn, pstmt, rs);
        }
    }
}
