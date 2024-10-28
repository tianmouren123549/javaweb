package com.gzu.JDBChomework;

import java.sql.*;
import java.util.Scanner;

public class JDBC_CREATE {
    public static void main(String[] args) {

        String url = "jdbc:mysql://127.0.0.1:3306/jdbc_test?serverTimezone=GMT&characterEncoding=UTF-8";
        String user = "root";
        String password = "trq123549";

        String sql = "INSERT INTO teacher (id,name,course,birthday) VALUES (?,?,?,?)";

        Scanner scanner = new Scanner(System.in);

        try (Connection conn = DriverManager.getConnection(url, user, password);) {
            conn.setAutoCommit(false);
            try (PreparedStatement ps = conn.prepareStatement(sql);) {

    System.out.print("请输入教师ID:");
    int id = scanner.nextInt();
    scanner.nextLine();

    System.out.print("请输入教师姓名:");
    String name = scanner.nextLine();

    System.out.print("请输入课程名称:");
    String course = scanner.nextLine();

    System.out.print("请输入教师生日:");
    String birthdayStr = scanner.nextLine();
    Date birthday = Date.valueOf(birthdayStr);

    ps.setInt(1,id);
    ps.setString(2,name);
    ps.setString(3,course);
    ps.setDate(4,birthday);
                ps.executeUpdate();
                conn.commit();
                System.out.print("输出插入成功");
            } catch (SQLException e) {
                conn.rollback();
                e.printStackTrace();
            } finally {
                conn.setAutoCommit(true);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
