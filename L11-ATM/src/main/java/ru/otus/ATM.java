package ru.otus;

import ru.otus.exception.BanknoteNominalException;

import java.util.*;

public class ATM {
    private Map<Integer, Integer> cells = new TreeMap<>(Collections.reverseOrder());
    private ArrayList<Banknote> banknotes = new ArrayList<Banknote>();

    public ATM() {
        banknotes = new ArrayList<Banknote>();
        addBancknoteNominal(Banknote.Nominal.ONE);
        addBancknoteNominal(Banknote.Nominal.TWO);
        addBancknoteNominal(Banknote.Nominal.FIFE);
        addBancknoteNominal(Banknote.Nominal.TEN);
        addBancknoteNominal(Banknote.Nominal.TWENTY);
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
    public Map<Integer, Integer> getMoney(int sum) throws Exception {
        Map<Integer, Integer> cashMap = new HashMap<>();
        if (sum > getBalance()) {
            System.out.println("Insufficient funds");
        } else {

            Integer currentSum = sum;

            for (Integer i : cells.keySet()) {
                cashMap.put(i, currentSum / i);
                currentSum = currentSum - (i * (currentSum / i));
            }
            cashMap.entrySet().forEach(c -> System.out.println("Nominal: " + c.getKey() + " Count: " + c.getValue()));
            if (currentSum != 0) {
                System.out.println("Not enough banknotes");
                cashMap.clear();
            } else {
                cashMap.entrySet().stream().forEach(cm ->
                        {
                            cells.entrySet().stream().forEach(
                                    c -> {
                                        if (cm.getKey().equals(c.getKey())) {
                                            cells.put(c.getKey(), c.getValue() - cm.getValue());
                                        }
                                    }
                            );
                        }
                );

            }

        }
        return cashMap;
    }

    //- выдавать сумму остатка денежных средств
    public int getBalance() {
        Integer balance = 0;
        return cells.entrySet().stream().mapToInt(e -> e.getKey() * e.getValue()).sum();

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


    private void addBancknoteNominal(Banknote.Nominal banknoteNominal) {
        Banknote b = new Banknote();
        b.setNominal(banknoteNominal);
        banknotes.add(b);
    }
}
