package ru.chemarev.andrey.core;

public interface DBService extends AutoCloseable {
    UserDataSet read(long id);
    void save(UserDataSet dataSet);
    void shutdown();
}
