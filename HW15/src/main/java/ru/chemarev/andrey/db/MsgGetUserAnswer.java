package ru.chemarev.andrey.db;

import ru.chemarev.andrey.app.FrontendService;
import ru.chemarev.andrey.app.MsgToFrontend;
import ru.chemarev.andrey.db.dataset.UserDataSet;
import ru.chemarev.andrey.messageSystem.Address;

/**
 * Created by tully.
 */
public class MsgGetUserAnswer extends MsgToFrontend {
    private final UserDataSet user;

    public MsgGetUserAnswer(Address from, Address to, UserDataSet user) {
        super(from, to);
        this.user = user;
    }

    @Override
    public void exec(FrontendService frontendService) {
        frontendService.addUser(user);
    }
}
