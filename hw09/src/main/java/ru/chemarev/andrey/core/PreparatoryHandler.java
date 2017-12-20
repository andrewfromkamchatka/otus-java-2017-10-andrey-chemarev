package ru.chemarev.andrey.core;

import java.sql.PreparedStatement;
import java.sql.SQLException;

@FunctionalInterface
public interface PreparatoryHandler {
    void handle(PreparedStatement statement) throws SQLException;
}
