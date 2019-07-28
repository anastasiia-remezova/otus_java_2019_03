package ru.otus;

public class Banknote {

    private Integer nominal;



    public Integer getNominal() {
        return nominal;
    }

    public void setNominal(Nominal nominal) {
        this.nominal = nominal.value;
    }

    public enum Nominal
    {
        ONE(100), TWO(200),FIVE(500), TEN(1000),TWENTY(2000);
        private final int value;

        private Nominal(int value) {
            this.value = value;
        }

    };


}
