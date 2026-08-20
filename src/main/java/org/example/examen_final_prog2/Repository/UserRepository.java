package main.java.org.example.examen_final_prog2.Repository;


import main.java.org.example.examen_final_prog2.modul.User;
import org.springframework.stereotype.Repository;

import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;

@Repository
public class UserRepository {
      private DataBaseConnection dataBaseConnection
    private User mapRow(ResultSet resultSet) throws SQLException{
        return new User(
                resultSet.getString("id"),
                resultSet.getString("ref"),
                resultSet.getString("firstName"),
                resultSet.getString("lastName"),
                resultSet.getString("email"),
                resultSet.getString("phone")
        );
    }

}
