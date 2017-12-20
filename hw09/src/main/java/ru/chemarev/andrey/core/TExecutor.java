package ru.chemarev.andrey.core;

import java.sql.*;

public class TExecutor {
    private Connection connection;

    public TExecutor(Connection connection) {
        this.connection = connection;
    }

    public <T> T execQuery(String query, ResultHandler<T> handler) throws SQLException {
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            ResultSet resultSet = stmt.executeQuery();
            return handler.handle(resultSet);
        }
    }

    public <T> T execQuery(String query, PreparatoryHandler preparatoryHandler, ResultHandler<T> resultHandler) throws SQLException {
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            preparatoryHandler.handle(stmt);
            return resultHandler.handle( stmt.executeQuery() );
        }
    }

    public int execUpdate(String update, PreparatoryHandler handler) throws SQLException {
        try (PreparedStatement stmt = connection.prepareStatement(update)) {
            handler.handle(stmt);
            return stmt.executeUpdate();
        }
    }
}
