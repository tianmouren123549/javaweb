package com.gzu.JDBChomework;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Scanner;

public class JDBC_DELETE {
    public static void main(String[] args) {

        String url = "jdbc:mysql://127.0.0.1:3306/jdbc_test?serverTimezone=GMT&characterEncoding=UTF-8";
        String user = "root";
        String password = "trq123549";

        String sql = "DELETE FROM  teacher WHERE name = ?";
        Scanner scanner = new Scanner(System.in);
        try (Connection conn = DriverManager.getConnection(url, user, password);) {
            conn.setAutoCommit(false);
            try (PreparedStatement ps = conn.prepareStatement(sql);) {

                System.out.print("请要删除的教师姓名:");
                String name = scanner.nextLine();

                ps.setString(1, name);

                ps.executeUpdate();
                conn.commit();
                System.out.print("删除成功!");
            } catch (SQLException e) {
                conn.rollback();
                e.printStackTrace();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
