package otus;

import org.javatuples.Pair;
import java.util.Deque;
import java.util.LinkedList;

public class ATMKeyboard {


    private final Deque<Pair<Command, Integer>> history = new LinkedList<Pair<Command, Integer>>();

    public void storeAndExecute(Command cmd,Integer sum ) {
        this.history.add(new Pair(cmd, sum));
         cmd.execute((Integer) sum);
    }

    public void operationList() {
        history.forEach(h -> System.out.println(h.getValue0().getClass() + " " + h.getValue1()));
    }

}
