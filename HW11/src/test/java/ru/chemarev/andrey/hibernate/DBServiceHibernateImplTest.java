package ru.chemarev.andrey.hibernate;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import ru.chemarev.andrey.core.*;

import static org.junit.Assert.*;

public class DBServiceHibernateImplTest {

    private DBService dbService;
    private UserDataSet user;
    private UserDataSet userFromDB;

    @Before
    public void initialize() {
        dbService = new DBServiceHibernateImpl();

        user = new UserDataSet();
        user.setName("Andrew");
        user.setAge(29);
        user.setAddress(new AddressDataSet("MyAddressStreet"));

        user.addPhone("8-789-456-12-23");
        user.addPhone("1-234-567-78-89");

        dbService.save(user);

        userFromDB = (UserDataSet) dbService.read(1);
    }

    @Test
    public void test() throws Exception {
        assertEquals(user, userFromDB);
    }

    @After
    public void after() {
        dbService.shutdown();
    }

}