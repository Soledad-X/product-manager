package com.utils;

import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;
import java.sql.SQLException;
import java.util.List;

public class DbUtils {
    public static boolean executeUpdate(String sql, Object[] params) {
        int count = 0;
        try {
            count = new QueryRunner(DBCPUtils.getDataSource()).update(sql,params);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return count > 0;
    }
    // 查询一行Bean
    public static<T> Object executeQueryResult(Class<T> cls, String sql, Object[] params){
        try {
            return  new QueryRunner(DBCPUtils.getDataSource()).query(sql, new BeanHandler<>(cls), params);
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }
    // 查询多行Bean
    public static<T> List<T> executeQueryResults(Class<T> cls, String sql, Object[] params){
        try {
            return new QueryRunner(DBCPUtils.getDataSource()).query(sql, new BeanListHandler<>(cls),params);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
