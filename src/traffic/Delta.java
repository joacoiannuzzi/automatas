package traffic;

import java.util.function.Consumer;

public class Delta {

    int nextState;
    Consumer<Integer> deltaFunction;

    public Delta(int nextState, Consumer<Integer> func) {
        this.nextState = nextState;
        this.deltaFunction = func;
    }
}
