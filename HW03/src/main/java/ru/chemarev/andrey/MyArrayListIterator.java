package ru.chemarev.andrey;

import sun.reflect.generics.reflectiveObjects.NotImplementedException;

import java.util.ListIterator;

class MyArrayListIterator<T> implements ListIterator<T> {

    public MyArrayListIterator(MyArrayList<T> myArrayList) {
        this.myArrayList = myArrayList;
    }

    @Override
    public boolean hasNext() {
        return counter == myArrayList.size() ? false : true;
    }

    @Override
    public T next() {
        return myArrayList.get(counter++);
    }

    @Override
    public boolean hasPrevious() {
        throw new NotImplementedException();
    }

    @Override
    public T previous() {
        throw new NotImplementedException();
    }

    @Override
    public int nextIndex() {
        throw new NotImplementedException();
    }

    @Override
    public int previousIndex() {
        throw new NotImplementedException();
    }

    @Override
    public void remove() {
        throw new NotImplementedException();
    }

    @Override
    public void set(T t) {
        myArrayList.add(t);
    }

    @Override
    public void add(T t) {
        throw new NotImplementedException();
    }

    private MyArrayList<T> myArrayList;
    private int counter = 0;
}