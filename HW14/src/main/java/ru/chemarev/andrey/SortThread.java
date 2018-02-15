package ru.chemarev.andrey;

import java.util.Arrays;

public class SortThread extends Thread {
    private int[] array;

    public SortThread(int[] array) {
        this.array = array;
    }

    @Override
    public void run() {
        Arrays.sort(array);
    }

    public int[] getSortedArray() {
        return array;
    }
}
