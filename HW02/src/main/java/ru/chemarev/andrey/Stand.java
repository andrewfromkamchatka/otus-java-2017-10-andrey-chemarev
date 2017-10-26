package ru.chemarev.andrey;

import java.util.*;

public class Stand {

    public Stand() {
        runtime = Runtime.getRuntime();
    }

    public void heatMemory(int cyclesCount) {
        for (int i = 1; i < cyclesCount; i++)
            System.gc();
    }

    public long getArraySize(Object[] array) {
        return  getObjectSize( () -> Arrays.copyOf(array, array.length) );
    }

    public long getStringSize(String string) {
        return getObjectSize( () -> new String(string.toCharArray()) );
    }

    public <T> long getArrayListSize(ArrayList<T> list) {
        return getObjectSize( () -> new ArrayList<>(list) );
    }

    public <T> long getLinkedListSize(LinkedList<T> list) {
        return getObjectSize( () -> new LinkedList<>(list) );
    }

    public long getObjectSize(StandLambda initiateOrDeepCopy) {
        long used_memory_1 = gcAndGetUsedMemory();
        Object temp = initiateOrDeepCopy.getStandObject();
        long used_memory_2 = gcAndGetUsedMemory();

        return used_memory_2 - used_memory_1;
    }

    private long gcAndGetUsedMemory() {
        System.gc();
        return runtime.totalMemory() - runtime.freeMemory();
    }

    private Runtime runtime;
}

interface StandLambda {
    Object getStandObject();
}