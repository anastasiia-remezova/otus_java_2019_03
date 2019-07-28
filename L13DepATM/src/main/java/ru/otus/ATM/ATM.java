package ru.otus.ATM;

import ru.otus.ATM.command.GetMoney;
import ru.otus.ATM.command.SetMoney;

import java.util.*;

public class ATM extends Observable {
    private List<Banknote.Nominal> nominal;
    private StateATM state;
    private ATMUtils atmUtils;
    private SetMoney setMoney;
    private GetMoney getMoney;
    private ATMKeyboard keyboard;

    ATM(ArrayList<Banknote.Nominal> nominal) {
        this.nominal = nominal;
        initialization();
    }

    ATM() {
        this.nominal = Arrays.asList(Banknote.Nominal.values());
        initialization();
    }

    private void initialization() {
        this.state = new StateATM(nominal);
        atmUtils = new ATMUtils(state);
        setMoney = new SetMoney(atmUtils);
        getMoney = new GetMoney(atmUtils);
        keyboard = new ATMKeyboard();
    }

    public void getMoney(Integer sum) {
        keyboard.storeAndExecute(getMoney, sum);
        setChanged();
        notifyObservers(sum*(-1));
    }

    public void setMoney(Integer sum) {
        keyboard.storeAndExecute(setMoney, sum);
        setChanged();
        notifyObservers(sum);
    }

    public Integer getBalance() {
        return state.getBalance();
    }

    public void getOperationList() {
        keyboard.operationList();
    }

    public void resumeState()
    {
        setChanged();
        notifyObservers(this.getBalance() *(-1));
        this.state = new StateATM(this.nominal);
        atmUtils = new ATMUtils(state);
        setMoney = new SetMoney(atmUtils);
        getMoney = new GetMoney(atmUtils);
        keyboard = new ATMKeyboard();
    }
}
