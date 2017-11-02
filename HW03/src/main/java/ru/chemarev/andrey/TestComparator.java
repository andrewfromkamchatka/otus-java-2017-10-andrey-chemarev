package ru.chemarev.andrey;

import java.util.Comparator;

class TestComparator implements Comparator<TestClass> {

    @Override
    public int compare(TestClass o1, TestClass o2) {
        if ( o1.getValue() < o2.getValue() )
            return -1;
        else if ( o1.getValue() > o2.getValue() )
            return 1;
        else
            return 0;
    }

}
