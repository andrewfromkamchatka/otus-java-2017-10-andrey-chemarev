package ru.chemarev.andrey;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.List;

public class BillPool {

    public BillPool(Denomination denomination) {
        bills = new ArrayDeque<>();
        this.denomination = denomination;
    }

    public boolean addBill(Bill bill) {
        if ( bill.getDenomination() == denomination ) {
            bills.push(bill);
            return true;
        } else {
            return false;
        }
    }

    public int getPossibleCountForAmount(int amount) {
        int billCount = amount / denomination.getValue();
        return getCount() >= billCount ? billCount : getCount();
    }

    public int getCount() {
        return bills.size();
    }

    public List<Bill> getBills(int count) {
        List<Bill> result = new ArrayList<>();
        for (int i = 0; i < count; i++)
            result.add( bills.pop() );
        return result;
    }

    public long getBalance() {
        return denomination.getValue() * getCount();
    }

    public Denomination getDenomination() {
        return denomination;
    }

    private ArrayDeque<Bill> bills;
    private Denomination denomination;
}
