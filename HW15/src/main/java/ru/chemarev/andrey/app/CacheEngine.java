package ru.chemarev.andrey.app;

import ru.chemarev.andrey.db.cache.MyElement;

public interface CacheEngine<K, V> {

    void put(MyElement<K, V> element);

    MyElement<K, V> get(K key);

    int getHitCount();

    int getMissCount();

    CacheStatistics getCacheStatistics();

    void dispose();
}
