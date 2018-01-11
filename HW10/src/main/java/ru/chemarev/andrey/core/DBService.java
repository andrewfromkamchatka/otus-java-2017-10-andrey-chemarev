package ru.chemarev.andrey.core;

public interface DBService {
    UserDataSet read(long id);
    void save(UserDataSet dataSet);
    void shutdown();
}
