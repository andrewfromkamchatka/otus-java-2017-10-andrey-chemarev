package ru.chemarev.andrey;

import org.junit.Before;
import org.junit.Test;
import ru.chemarev.andrey.atm.ATM;
import ru.chemarev.andrey.atm.Bill;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

public class DepartmentTest {

    private Department department;
    private List<ATM> atms;

    @Before
    public void initialize() {
        department = new Department();
        atms = new ArrayList<>();

        ATM atm = ATM.getDefaultATM();
        atm.putMoney(Bill.oneHundred());
        department.registerATM( new DepartmentATM(atm) );
        atms.add(atm);

        atm = ATM.getDefaultATM();
        atm.putMoney(Bill.fiveHundred());
        department.registerATM( new DepartmentATM(atm) );
        atms.add(atm);

        atm = ATM.getDefaultATM();
        atm.putMoney(Bill.oneThousand());
        department.registerATM( new DepartmentATM(atm) );
        atms.add(atm);
    }

    @Test
    public void gelAllBalance() throws Exception {
        assertEquals(1600, department.gelAllBalance());
    }

    @Test
    public void setAllToStartState() throws Exception {
        atms.get(0).putMoney(Bill.fiveThousand());
        assertEquals(6600, department.gelAllBalance());

        department.setAllToStartState();
        assertEquals(1600, department.gelAllBalance());
    }

}