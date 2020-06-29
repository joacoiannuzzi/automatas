package vend;

import java.util.Map;

public class Moore {

    Map<Integer, State> states;
    Map<Character, Alpha> alphas;
    int state;
    Map<Integer, Map<Integer, Integer>> deltas;

    public Moore(Map<Integer, State> states, Map<Character, Alpha> alphas, int state, Map<Integer, Map<Integer, Integer>> deltas) {
        this.states = states;
        this.alphas = alphas;
        this.state = state;
        this.deltas = deltas;
    }

    void processInput(String input) {
        char[] chars = input.toCharArray();

        for (char aChar : chars) {
            Alpha alpha = alphas.get(aChar);
            if (alpha != null) {
                int newState = deltas.get(state).get(alpha.input);
                System.out.printf("\"%-8s\" -> %-8s -> \"%-8s\" = \"%s\"\n",
                        states.get(state).name,
                        alpha.name,
                        states.get(newState).name,
                        states.get(newState).output
                );
                state = newState;
                if (states.get(state).isFinal) {
                    System.out.printf("%s...\n", "ACCEPTED");
                    return;
                }
            } else {
                System.out.printf("%s...\n", "BAD_SYMBOL");
                return;
            }
        }
        System.out.printf("%s...\n", "REJECTED");

    }

}
