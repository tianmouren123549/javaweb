package com.gzu.JDBCbulk_operation;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Date;
import java.util.Calendar;
import java.util.Random;
import java.sql.Statement;
import java.sql.ResultSet;

public class JDBC_Test1 {
    public static void main(String[] args) {

        String url = "jdbc:mysql://127.0.0.1:3306/jdbc_test?serverTimezone=GMT&characterEncoding=UTF-8";
        String user = "root";
        String password = "trq123549";

        String sql = "INSERT INTO teacher(id, name, course, birthday) VALUES(?, ?, ?, ?)";
        Random random = new Random();
        String[] courses = {"Math", "Science", "English", "History", "Art"}; // 假设有这些课程

        try (Connection conn = DriverManager.getConnection(url, user, password)) {
            conn.setAutoCommit(false);

            int startingId;
            try (Statement stmt = conn.createStatement();
                 ResultSet rs = stmt.executeQuery("SELECT MAX(id) FROM teacher")) {

                if (rs.next()) {
                    int maxId = rs.getInt(1);
                    // 如果表中没有记录，设置起始 ID 为 1；否则设置为 maxId + 1
                    startingId = (maxId == 0) ? 1 : maxId + 1;
                } else {
                    startingId = 1; // 如果查询没有返回任何结果，默认从1开始
                }
            }

            try (PreparedStatement ps = conn.prepareStatement(sql)) {
                Calendar calendar = Calendar.getInstance();

                for (int i = 0; i < 100; i++) {
                    // 设置ID
                    int id = startingId + i;
                    ps.setInt(1, id);

                    // 设置姓名
                    ps.setString(2, "name" + i);

                    // 随机选择课程
                    String randomCourse = courses[random.nextInt(courses.length)];
                    ps.setString(3, randomCourse);

                    // 设置出生日期，每条记录增加1年
                    calendar.set(1990 + i, Calendar.JANUARY, 1); // 假设出生年份从1990年开始
                    ps.setDate(4, new Date(calendar.getTimeInMillis()));

                    ps.addBatch();

                    // 每5条记录执行一次批处理
                    if ((i + 1) % 5 == 0) { // 修正为(i + 1) % 5 == 0，以便确保在插入5条后执行
                        ps.executeBatch();
                        ps.clearBatch();
                    }
                }

                // 执行剩余的批处理
                ps.executeBatch();
                conn.commit();
                System.out.println("完成批量插入数据");
            } catch (SQLException e) {
                conn.rollback();
                e.printStackTrace();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
