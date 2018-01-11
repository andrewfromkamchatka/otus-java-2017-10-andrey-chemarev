package ru.chemarev.andrey.core;

import ru.chemarev.andrey.UserDataSet;

public interface DBService {
    UserDataSet read(long id);
    void save(UserDataSet dataSet);
    void shutdown();
}
