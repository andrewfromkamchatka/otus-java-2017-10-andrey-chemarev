package ru.chemarev.andrey;

import ru.chemarev.andrey.core.DBService;

import java.sql.SQLException;

/*
create user otus with password 'otus';

create sequence user_id_seq;

create table "User" (
	id bigint primary key default nextval('user_id_seq'),
    name varchar(255),
    age int
);
 */
public class Main {
    public static void main(String[] args) {

        try {
            DBService dbService = new DBServiceImpl();

            UserDataSet user = new UserDataSet();
            user.setName("Andrew");
            user.setAge(29);

            dbService.save(user);
            System.out.println("Saved user: " + user);

            UserDataSet userFromDB = dbService.read(1);
            System.out.println("Read user: " + userFromDB);

            dbService.shutdown();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
