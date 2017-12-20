package ru.chemarev.andrey;

import ru.chemarev.andrey.core.DataSet;
import ru.chemarev.andrey.core.TExecutor;
import ru.chemarev.andrey.core.Table;

import java.lang.reflect.Field;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.StringJoiner;

public class Executor extends TExecutor {
    private static final String INSERT_DATA_SET = "insert into %s(%s) values(%s)";
    private static final String SELECT_DATA_SET_BY_ID = "select * from %s where id = %d";

    public Executor(Connection connection) throws SQLException {
        super(connection);
    }

    <T extends DataSet> void save(T dateSet) throws SQLException {
        Class clazz = dateSet.getClass();

        Table table = (Table) clazz.getAnnotation(Table.class);
        if ( table == null ) {
            throw new IllegalArgumentException();
        }

        StringJoiner columnJoiner = new StringJoiner(",");
        StringJoiner valuesJoiner = new StringJoiner(",");
        ArrayList<Object> values = new ArrayList<>();

        for (Field field : clazz.getDeclaredFields()) {
            columnJoiner.add(field.getName());
            valuesJoiner.add("?");

            field.setAccessible(true);
            try {
                values.add(field.get(dateSet));
            } catch (IllegalAccessException e) {
                throw new RuntimeException(String.format("Cannot save field %s of %s class", field.getName(), clazz.getName()));
            }
            field.setAccessible(false);
        }

        String query = String.format(INSERT_DATA_SET, table.name(), columnJoiner.toString(), valuesJoiner.toString());

        execUpdate(query, statement -> {
            int i = 1;
            for (Object value : values ) {
                statement.setObject(i, value);
                i++;
            }
        });
    }

    <T extends DataSet> T load(long id, Class<T> clazz) throws SQLException {
        Table table = (Table) clazz.getAnnotation(Table.class);
        if ( table == null ) {
            throw new IllegalArgumentException();
        }

        String query = String.format(SELECT_DATA_SET_BY_ID, table.name(), id);

        return execQuery(query, resultSet -> {
            try {
                if (!resultSet.next()) {
                    return null;
                }

                T result = clazz.newInstance();
                result.setId(id);
                for (Field field : result.getClass().getDeclaredFields()) {
                    field.setAccessible(true);
                    field.set(result, resultSet.getObject( field.getName() ));
                    field.setAccessible(false);
                }

                return result;
            } catch (InstantiationException | IllegalAccessException e) {
                throw new RuntimeException(String.format("Cannot create object of %s class", clazz.getName()), e);
            }
        });

    }
}
