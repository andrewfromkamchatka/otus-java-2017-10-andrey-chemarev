package ru.chemarev.andrey;

public class TestClass {

    public TestClass(long number, long value) {
        this.number = number;
        this.value = value;
    }

    @Override
    public String toString() {
        return String.format("Element %d, value = %d", number, value);
    }

    public long getNumber() {
        return number;
    }

    public long getValue() {
        return value;
    }


    private long number;
    private long value;
}

