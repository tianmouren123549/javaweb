package com.gzu.JDBChomework;

import java.sql.*;
import java.util.Scanner;

public class JDBC_UPDATE {
    public static void main(String[] args) {

        String url = "jdbc:mysql://127.0.0.1:3306/jdbc_test?serverTimezone=GMT&characterEncoding=UTF-8";
        String user = "root";
        String password = "trq123549";

        String sql = "UPDATE teacher SET name = ? , course = ? , birthday = ? WHERE id = ?";

        Scanner scanner = new Scanner(System.in);
        try (Connection conn = DriverManager.getConnection(url, user, password);) {
            conn.setAutoCommit(false);
            try (PreparedStatement ps = conn.prepareStatement(sql);) {

                System.out.print("请输新的教师姓名:");
                String name = scanner.nextLine();

                System.out.print("输入新的课程名:");
                String course = scanner.nextLine();

                System.out.print("请输入新的教师生日:");
                String birthdayStr = scanner.nextLine();
                Date birthday = Date.valueOf(birthdayStr);


                System.out.print("请输入被更新的教师的id:");
                int id = scanner.nextInt();
                scanner.nextLine();

                ps.setString(1 ,name);
                ps.setString(2 ,course);
                ps.setDate(3, birthday);
                ps.setInt(4, id);


                ps.executeUpdate();
                conn.commit();
                System.out.print("输出更新成功");
            } catch (SQLException e) {
                conn.rollback();
                e.printStackTrace();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
