package util;

public class GameThread {
    public static void setTimeout(int delayMilliseconds, MyFunction f) {
        new Thread(() -> {
            try {
                Thread.sleep(delayMilliseconds);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
            f.apply();
        }).start();
    }
}
