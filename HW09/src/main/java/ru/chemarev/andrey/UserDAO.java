package ru.chemarev.andrey;

import java.sql.SQLException;

public class UserDAO {

    private Executor executor;

    public UserDAO(Executor executor) {
        this.executor = executor;
    }

    public UserDataSet load(long id) {
        try {
            return executor.load(id, UserDataSet.class);
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    public void save(UserDataSet dataSet) {
        try {
            executor.save(dataSet);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
