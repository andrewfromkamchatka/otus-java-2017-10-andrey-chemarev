package ru.chemarev.andrey.app;

import ru.chemarev.andrey.db.dataset.UserDataSet;
import ru.chemarev.andrey.front.StatisticsWebSocket;
import ru.chemarev.andrey.messageSystem.Addressee;

public interface FrontendService extends Addressee {
    void init();

    void handleRequest(int id);
    void handleStatisticsRequest(StatisticsWebSocket socket);

    void addUser(UserDataSet user);
    void sendUserCacheStatistics(StatisticsWebSocket socket, CacheStatistics statistics);
}

