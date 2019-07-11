package ru.otus;

import ru.otus.exception.BanknoteNominalException;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class ATM {
    private Map<Integer, Integer> cells = new HashMap<>();
    private ArrayList<Banknote> banknotes = new ArrayList<Banknote>();

    public ATM() {
        banknotes = new ArrayList<Banknote>();
        addBancknoteNominal(100);
        addBancknoteNominal(200);
        addBancknoteNominal(500);
        addBancknoteNominal(1000);
        addBancknoteNominal(2000);

    }

    //- принимать банкноты разных номиналов (на каждый номинал должна быть своя ячейка)
    public void setMoney(Integer banknoteNominal) throws Exception {

        if (isSuchBanknotNominal(banknoteNominal)) {

            addBanknote(banknoteNominal);

        } else {
            System.out.println("Wrong Banknote Nominal " + banknoteNominal);
         
        }

    }

    // - выдавать запрошенную сумму минимальным количеством банкнот или ошибку если сумму нельзя выдать
    public ArrayList<Banknote> getMoney(int sum) throws Exception {
        return null;
    }

    //- выдавать сумму остатка денежных средств
    public int getBalance() {
        Integer balance =0;
        return cells.entrySet().stream().mapToInt(e->e.getKey()*e.getValue()).sum();

    }

    private void addBanknote(Integer banknoteNominal) {

        if (cells.containsKey(banknoteNominal)) {
            cells.put(banknoteNominal, cells.get(banknoteNominal) + 1);
        } else {
            cells.put(banknoteNominal, 1);
        }
    }

    private boolean isSuchBanknotNominal(Integer banknoteNominal) {

        Banknote b = banknotes.stream().filter(x -> x.getNominal().equals(banknoteNominal)).findFirst().orElse(null);

        if (b != null) {
            return true;
        } else {
            return false;
        }

    }


    private void addBancknoteNominal(Integer banknoteNominal) {
        Banknote b = new Banknote();
        b.setNominal(banknoteNominal);
        //System.out.println();
        banknotes.add( b);
    }
}
