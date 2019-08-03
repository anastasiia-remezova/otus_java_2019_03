package ru.otus.ATM.command;

import ru.otus.ATM.ATMUtils;

public class SetMoney implements Command {

    private final ATMUtils atm;

    public SetMoney (ATMUtils atm) {
        this.atm = atm;
    }

    @Override    // Command
    public void execute(Integer sum) {
      atm.setMoney(sum);
    }

}
