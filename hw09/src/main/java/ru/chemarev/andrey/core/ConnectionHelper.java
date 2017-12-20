package ru.chemarev.andrey.core;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnectionHelper {

    public static Connection getConnection() throws SQLException {
        DriverManager.registerDriver(new org.postgresql.Driver());

        String url = "jdbc:postgresql://localhost:5432/db_example?user=otus&password=otus";

        return DriverManager.getConnection(url);
    }

}
