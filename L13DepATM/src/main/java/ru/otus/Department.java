package ru.otus;

import ru.otus.ATM.ATM;

import java.util.ArrayList;
import java.util.List;

public class Department {

    private static Integer departmentBalance;
    private List<ATM> departmentATMs = new ArrayList<ATM>();

    Department() {
        departmentBalance = 0;
    }

    ATM getATM(Integer number) {
        return departmentATMs.get(number);
    }

    List<ATM> getDepartment() {
        return departmentATMs;
    }

    public void addATM(ATM atm) {
        departmentATMs.add(atm);
        atm.addObserver((obj, arg) -> {
            departmentBalance = departmentBalance + (Integer) arg;
            System.out.println("Received response ATM " + arg
            );
        });

    }

    public Integer getAllBalance() {
        return departmentBalance;
    }
}
