package ru.chemarev.andrey;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class ATMTest {

    private ATM atm;

    @Before
    public void initialize() {
        atm = new ATM(Denomination.FIVE_THOUSAND, Denomination.ONE_THOUSAND,
                Denomination.FIVE_HUNDRED, Denomination.ONE_HUNDRED);

        atm.putMoney(Bill.oneThousand());
        atm.putMoney(Bill.fiveHundred());
        atm.putMoney(Bill.getBills(Denomination.ONE_HUNDRED, 8));
    }

    @Test
    public void getBalance() throws Exception {
        assertEquals(2300, atm.getBalance());
    }

    @Test
    public void getMoney() throws Exception {
        assertEquals(3, atm.getMoney(1600).size());
    }

    @Test(expected = ATMException.class)
    public void getMoney_NotEnough() throws Exception {
        atm.getMoney(5000);
    }

    @Test(expected = ATMException.class)
    public void getMoney_NotEnoughBills() throws Exception {
        atm.getMoney(2400);
    }

    @Test(expected = ATMException.class)
    public void getMoney_NotCorrectedValue() throws Exception {
        atm.getMoney(1234);
    }

}