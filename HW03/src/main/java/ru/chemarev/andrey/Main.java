package ru.chemarev.andrey;

import java.util.Collections;
import java.util.Random;

public class Main {

    public static void main(String[] args) {
        testAddAll(false);
        testCopy(false);
        testSort(false);
    }

    public static void testAddAll(boolean isShowOnScreen) {
        MyArrayList<String> myArrayList = new MyArrayList<String>();

        long startTime = System.nanoTime();
        Collections.addAll(myArrayList,"One", "Two", "Three");
        long resultTime = System.nanoTime() - startTime;

        if ( isShowOnScreen )
            for ( String element : myArrayList )
                System.out.println(element);


        System.out.println(String.format("Time of addAll() : %d ns", resultTime));
    }

    public static void testCopy(boolean isShowOnScreen) {
        MyArrayList<TestClass> myArrayList = new MyArrayList<>();
        MyArrayList<TestClass> myArrayList_2 = new MyArrayList<>();

        for (int i = 0; i < 1000; i++) {
            if ( i % 2 == 0 )
                myArrayList.add(new TestClass(i, i));
            else
                myArrayList_2.add(new TestClass(i, i));
        }

        long startTime = System.nanoTime();
        Collections.copy(myArrayList_2, myArrayList);
        long resultTime = System.nanoTime() - startTime;

        if ( isShowOnScreen )
            for ( TestClass element : myArrayList_2 )
                System.out.println(element);

        System.out.println(String.format("Time of copy() : %d ns", resultTime));
    }

    public static void testSort(boolean isShowOnScreen) {
        Random random = new Random();
        MyArrayList<TestClass> myArrayList = new MyArrayList<>();

        for (int i = 0; i < 1000; i++)
            myArrayList.add(new TestClass(i, random.nextInt(1000) + 1));

        long startTime = System.nanoTime();
        Collections.sort(myArrayList, new TestComparator());
        long resultTime = System.nanoTime() - startTime;

        if ( isShowOnScreen )
            for ( TestClass element : myArrayList )
                System.out.println(element);


        System.out.println(String.format("Time of sort() : %d ns", resultTime));
    }
}
