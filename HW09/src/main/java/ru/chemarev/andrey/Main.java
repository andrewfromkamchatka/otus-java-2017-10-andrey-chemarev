package ru.chemarev.andrey;

import ru.chemarev.andrey.core.ConnectionHelper;

import java.sql.Connection;
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
            Connection connection = ConnectionHelper.getConnection();
            Executor executor = new Executor(connection);

            UserDataSet user = new UserDataSet();
            user.setName("Andrew");
            user.setAge(29);

            executor.save(user);

            UserDataSet userFromDB = executor.load(1, UserDataSet.class);
            System.out.println(userFromDB);

            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
