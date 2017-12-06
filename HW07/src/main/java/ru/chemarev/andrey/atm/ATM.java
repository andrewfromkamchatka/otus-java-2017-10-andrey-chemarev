package ru.chemarev.andrey.atm;

import java.util.*;

public class ATM {

    public ATM(Denomination... denominations) {
        poolMap = new TreeMap<>(Collections.reverseOrder());

        for (Denomination denomination : denominations)
            poolMap.put(denomination, new BillPool(denomination));
    }

    public static ATM getDefaultATM() {
        return new ATM(Denomination.FIVE_THOUSAND,
                Denomination.ONE_THOUSAND,
                Denomination.FIVE_HUNDRED,
                Denomination.ONE_HUNDRED
        );
    }

    public long getBalance() {
        return poolMap.entrySet().stream()
                .mapToLong( e -> e.getValue().getBalance() )
                .sum();
    }

    public List<Bill> getMoney(int amount) throws ATMException {
        if ( amount > getBalance() )
            throw new ATMException("Not enough money");

        if ( ! isEnoughBills(amount) )
            throw new ATMException("Can't give you the specified amount");

        return getBills(amount);
    }

    private boolean isEnoughBills(int amount) {
        int remainder = amount;

        for ( BillPool pool : poolMap.values() ) {
            int portion = pool.getPossibleCountForAmount(remainder) * pool.getDenomination().getValue();
            remainder -= portion;
        }

        return remainder == 0;
    }

    private List<Bill> getBills(int amount) {
        List<Bill> result = new ArrayList<>();

        int remainder = amount;
        for ( BillPool pool : poolMap.values() ) {
            int count = pool.getPossibleCountForAmount(remainder);
            if (count != 0) {
                result.addAll( pool.getBills(count) );
                remainder -= count * pool.getDenomination().getValue();
            }
        }

        return result;
    }

    public boolean putMoney(Bill bill) {
        BillPool pool = poolMap.get( bill.getDenomination() );

        return pool != null && pool.addBill(bill);
    }

    public List<Bill> putMoney(List<Bill> bills) {
        List<Bill> unsuitable = new ArrayList<>();
        for (Bill bill : bills) {
            if ( ! putMoney(bill) )
                unsuitable.add(bill);
        }
        return unsuitable;
    }

    public void loadFromMemento(ATMMemento memento) {
        poolMap.clear();
        for (Map.Entry<Denomination, Integer> entry : memento.getState().entrySet()) {
            BillPool pool = new BillPool(entry.getKey());
            pool.addBills( Bill.getBills(entry.getKey(), entry.getValue()) );
            poolMap.put(entry.getKey(), pool);
        }

    }

    public ATMMemento saveToMemento() {
        return new ATMMemento(poolMap.values());
    }

    private Map<Denomination, BillPool> poolMap;
}