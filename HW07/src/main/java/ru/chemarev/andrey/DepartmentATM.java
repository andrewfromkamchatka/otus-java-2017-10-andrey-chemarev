package ru.chemarev.andrey;

import ru.chemarev.andrey.atm.ATM;
import ru.chemarev.andrey.atm.ATMMemento;

public class DepartmentATM {

    public DepartmentATM(ATM atm) {
        this.atm = atm;
        saveState();
    }

    public long getBalance() {
        return atm.getBalance();
    }

    public void restoreState() {
        if ( memento != null )
            atm.loadFromMemento(memento);
    }

    public void saveState() {
        memento = atm.saveToMemento();
    }

    private ATM atm;
    private ATMMemento memento;
}
