package ru.chemarev.andrey;

import org.junit.Before;
import org.junit.Test;

import java.util.concurrent.ThreadLocalRandom;

import static org.junit.Assert.*;

public class SorterTest {

    private int SIZE = 235;
    private int THREAD_COUNT = 4;

    private Sorter sorter;
    private int[] array;

    @Before
    public void initialize() {
        sorter = new Sorter(THREAD_COUNT);

        array = new int[SIZE];

        for (int i = 0; i < SIZE; i++) {
            array[i] = ThreadLocalRandom.current().nextInt(0, SIZE);
        }
    }

    @Test
    public void testSort() throws InterruptedException {
        int[] result = sorter.sort(array);

        for (int i = 0; i < SIZE - 1; i++)
            if (result[i] > result[i+1])
                fail();

    }
}