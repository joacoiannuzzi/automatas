package traffic;

import java.util.Map;
import java.util.function.Consumer;

public class Traffic {

    //states
    static int EW_GO = 0;
    static int EW_STOPPING = 1;
    static int NS_GO = 2;
    static int NS_STOPPING = 3;

    //events
    static int T1 = 0;

    //times
    static int YELLOW_TIME = 3;
    static int GREEN_TIME = 15;

    // bit value for each color
    static int RED = 0x04;
    static int YELLOW = 0x02;
    static char GREEN = 0x01;

    //  indexes for each traffic light
    static int EW = 0;
    static int NS = 1;

    public static void main(String[] args) {
        Map<Integer, String> statesNames = Map.ofEntries(
                Map.entry(EW_GO, "EW_GO"),
                Map.entry(EW_STOPPING, "EW_STOPPING"),
                Map.entry(NS_GO, "NS_GO"),
                Map.entry(NS_STOPPING, "NS_STOPPING")
        );
        Map<Integer, String> events = Map.of(T1, "Timer");

        int[] lights = new int[2];
        Timer timer = new Timer();

        Consumer<Integer> ew_yellow = event -> {
            lights[EW] |= YELLOW;
            timer.set(YELLOW_TIME);
        };

        Consumer<Integer> ns_yellow = event -> {
            lights[NS] |= YELLOW;
            timer.set(YELLOW_TIME);
        };

        Consumer<Integer> ew_green = event -> {
            lights[EW] = GREEN;
            lights[NS] = RED;
            timer.set(GREEN_TIME);
        };

        Consumer<Integer> ns_green = event -> {
            lights[NS] = GREEN;
            lights[EW] = RED;
            timer.set(GREEN_TIME);
        };

        Map<Integer, Delta> ew_go = Map.of(
                T1, new Delta(EW_STOPPING, ew_yellow)
        );
        Map<Integer, Delta> ew_stopping = Map.of(
                T1, new Delta(NS_GO, ns_green)
        );
        Map<Integer, Delta> ns_go = Map.of(
                T1, new Delta(NS_STOPPING, ns_yellow)
        );
        Map<Integer, Delta> ns_stopping = Map.of(
                T1, new Delta(EW_GO, ew_green)
        );


        Map<Integer, Map<Integer, Delta>> deltas = Map.ofEntries(
                Map.entry(EW_GO, ew_go),
                Map.entry(EW_STOPPING, ew_stopping),
                Map.entry(NS_GO, ns_go),
                Map.entry(NS_STOPPING, ns_stopping)
        );

        Mealy mealy = new Mealy(
                statesNames,
                events,
                EW_GO,
                deltas
        );
        ew_green.accept(T1); // set initial state

        long clock = System.currentTimeMillis();

        while (true) {
            if (System.currentTimeMillis() - clock >= 1000) {
                System.out.printf("%2d\r", timer.get());
                if (timer.decrement()) {
                    System.out.println("\r");
                    mealy.transition(T1);

                    System.out.printf("EW: %c%c%c\n",
                            (lights[EW] & RED) != 0 ? 'R' : '_',
                            (lights[EW] & YELLOW) != 0 ? 'W' : '_',
                            (lights[EW] & GREEN) != 0 ? 'G' : '_'
                    );

                    System.out.printf("NS: %c%c%c\n",
                            (lights[NS] & RED) != 0 ? 'R' : '_',
                            (lights[NS] & YELLOW) != 0 ? 'W' : '_',
                            (lights[NS] & GREEN) != 0 ? 'G' : '_'
                    );
                }
                clock = System.currentTimeMillis();
            }
        }

    }

}
