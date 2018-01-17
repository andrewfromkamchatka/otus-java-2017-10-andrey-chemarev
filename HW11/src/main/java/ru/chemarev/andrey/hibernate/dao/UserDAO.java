package ru.chemarev.andrey.hibernate.dao;

import org.hibernate.Session;
import ru.chemarev.andrey.core.UserDataSet;

public class UserDAO {
    private Session session;

    public UserDAO(Session session) {
        this.session = session;
    }

    public void save(UserDataSet dataSet) {
        session.save(dataSet);
    }

    public UserDataSet read(long id) {
        return session.load(UserDataSet.class, id);
    }
}
