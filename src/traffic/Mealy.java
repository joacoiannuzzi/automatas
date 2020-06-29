package traffic;

public class Mealy {

    String[] statesNames;
    String[] events;
    Delta[][] deltas;
    int currentState;

    public Mealy(String[] states, String[] events, int initialState, Delta[][] deltas) {
        this.statesNames = states;
        this.events = events;
        this.currentState = initialState;
        this.deltas = deltas;
    }

    public void transition(int event) {
        int newState = deltas[currentState][event].nextState;
        System.out.printf("%s + %s -> %s\n",
                statesNames[currentState],
                events[event],
                statesNames[newState]
        );
        deltas[currentState][event].deltaFunction.accept(event);
        currentState = newState;
    }


}
