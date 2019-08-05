package ru.otus;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import ru.otus.dbservice.DBService;
import ru.otus.dbservice.DataSourceH2;
import ru.otus.dbservice.JdbcTemplate;
import ru.otus.domain.Account;
import ru.otus.domain.User;

import javax.sql.DataSource;

public class TestDBService {

    public static  @BeforeAll
    void initUser(){

        DataSource dataSource = new DataSourceH2();
        DBService demo = new JdbcTemplate<User>(dataSource);
        User Pit = new User(1, "Pit", 12);
        User Mary = new User(2, "Mary", 8);
        demo.create(Pit);
        demo.update(Pit);
        demo.update(Mary);
        User NewPit = (User) demo.load(1,User.class);
    }

    public static @BeforeAll
    void initAccount(){
        DataSource dataSource = new DataSourceH2();
        DBService demo = new JdbcTemplate<User>(dataSource);
        Account pitAccount = new Account(2, "credit", 12);
        demo.create(pitAccount);
        demo.update(pitAccount);

    }

    @Test
    public void testUser(){

    }

    @Test
    public void testAccout(){

    }
}
