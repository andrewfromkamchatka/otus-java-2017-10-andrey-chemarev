package ru.chemarev.andrey.db;

import ru.chemarev.andrey.app.CacheStatistics;
import ru.chemarev.andrey.app.FrontendService;
import ru.chemarev.andrey.app.MsgToFrontend;
import ru.chemarev.andrey.front.StatisticsWebSocket;
import ru.chemarev.andrey.messageSystem.Address;

public class MsgGetUserCacheStatisticsAnswer extends MsgToFrontend {
    private final StatisticsWebSocket socket;
    private CacheStatistics statistics;

    public MsgGetUserCacheStatisticsAnswer(Address from, Address to, StatisticsWebSocket socket, CacheStatistics statistics) {
        super(from, to);
        this.socket = socket;
        this.statistics = statistics;
    }

    @Override
    public void exec(FrontendService frontendService) {
        frontendService.sendUserCacheStatistics(socket, statistics);
    }
}
