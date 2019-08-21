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
    static User Pit = null;
    static User newPit = null;
    static User Mary = null;
    static Account pitAccount = null;
    static Account newPitAccount = null;


    public static @BeforeAll
    void initUser() {

        DataSource dataSource = new DataSourceH2();
        DBService demo = new JdbcTemplate<User>(dataSource);
        Pit = new User(1, "Pit", 12);
        Mary = new User(2, "Mary", 8);

        demo.create(User.class);
        demo.update(Pit);
        demo.update(Mary);


        newPit = (User) demo.load(1, User.class);


    }

    public static @BeforeAll
    void initAccount() {
        DataSource dataSource = new DataSourceH2();
        DBService demo = new JdbcTemplate<User>(dataSource);
        pitAccount = new Account(2, "credit", 12);
        demo.create(Account.class);
        demo.update(pitAccount);
        newPitAccount = (Account) demo.load(2, Account.class);
    }

    @Test
    public void testUser() {
        assertTrue(newPit.equals(Pit));
        assertFalse(newPit.equals(Mary));
    }

    @Test
    public void testAccout() {
        assertTrue(pitAccount.equals(newPitAccount));
    }
}
