package com.gzu.JDBChomework;

import java.sql.*;
public class JDBC_RETRIEVE {
    public static void main(String[] args) {

        String url = "jdbc:mysql://127.0.0.1:3306/jdbc_test?serverTimezone=GMT&characterEncoding=UTF-8";
        String user = "root";
        String password = "trq123549";
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            // 加载mysql驱动
            conn = DriverManager.getConnection(url, user, password);
            // 创建Statement对象
            ps = conn.prepareStatement("SELECT * FROM teacher");
            // 执行查询
            rs = ps.executeQuery();
            // 输出查询结果
            int columnCount = rs.getMetaData().getColumnCount();
            // 输出查询结果
            while (rs.next()) {
                for (int i = 1; i <= columnCount; i++) {
                    System.out.print(rs.getObject(i) + " ");
                }
                System.out.println(); // 换行
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            // 释放资源
            if (rs != null) {
                try {
                    rs.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
            if (ps != null) {
                try {
                    ps.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
            if (conn != null) {
                try {
                    conn.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
