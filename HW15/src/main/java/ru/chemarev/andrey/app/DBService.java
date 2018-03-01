package ru.chemarev.andrey.app;

import ru.chemarev.andrey.db.dataset.UserDataSet;
import ru.chemarev.andrey.messageSystem.Addressee;

public interface DBService extends Addressee, AutoCloseable {
    void init();
    UserDataSet getUser(long id);
    CacheEngine<Long, UserDataSet> getUserCache();
    void saveUser(UserDataSet dataSet);
    void shutdown();
}
