package ru.otus;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import ru.otus.dbservice.DBService;
import ru.otus.dbservice.DataSourceH2;
import ru.otus.dbservice.JdbcTemplate;
import ru.otus.domain.Account;
import ru.otus.domain.User;

import javax.sql.DataSource;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class TestDBService {


    @Test
    public void testUser() {

        DataSource dataSource = new DataSourceH2();
        DBService demo = new JdbcTemplate<User>(dataSource);
        User Pit = new User(1, "Pit", 12);
        User Mary = new User(2, "Mary", 8);

        demo.createTable(User.class);
        demo.create(Pit);
        demo.create(Mary);

        User newPit = (User) demo.load(1, User.class);
        assertTrue(newPit.equals(Pit));
        assertFalse(newPit.equals(Mary));


        demo.update(new User(2,"Mary",16));
        User newMary = (User) demo.load(2, User.class);
        assertTrue(newMary.equals(new User(2,"Mary",16)));


    }

    @Test
    public void testAccout() {

        DataSource dataSource = new DataSourceH2();
        DBService demo = new JdbcTemplate<User>(dataSource);
        Account pitAccount = new Account(2, "credit", 12);
        demo.createTable(Account.class);
        demo.create(pitAccount);
        Account newPitAccount = (Account) demo.load(2, Account.class);

        assertTrue(pitAccount.equals(newPitAccount));
    }
}
