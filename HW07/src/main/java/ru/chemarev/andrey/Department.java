package ru.chemarev.andrey;

import java.util.ArrayList;
import java.util.List;

public class Department {

    public Department() {
        atms = new ArrayList<>();
    }

    public long gelAllBalance() {
        return atms.stream()
                .mapToLong( atm -> atm.getBalance() )
                .sum();
    }

    public void setAllToStartState() {
        atms.stream()
                .forEach( atm -> atm.restoreState() );
    }

    public void registerATM(DepartmentATM atm) {
        atms.add(atm);
    }

    List<DepartmentATM> atms;
}
