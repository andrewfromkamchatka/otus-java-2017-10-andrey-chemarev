package ru.chemarev.andrey;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class BillPoolTest {

    private BillPool pool;

    @Before
    public void initialize() {
        pool = new BillPool(Denomination.ONE_HUNDRED);
        pool.addBill(Bill.oneHundred());
        pool.addBill(Bill.oneHundred());
        pool.addBill(Bill.oneHundred());
    }

    @Test
    public void getBalance() throws Exception {
        assertEquals(300, pool.getBalance());
    }

    @Test
    public void addBill() throws Exception {
        assertTrue(pool.addBill( Bill.oneHundred() ));
    }

    @Test
    public void addBill_NotSuitable() throws Exception {
        assertFalse(pool.addBill( Bill.fiveThousand() ));
    }

    @Test
    public void getPossibleCountForAmount() throws Exception {
        int amount = 400;
        assertEquals(3, pool.getPossibleCountForAmount(amount));

        amount = 200;
        assertEquals(2, pool.getPossibleCountForAmount(amount));
    }

}