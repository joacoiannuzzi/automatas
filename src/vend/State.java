package vend;

public class State {

    String name;
    String output;
    boolean isFinal;

    public State(String name, String output, boolean isFinal) {
        this.name = name;
        this.output = output;
        this.isFinal = isFinal;
    }
}
