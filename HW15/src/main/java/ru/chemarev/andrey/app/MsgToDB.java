package ru.chemarev.andrey.app;

import ru.chemarev.andrey.messageSystem.Address;
import ru.chemarev.andrey.messageSystem.Addressee;
import ru.chemarev.andrey.messageSystem.Message;

/**
 * Created by tully.
 */
public abstract class MsgToDB extends Message {
    public MsgToDB(Address from, Address to) {
        super(from, to);
    }

    @Override
    public void exec(Addressee addressee) {
        if (addressee instanceof DBService) {
            exec((DBService) addressee);
        }
    }

    public abstract void exec(DBService dbService);
}
