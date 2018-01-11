package ru.chemarev.andrey;

import ru.chemarev.andrey.core.AddressDataSet;
import ru.chemarev.andrey.core.DBService;
import ru.chemarev.andrey.core.UserDataSet;
import ru.chemarev.andrey.hibernate.DBServiceHibernateImpl;

public class Main {
    public static void main(String[] args) {

        DBService dbService = new DBServiceHibernateImpl();

        UserDataSet user = new UserDataSet();
        user.setName("Andrew");
        user.setAge(29);
        user.setAddress(new AddressDataSet("MyAddressStreet"));

        user.addPhone("8-789-456-12-23");
        user.addPhone("1-234-567-78-89");

        dbService.save(user);

        UserDataSet userFromDB = dbService.read(1);
        System.out.println(userFromDB.toString());

        dbService.shutdown();

    }
}
