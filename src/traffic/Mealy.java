package traffic;

import java.util.Map;

public class Mealy {

    Map<Integer, String> statesNames;
    Map<Integer, String> events;
    Map<Integer, Map<Integer, Delta>> deltas;
    int currentState;

    public Mealy(Map<Integer, String> statesNames, Map<Integer, String> events, int initialState, Map<Integer, Map<Integer, Delta>> deltas) {
        this.statesNames = statesNames;
        this.events = events;
        this.currentState = initialState;
        this.deltas = deltas;
    }

    public void transition(int event) {
        int newState = deltas.get(currentState).get(event).nextState;
        System.out.printf("%s + %s -> %s\n",
                statesNames.get(currentState),
                events.get(event),
                statesNames.get(newState)
        );
        deltas.get(currentState).get(event).deltaFunction.accept(event);
        currentState = newState;
    }


}
