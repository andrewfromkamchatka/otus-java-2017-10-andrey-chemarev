package ru.chemarev.andrey;

import sun.reflect.generics.reflectiveObjects.NotImplementedException;

import java.util.*;
import java.util.function.UnaryOperator;

public class MyArrayList<T> implements List<T> {

    public MyArrayList() {
        array = new Object[0];
    }

    @Override
    public int size() {
        return currentPosition;
    }

    @Override
    public boolean isEmpty() {
        return currentPosition == 0;
    }

    @Override
    public boolean contains(Object o) {
        throw new NotImplementedException();
    }

    @Override
    public Iterator<T> iterator() {
        return new MyArrayListIterator<>(this);
    }

    @Override
    public Object[] toArray() {
        throw new NotImplementedException();
    }

    @Override
    public <T1> T1[] toArray(T1[] a) {
        throw new NotImplementedException();
    }

    @Override
    public boolean add(T t) {

        if ( currentPosition == array.length )
            if (array.length + STEP > Integer.MAX_VALUE)
                return false;
            else
                array = Arrays.copyOf(array, array.length + STEP);

        array[currentPosition] = t;
        currentPosition++;

        return true;
    }

    @Override
    public boolean remove(Object o) {
        for ( int i = 0; i < currentPosition; i++)
            if ( o.equals(array[i]) ) {
                System.arraycopy(array, i+1, array, i, currentPosition-i-1);
                array[currentPosition-1] = null;
                currentPosition--;
                return true;
            }

        return false;
    }

    @Override
    public boolean containsAll(Collection<?> c) {
        throw new NotImplementedException();
    }

    @Override
    public boolean addAll(Collection<? extends T> c) {
        throw new NotImplementedException();
    }

    @Override
    public boolean addAll(int index, Collection<? extends T> c) {
        throw new NotImplementedException();
    }

    @Override
    public boolean removeAll(Collection<?> c) {
        throw new NotImplementedException();
    }

    @Override
    public boolean retainAll(Collection<?> c) {
        throw new NotImplementedException();
    }

    @Override
    public void replaceAll(UnaryOperator<T> operator) {
        throw new NotImplementedException();
    }

    @Override
    public void sort(Comparator<? super T> c) {
        bubbleSort(c);
    }

    @Override
    public void clear() {
        throw new NotImplementedException();
    }

    @Override
    public T get(int index) {
        if ( index > currentPosition )
            throw new IndexOutOfBoundsException(String.format("Index (%d) is not in the list", index, size()));

        return (T) array[index];
    }

    @Override
    public T set(int index, T element) {
        throw new NotImplementedException();
    }

    @Override
    public void add(int index, T element) {
        throw new NotImplementedException();
    }

    @Override
    public T remove(int index) {
        throw new NotImplementedException();
    }

    @Override
    public int indexOf(Object o) {
        throw new NotImplementedException();
    }

    @Override
    public int lastIndexOf(Object o) {
        throw new NotImplementedException();
    }

    @Override
    public ListIterator<T> listIterator() {
        return new MyArrayListIterator<>(this);
    }

    @Override
    public ListIterator<T> listIterator(int index) {
        throw new NotImplementedException();
    }

    @Override
    public List<T> subList(int fromIndex, int toIndex) {
        throw new NotImplementedException();
    }

    private void bubbleSort(Comparator<? super T> c) {
        for (int i = 0; i < currentPosition - 1; i++) {
            boolean isSorted = true;

            for (int j = i + 1; j < currentPosition; j++) {

                if (c.compare((T) array[i], (T) array[j]) == 1) {
                    Object o = array[i];
                    array[i] = array[j];
                    array[j] = o;
                    isSorted = false;
                }
            }

            if ( isSorted )
                break;
        }
    }

    private Object[] array;
    private int currentPosition = 0;
    private static int STEP = 100;
}