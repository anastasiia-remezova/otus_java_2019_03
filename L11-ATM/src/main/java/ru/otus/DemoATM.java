package ru.otus;

public class DemoATM {
    public static void main(String[] args) throws Exception {
        ATM atm = new ATM();
        atm.setMoney(100);
        atm.setMoney(1000);
        atm.setMoney(200);
        atm.setMoney(400);
        System.out.println(atm.getBalance());
    }
}
