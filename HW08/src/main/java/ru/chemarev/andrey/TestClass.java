package ru.chemarev.andrey;

import java.util.*;

public class TestClass {

    private byte byteField = Byte.MAX_VALUE / 2;
    private boolean booleanField = false;
    private char charField = 'A';
    private short shortField = Short.MAX_VALUE / 2;
    private int intField = Integer.MAX_VALUE / 2;
    private long longField = Long.MAX_VALUE / 2;
    private double doubleField = Double.MAX_VALUE / 2;
    private float floatField = Float.MAX_VALUE / 2;

    private String stringField = "testString";
    private String[] arrayField = {"first", "second"};
    private TestClass2[] arrayField2 = {new TestClass2()};
    private List<TestClass2> listField = new ArrayList<>();
    private Map<Integer, TestClass2> mapField = new HashMap<>();

    public TestClass() {
        listField.add(new TestClass2());
        mapField.put(5, new TestClass2());
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        TestClass testClass = (TestClass) o;

        if (byteField != testClass.byteField) return false;
        if (booleanField != testClass.booleanField) return false;
        if (charField != testClass.charField) return false;
        if (shortField != testClass.shortField) return false;
        if (intField != testClass.intField) return false;
        if (longField != testClass.longField) return false;
        if (Double.compare(testClass.doubleField, doubleField) != 0) return false;
        if (Float.compare(testClass.floatField, floatField) != 0) return false;
        if (!stringField.equals(testClass.stringField)) return false;
        // Probably incorrect - comparing Object[] arrays with Arrays.equals
        if (!Arrays.equals(arrayField, testClass.arrayField)) return false;
        // Probably incorrect - comparing Object[] arrays with Arrays.equals
        if (!Arrays.equals(arrayField2, testClass.arrayField2)) return false;
        if (!listField.equals(testClass.listField)) return false;
        return mapField.equals(testClass.mapField);
    }

    @Override
    public int hashCode() {
        int result;
        long temp;
        result = (int) byteField;
        result = 31 * result + (booleanField ? 1 : 0);
        result = 31 * result + (int) charField;
        result = 31 * result + (int) shortField;
        result = 31 * result + intField;
        result = 31 * result + (int) (longField ^ (longField >>> 32));
        temp = Double.doubleToLongBits(doubleField);
        result = 31 * result + (int) (temp ^ (temp >>> 32));
        result = 31 * result + (floatField != +0.0f ? Float.floatToIntBits(floatField) : 0);
        result = 31 * result + stringField.hashCode();
        result = 31 * result + Arrays.hashCode(arrayField);
        result = 31 * result + Arrays.hashCode(arrayField2);
        result = 31 * result + listField.hashCode();
        result = 31 * result + mapField.hashCode();
        return result;
    }
}