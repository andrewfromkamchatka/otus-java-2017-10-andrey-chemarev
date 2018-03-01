package ru.chemarev.andrey.db;

import ru.chemarev.andrey.app.DBService;
import ru.chemarev.andrey.app.MsgToDB;
import ru.chemarev.andrey.db.dataset.UserDataSet;
import ru.chemarev.andrey.messageSystem.Address;

/**
 * Created by tully.
 */
public class MsgGetUser extends MsgToDB {
    private final long id;

    public MsgGetUser(Address from, Address to, long id) {
        super(from, to);
        this.id = id;
    }

    @Override
    public void exec(DBService dbService) {
        UserDataSet user = dbService.getUser(id);
        dbService.getMS().sendMessage(new MsgGetUserAnswer(getTo(), getFrom(), user));
    }
}
