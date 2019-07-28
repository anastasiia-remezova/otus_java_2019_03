package ru.otus.ATM.command;

import ru.otus.ATM.ATMUtils;

public class GetMoney implements Command {



    private final ATMUtils atm;

    public GetMoney (ATMUtils atm) {
        this.atm = atm;
    }

    @Override    // Command
    public void execute(Integer sum) {
        atm.getMoney(sum);

    }
}
