package traffic;

public class Timer {

    int timer = 0;

    void set(int secs) {
        timer = secs;
    }

    int get() {
        return timer;
    }

    boolean decrement() {
        return --timer == 0;
    }
}
