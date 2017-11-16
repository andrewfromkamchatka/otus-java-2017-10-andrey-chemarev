package ru.chemarev.andrey;

import ru.chemarev.andrey.test.TestClass1;
import ru.chemarev.andrey.test.WrongTestClass;

import java.io.IOException;

public class Main {

    public static void main(String[] args) {
        try {
//            MyTestFramework.testClass(WrongTestClass.class.getName());
//            MyTestFramework.testClass(TestClass1.class.getName());
            MyTestFramework.testPackage("ru.chemarev.andrey.test");
        } catch (ClassNotFoundException | IOException e) {
            e.printStackTrace();
        }
    }

}
