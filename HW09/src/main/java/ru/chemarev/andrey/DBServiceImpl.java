package ru.chemarev.andrey;

import ru.chemarev.andrey.core.ConnectionHelper;
import ru.chemarev.andrey.core.DBService;

import java.sql.SQLException;

public class DBServiceImpl implements DBService {

    private Executor executor;

    public DBServiceImpl() throws SQLException {
        executor = new Executor(ConnectionHelper.getConnection());
    }

    @Override
    public UserDataSet read(long id) {
        UserDAO dao = new UserDAO(executor);
        return dao.load(id);
    }

    @Override
    public void save(UserDataSet dataSet) {
        UserDAO dao = new UserDAO(executor);
        dao.save(dataSet);
    }

    @Override
    public void shutdown() {
        try {
            executor.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
