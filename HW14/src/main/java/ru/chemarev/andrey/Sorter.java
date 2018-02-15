package ru.chemarev.andrey;

import java.util.Arrays;

public class Sorter {
    private int THREAD_COUNT;
    private SortThread[] threads;

    public Sorter(int threadCount) {
        THREAD_COUNT = threadCount;
        threads = new SortThread[threadCount];
    }

    public int[] sort(int[] array) throws InterruptedException {
        initialize(array);
        start();
        return buildResult();
    }

    private void initialize(int[] array) {
        int step = array.length / THREAD_COUNT;
        for (int i = 0; i < THREAD_COUNT; i++) {
            int start = i * step;
            int end = (i != THREAD_COUNT - 1) ? start + step : array.length;

            threads[i] = new SortThread(Arrays.copyOfRange(array, start, end));
        }
    }

    private void start() throws InterruptedException {
        for (int i = 0; i < THREAD_COUNT; i++) {
            threads[i].start();
        }

        for (int i = 0; i < THREAD_COUNT; i++) {
            threads[i].join();
        }
    }

    private int[] buildResult() {
        int[] result = threads[0].getSortedArray();
        for (int i = 1; i < THREAD_COUNT; i++) {
            result = mergeArrays(result, threads[i].getSortedArray());
        }

        return result;
    }

    private int[] mergeArrays(int[] array1, int[] array2) {
        int[] result = new int[array1.length + array2.length];

        int i = 0; int j = 0; int k = 0;

        while (i < array1.length && j < array2.length) {
            if (array1[i] < array2[j]) {
                result[k] = array1[i];
                i++;
            } else {
                result[k] = array2[j];
                j++;
            }
            k++;
        }

        if (i < array1.length) {
            System.arraycopy(array1, i, result, k, (array1.length - i));
        }

        if (j < array2.length) {
            System.arraycopy(array2, j, result, k, (array2.length - j));
        }

        return result;
    }

}
