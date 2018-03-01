package ru.chemarev.andrey;

import ru.chemarev.andrey.app.DBService;
import ru.chemarev.andrey.db.dataset.AddressDataSet;
import ru.chemarev.andrey.db.dataset.UserDataSet;

import java.util.concurrent.ThreadLocalRandom;

public class Emulation {

    private DBService dbService;

    public Emulation(DBService dbService) {
        this.dbService = dbService;
    }

    public void cacheWork() {
        Runnable runnable = () -> {
            for (int i = 1; i < Integer.MAX_VALUE; i++) {
                dbService.saveUser(generateUser());

                if (i % 50 == 0) {

                    try {
                        Thread.sleep(2 * 1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }

                    for (int j = 1; j < 25; j++) {
                        dbService.getUser(ThreadLocalRandom.current().nextInt(i - 50, i));
                    }
                }
            }
        };

        Thread thread = new Thread(runnable);
        thread.start();
    }

    private UserDataSet generateUser() {
        UserDataSet userDataSet = new UserDataSet();

        userDataSet.setName("Andrew");
        userDataSet.setAge(29);
        userDataSet.setAddress(new AddressDataSet("MyAddressStreet"));

        userDataSet.addPhone("8-789-456-12-23");
        userDataSet.addPhone("1-234-567-78-89");

        return userDataSet;
    }
}
