package org.example.examen_final_prog2.Repository;

import org.springframework.stereotype.Component;

import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;

@Component
public class DataBaseConnection {
    public ResultSet connectionDB(String sql, Object... params) {
        ResultSet resultSet = null;
        try {
            var connection = DriverManager.getConnection(
                    "jdbc:postgresql://localhost:5432/exam",
                    System.getenv("USERNAME"),
                    System.getenv("DB_PASSWORD")
            );
            var statement = connection.prepareStatement(sql);
            for (int i = 0; i < params.length; i++) {
                statement.setObject(i + 1, params[i]);
            }
            resultSet = statement.executeQuery();
        } catch (SQLException e) {
            System.out.println("Database error : " + e.getMessage());
        }
        return resultSet;
    }
}
