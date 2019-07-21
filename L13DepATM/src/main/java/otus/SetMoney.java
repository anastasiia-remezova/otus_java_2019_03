package otus;

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
