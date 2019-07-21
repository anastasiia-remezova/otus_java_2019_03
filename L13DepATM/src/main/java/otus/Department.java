package otus;

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

    public static void main(String[] args) {

        Department d = new Department();
        d.addATM(new ATM());
        d.addATM(new ATM());
        d.addATM(new ATM());

        d.getATM(0).setMoney(100);
        d.getATM(0).setMoney(500);
        d.getATM(0).setMoney(200);
        d.getATM(1).setMoney(100);
        d.getATM(2).setMoney(100);

        System.out.println("departmentBalance:" + d.getAllBalance());
        d.getATM(0).getMoney(200);
        System.out.println(d.getATM(0).getBalance());
        System.out.println("departmentBalance:" + d.getAllBalance());

        d.getATM(1).resumeState();
        System.out.println("d.atm1: " + d.getATM(1).getBalance());
        System.out.println(d.getAllBalance());
        
    }
}
