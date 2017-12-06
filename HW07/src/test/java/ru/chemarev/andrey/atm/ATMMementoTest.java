package ru.chemarev.andrey.atm;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class ATMMementoTest {

    private ATMMemento memento;

    @Before
    public void initialize() {
        ATM atm = ATM.getDefaultATM();
        atm.putMoney(Bill.getBills(Denomination.ONE_HUNDRED, 3));
        atm.putMoney(Bill.getBills(Denomination.FIVE_HUNDRED, 4));
        atm.putMoney(Bill.getBills(Denomination.ONE_THOUSAND, 5));
        atm.putMoney(Bill.getBills(Denomination.FIVE_THOUSAND, 6));

        memento = atm.saveToMemento();
    }

    @Test
    public void getState() throws Exception {
        assertEquals(3, memento.getState().get(Denomination.ONE_HUNDRED).intValue());
        assertEquals(4, memento.getState().get(Denomination.FIVE_HUNDRED).intValue());
        assertEquals(5, memento.getState().get(Denomination.ONE_THOUSAND).intValue());
        assertEquals(6, memento.getState().get(Denomination.FIVE_THOUSAND).intValue());
    }

}