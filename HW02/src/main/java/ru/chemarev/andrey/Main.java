package ru.chemarev.andrey;

import java.util.ArrayList;
import java.util.LinkedList;

public class Main {

    public static void main(String... args) {
        Stand stand = new Stand();

        String emptyString = "";
        System.out.println( String.format("Empty string size : %d", stand.getStringSize(emptyString)) );
        stand.heatMemory(10);

        System.out.println( String.format("Object size : %d", stand.getObjectSize( () -> new Object() ) ) );

        TestClass test = new TestClass();
        System.out.println( String.format("TestClass object size : %d", stand.getObjectSize( () -> new TestClass(test)) ) );

        System.out.println("");
        System.out.println(String.format("Empty ArrayList size : %d", stand.getObjectSize( () -> new ArrayList<>() )));
        ArrayList<TestClass> testArrayList = new ArrayList<>();
        for (int i = 1; i <= 1000; i++) {
            testArrayList.add(new TestClass());
            if (i % 200 == 0) {
                long objectSize = stand.getArrayListSize(testArrayList);
                long elementSize = objectSize / testArrayList.size();
                System.out.println(String.format("ArrayList with %d element : %d bytes, ~ element size %d bytes ", i, objectSize, elementSize));
            }
        }
        stand.heatMemory(5);

        System.out.println("");
        System.out.println(String.format("Empty LinkedList size : %d", stand.getObjectSize( () -> new ArrayList<>() )));
        LinkedList<TestClass> linkedList = new LinkedList<>();
        for (int i = 1; i <= 1000; i++) {
            linkedList.add(new TestClass());
            if (i % 200 == 0) {
                long objectSize = stand.getLinkedListSize(linkedList);
                long elementSize = objectSize / linkedList.size();
                System.out.println(String.format("LinkedList with %d element : %d bytes, ~ element size %d bytes ", i, objectSize, elementSize));
            }
        }
    }
}

class TestClass {

    public TestClass() {
        super();
    }

    public TestClass(TestClass cloned) {
        this.a = cloned.a;
        this.b = cloned.b;
        this.c = cloned.c;
    }

    int a = 5;
    long b = 10;
    byte c = 1;
}
