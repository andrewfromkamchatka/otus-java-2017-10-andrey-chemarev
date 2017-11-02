package ru.chemarev.andrey;

public class TestClass {

    public TestClass(int number, int value) {
        this.number = number;
        this.value = value;
    }

    @Override
    public String toString() {
        return String.format("Element %d, value = %d", number, value);
    }

    public int getNumber() {
        return number;
    }

    public int getValue() {
        return value;
    }


    private int number;
    private int value;
}

