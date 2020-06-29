package traffic;

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

        String[] statesNames = {"EW_GO", "EW_STOPPING", "NS_GO", "NS_STOPPING"};
        String[] events = {"Timer"};
        int[] lights = new int[2];

        Timer timer = new Timer();

        Consumer<Integer> ew_yellow = (Integer event) -> {
            lights[EW] |= YELLOW;
            timer.set(YELLOW_TIME);
        };

        Consumer<Integer> ns_yellow = (Integer event) -> {
            lights[NS] |= YELLOW;
            timer.set(YELLOW_TIME);
        };

        Consumer<Integer> ew_green = (Integer event) -> {
            lights[EW] = GREEN;
            lights[NS] = RED;
            timer.set(GREEN_TIME);
        };

        Consumer<Integer> ns_green = (Integer event) -> {
            lights[NS] = GREEN;
            lights[EW] = RED;
            timer.set(GREEN_TIME);
        };

        Delta[] ew_go = {new Delta(T1, EW_STOPPING, ew_yellow)};
        Delta[] ew_stopping = {new Delta(T1, NS_GO, ns_green)};
        Delta[] ns_go = {new Delta(T1, NS_STOPPING, ns_yellow)};
        Delta[] ns_stopping = {new Delta(T1, NS_GO, ew_green)};

        Delta[][] deltas = {ew_go, ew_stopping, ns_go, ns_stopping};

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
                            (lights[EW] & GREEN) != 0 ? 'G' : '_');

                    System.out.printf("NS: %c%c%c\n",
                            (lights[NS] & RED) != 0 ? 'R' : '_',
                            (lights[NS] & YELLOW) != 0 ? 'W' : '_',
                            (lights[NS] & GREEN) != 0 ? 'G' : '_');
                }
                clock = System.currentTimeMillis();
            }
        }

    }

}
