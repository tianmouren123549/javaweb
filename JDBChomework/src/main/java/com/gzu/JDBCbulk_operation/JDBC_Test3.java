package com.gzu.JDBCbulk_operation;

import java.sql.*;

public class JDBC_Test3 {
    public static void main(String[] args) {

        String url = "jdbc:mysql://127.0.0.1:3306/jdbc_test?serverTimezone=GMT&characterEncoding=UTF-8";
        String user = "root";
        String password = "trq123549";
        String sql = "SELECT * FROM teacher WHERE id > ?";

        try (Connection conn = DriverManager.getConnection(url, user, password);
             PreparedStatement ps = conn.prepareStatement(sql, ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
        ) {
            // 设置参数
            ps.setInt(1, 20);
            // 执行查询
            try (ResultSet rs = ps.executeQuery()) {
                // 检查结果集是否为空
                if (!rs.last()) {
                    System.out.println("没有查询到数据。");
                    return;
                }

                // 移动到倒数第二行
                rs.absolute(-2);
                System.out.println("倒数第二条记录：");
                System.out.println("ID: " + rs.getInt("id") + ", Name: " + rs.getString("name"));

            } catch (SQLException e) {
                e.printStackTrace();
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
