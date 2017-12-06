package ru.chemarev.andrey.atm;

import java.util.ArrayList;
import java.util.List;

public class Bill {

    public static List<Bill> getBills(Denomination denomination, int count) {
        List<Bill> result = new ArrayList<>(count);
        for (int i = 0; i < count; i++)
            result.add( new Bill(denomination) );
        return result;
    }

    public static Bill oneHundred() {
        return new Bill(Denomination.ONE_HUNDRED);
    }

    public static Bill fiveHundred() {
        return new Bill(Denomination.FIVE_HUNDRED);
    }

    public static Bill oneThousand() {
        return new Bill(Denomination.ONE_THOUSAND);
    }

    public static Bill fiveThousand() {
        return new Bill(Denomination.FIVE_THOUSAND);
    }

    public static Bill fifteen() {
        return new Bill(Denomination.FIFTEEN);
    }

    private Bill(Denomination denomination) {
        this.denomination = denomination;
    }

    public Denomination getDenomination() {
        return denomination;
    }

    private Denomination denomination;
}
