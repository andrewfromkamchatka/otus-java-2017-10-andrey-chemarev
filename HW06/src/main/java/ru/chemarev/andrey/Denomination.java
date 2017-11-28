package ru.chemarev.andrey;

public enum Denomination {
    FIFTEEN(50), ONE_HUNDRED(100), FIVE_HUNDRED(500), ONE_THOUSAND(1000), FIVE_THOUSAND(5000);

    Denomination(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }

    private int value;
}
