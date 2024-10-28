package com.gzu.JDBCbulk_operation;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Scanner;

public class JDBC_Test2 {
    public static void main(String[] args) {
        String url = "jdbc:mysql://127.0.0.1:3306/jdbc_test?serverTimezone=GMT&characterEncoding=UTF-8";
        String user = "root";
        String password = "trq123549";

        String sql = "DELETE FROM teacher WHERE name = ?";
        Scanner scanner = new Scanner(System.in);

        try (Connection conn = DriverManager.getConnection(url, user, password)) {
            conn.setAutoCommit(false);
            try (PreparedStatement ps = conn.prepareStatement(sql)) {
                System.out.print("请输入要删除的教师姓名（以空格分隔）: ");
                String input = scanner.nextLine();


                // 处理输入的教师姓名
                String[] names = input.split(" ");
                for (String name : names) {
                    ps.setString(1, name.trim()); // 去除多余空格
                    ps.addBatch(); // 添加到批处理中
                }
                // 执行批处理
                int[] results = ps.executeBatch();
                conn.commit(); // 提交事务

                // 输出删除结果
                int totalDeleted = 0;
                for (int result : results) {
                    if (result > 0) {
                        totalDeleted++;
                    }
                }
                System.out.println("成功删除 " + totalDeleted + " 条记录。");
            } catch (SQLException e) {
                conn.rollback(); // 出现异常时回滚
                e.printStackTrace();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            scanner.close(); // 关闭扫描器
        }
    }
}
