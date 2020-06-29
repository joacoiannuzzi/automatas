package traffic;

import java.util.function.Consumer;

public class Delta {

    int event;
    int nextState;
    Consumer<Integer> deltaFunction;

    public Delta(int event, int nextState, Consumer<Integer> func) {
        this.event = event;
        this.nextState = nextState;
        this.deltaFunction = func;
    }
}
