package ru.chemarev.andrey;

public class TestClass2 {
    private String stringField = "Hello World";

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        TestClass2 that = (TestClass2) o;

        return stringField != null ? stringField.equals(that.stringField) : that.stringField == null;
    }

    @Override
    public int hashCode() {
        return stringField != null ? stringField.hashCode() : 0;
    }
}
