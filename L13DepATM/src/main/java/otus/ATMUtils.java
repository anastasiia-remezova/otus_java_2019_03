package otus;

import java.util.*;

public class ATMUtils  {
    private StateATM state;

    public ATMUtils(StateATM atm) {
        this.state = atm;
    }

    //- принимать банкноты разных номиналов (на каждый номинал должна быть своя ячейка)
    public void setMoney(Integer banknoteNominal) {

        if (state.isSuchBanknotNominal(banknoteNominal)) {
            state.addBanknote(banknoteNominal);

        } else {
            System.out.println("Wrong Banknote Nominal " + banknoteNominal);
        }

    }

    // - выдавать запрошенную сумму минимальным количеством банкнот или ошибку если сумму нельзя выдать
    public void getMoney(Integer sum) {
        Map<Integer, Integer> cashMap = new HashMap<>();
        if (sum > state.getBalance()) {
            System.out.println("Insufficient funds");
        } else {

            Integer currentSum = sum;

            for (Integer i : state.getCells().keySet()) {
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
                            state.getCells().entrySet().stream().forEach(
                                    c -> {
                                        if (cm.getKey().equals(c.getKey())) {
                                            state.putCell(c.getKey(), c.getValue() - cm.getValue());
                                        }
                                    }
                            );
                        }
                );
            }
        }

    }


}



