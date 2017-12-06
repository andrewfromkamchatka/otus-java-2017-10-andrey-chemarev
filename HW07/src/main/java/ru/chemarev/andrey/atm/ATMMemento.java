package ru.chemarev.andrey.atm;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

public class ATMMemento {

    public ATMMemento(Collection<BillPool> billPools) {
        state = new HashMap<>();
        for (BillPool pool : billPools)
            state.put(pool.getDenomination(), pool.getCount());
    }

    public Map<Denomination, Integer> getState() {
        return state;
    }

    private Map<Denomination, Integer> state;
}
