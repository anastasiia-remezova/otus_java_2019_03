package ru.otus.ATM;

import java.util.*;

public class StateATM {
    private Map<Integer, Integer> cells = new TreeMap<>(Collections.reverseOrder());
    private ArrayList<Banknote> banknotes = new ArrayList<Banknote>();

    StateATM(List<Banknote.Nominal> banknotes) {

        this.banknotes = new ArrayList<Banknote>();
        banknotes.forEach(b ->addBancknoteNominal(b));

    }

    public Map<Integer, Integer> getCells() {
        return this.cells;
    }

    public void putCell(Integer key, Integer value) {
        cells.put(key, value);
    }

    public void addBanknote(Integer banknoteNominal) {

        if (cells.containsKey(banknoteNominal)) {
            cells.put(banknoteNominal, cells.get(banknoteNominal) + 1);
        } else {
            cells.put(banknoteNominal, 1);
        }
    }

    public void addBancknoteNominal(Banknote.Nominal banknoteNominal) {
        Banknote b = new Banknote();
        b.setNominal(banknoteNominal);
        banknotes.add(b);
    }


    public boolean isSuchBanknotNominal(Integer banknoteNominal) {

        Banknote b = banknotes.stream().filter(x -> x.getNominal().equals(banknoteNominal)).findFirst().orElse(null);

        if (b != null) {
            return true;
        } else {
            return false;
        }
    }

    public int getBalance() {

        return cells.entrySet().stream().mapToInt(e -> e.getKey() * e.getValue()).sum();

    }


}
