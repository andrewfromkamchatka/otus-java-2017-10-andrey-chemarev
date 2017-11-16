package ru.chemarev.andrey;

public class MyAsserts {

    public static void assertTrue(boolean expression) {
        if ( !expression )
            throw new AssertionError();
    }

    public static void assertFalse(boolean expression) {
        if ( expression )
            throw new AssertionError();
    }

    public static void assertEquals(Object obj1, Object obj2) {
        if ( ! obj1.equals(obj2) )
            throw new AssertionError();
    }
}
