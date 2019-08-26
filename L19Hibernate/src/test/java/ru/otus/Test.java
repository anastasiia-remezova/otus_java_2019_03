package ru.otus;

import dbservice.DBService;
import dbservice.DBServiceImpl;
import domain.User;

import javax.sql.DataSource;

public class Test {

    @org.junit.jupiter.api.Test
    void userTest()
    {
        DBService db = new DBServiceImpl();
        User Luzer = new User(1,"Luiza",18);
        db.create(Luzer);
        db.update(new User(1,"Cat",12));
        db.load(1,User.class);

    }

}
