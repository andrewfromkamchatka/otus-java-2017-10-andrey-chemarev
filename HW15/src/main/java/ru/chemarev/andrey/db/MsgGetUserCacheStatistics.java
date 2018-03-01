package ru.chemarev.andrey.db;

import ru.chemarev.andrey.app.DBService;
import ru.chemarev.andrey.app.MsgToDB;
import ru.chemarev.andrey.front.StatisticsWebSocket;
import ru.chemarev.andrey.messageSystem.Address;

public class MsgGetUserCacheStatistics extends MsgToDB {
    private final StatisticsWebSocket socket;

    public MsgGetUserCacheStatistics(Address from, Address to, StatisticsWebSocket socket) {
        super(from, to);
        this.socket = socket;
    }

    @Override
    public void exec(DBService dbService) {
        dbService.getMS().sendMessage(new MsgGetUserCacheStatisticsAnswer(
                getTo(), getFrom(), socket, dbService.getUserCache().getCacheStatistics()
        ));
    }
}
