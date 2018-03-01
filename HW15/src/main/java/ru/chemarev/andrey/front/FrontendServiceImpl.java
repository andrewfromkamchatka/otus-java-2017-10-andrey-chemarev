package ru.chemarev.andrey.front;

import ru.chemarev.andrey.app.CacheStatistics;
import ru.chemarev.andrey.app.FrontendService;
import ru.chemarev.andrey.app.MessageSystemContext;
import ru.chemarev.andrey.db.MsgGetUser;
import ru.chemarev.andrey.db.MsgGetUserCacheStatistics;
import ru.chemarev.andrey.db.dataset.UserDataSet;
import ru.chemarev.andrey.messageSystem.Address;
import ru.chemarev.andrey.messageSystem.Message;
import ru.chemarev.andrey.messageSystem.MessageSystem;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by tully.
 */
public class FrontendServiceImpl implements FrontendService {
    private final Address address;
    private final MessageSystemContext context;

    private final Map<Long, UserDataSet> users = new HashMap<>();

    public FrontendServiceImpl(MessageSystemContext context, Address address) {
        this.context = context;
        this.address = address;
    }

    public void init() {
        context.getMessageSystem().addAddressee(this);
    }

    @Override
    public Address getAddress() {
        return address;
    }

    public void handleRequest(int id) {
        Message message = new MsgGetUser(getAddress(), context.getDbAddress(), id);
        context.getMessageSystem().sendMessage(message);
    }

    @Override
    public void handleStatisticsRequest(StatisticsWebSocket socket) {
        Message message = new MsgGetUserCacheStatistics(getAddress(), context.getDbAddress(), socket);
        context.getMessageSystem().sendMessage(message);
    }

    public void addUser(UserDataSet user) {
        users.put(user.getId(), user);
        System.out.println("User: " + user.getName() + " has id: " + user.getId());
    }

    public void sendUserCacheStatistics(StatisticsWebSocket socket, CacheStatistics statistics) {
        socket.sendStatisticsAnswer(statistics);
    }

    @Override
    public MessageSystem getMS() {
        return context.getMessageSystem();
    }
}
