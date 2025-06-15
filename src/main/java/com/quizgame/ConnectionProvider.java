
package com.quizgame;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

import java.sql.Connection;
import java.sql.SQLException;

public class ConnectionProvider {
    private static final HikariDataSource dataSource;

    static {
        HikariConfig config = new HikariConfig();
        config.setJdbcUrl("jdbc:postgresql://localhost:5432/Quize_of_kings_game"); // آدرس دیتابیس‌ت
        config.setUsername("postgres"); // نام کاربری دیتابیس
        config.setPassword("1234"); // پسوردت
        config.setMaximumPoolSize(10); // حداکثر تعداد Connection همزمان
        config.setMinimumIdle(2);
        config.setIdleTimeout(30000); // 30 ثانیه
        config.setConnectionTimeout(30000); // timeout برای گرفتن connection
        config.setMaxLifetime(1800000); // 30 دقیقه

        dataSource = new HikariDataSource(config);
    }

    public static Connection getConnection() throws SQLException {
        return dataSource.getConnection();
    }
}