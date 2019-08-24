package ru.otus;


import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import ru.otus.ATM.ATM;

import static org.junit.jupiter.api.Assertions.assertEquals;


public class DepartmentTest {
    static Department d = new Department();

    @Test
    public void testAllBalance() {

        d.addATM(new ATM());
        d.addATM(new ATM());
        d.addATM(new ATM());

        d.getATM(0).setMoney(100);
        d.getATM(0).setMoney(500);
        d.getATM(0).setMoney(200);
        d.getATM(1).setMoney(100);
        d.getATM(2).setMoney(100);

        assertEquals(d.getAllBalance(),(Integer) 1000);
        d.getATM(0).getMoney(200);
        assertEquals(d.getAllBalance(),(Integer) 800);
        d.getATM(1).resumeState();
        assertEquals(d.getAllBalance(),(Integer) 700);
        assertEquals(d.getATM(1).getBalance(),(Integer) 0);

    }

}
