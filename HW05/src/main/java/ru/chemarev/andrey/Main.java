package ru.chemarev.andrey;

import ru.chemarev.andrey.test.TestClass2;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;

public class Main {

    public static void main(String[] args) {
        try {
            MyTestFramework.testClass(TestClass2.class.getName());
            MyTestFramework.testPackage("ru.chemarev.andrey.test");
        } catch (ClassNotFoundException | InvocationTargetException | InstantiationException | IllegalAccessException | IOException e) {
            e.printStackTrace();
        }
    }

}
