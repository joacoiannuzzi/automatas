package vend;


import java.util.Map;
import java.util.Scanner;

public class Vend {

    // State enumeration
    static int S0 = 0, S1 = 1, S2 = 2, S3 = 3;

    // input alpha classification
    static int P1 = 0, P2 = 1;


    private final Map<Integer, State> states = Map.ofEntries(
            Map.entry(S0, new State("S0", "No expending", false)),
            Map.entry(S1, new State("S1", "No expending", false)),
            Map.entry(S2, new State("S2", "No expending", false)),
            Map.entry(S3, new State("S3", "Expending", true))
    );
    private final Map<Character, Alpha> alphas = Map.ofEntries(
            Map.entry('1', new Alpha(P1, "\"Coin $1\"")),
            Map.entry('2', new Alpha(P2, "\"Coin $2\""))
    );

    Map<Integer, Integer> s0_state = Map.ofEntries(
            Map.entry(P1, S1),
            Map.entry(P2, S2)
    );

    Map<Integer, Integer> s1_state = Map.ofEntries(
            Map.entry(P1, S2),
            Map.entry(P2, S3)
    );
    Map<Integer, Integer> s2_state = Map.ofEntries(
            Map.entry(P1, S3),
            Map.entry(P2, S3)
    );
    Map<Integer, Integer> s3_state = Map.ofEntries();

    private final Map<Integer, Map<Integer, Integer>> deltas = Map.ofEntries(
            Map.entry(S0, s0_state),
            Map.entry(S1, s1_state),
            Map.entry(S2, s2_state),
            Map.entry(S3, s3_state)
    );


    Moore moore = new Moore(
            states,
            alphas,
            S0,
            deltas
    );

    void run() {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter pattern: ");
        String input = scanner.next();
        moore.processInput(input);
    }

    public static void main(String[] args) {
        new Vend().run();
    }


}
