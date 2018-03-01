package ru.chemarev.andrey.db.dao;

import org.hibernate.Session;
import ru.chemarev.andrey.db.dataset.UserDataSet;

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
