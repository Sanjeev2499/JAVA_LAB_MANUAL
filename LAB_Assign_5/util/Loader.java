package util;

// Runnable to simulate loading during operations
public class Loader implements Runnable {
    private String msg;
    private int ms;

    public Loader(String msg, int ms) {
        this.msg = msg;
        this.ms = ms;
    }

    @Override
    public void run() {
        try {
            System.out.print(msg);
            int steps = Math.max(1, ms / 300);
            for (int i = 0; i < steps; i++) {
                Thread.sleep(300);
                System.out.print(".");
            }
            System.out.println();
        } catch (InterruptedException ignored) {
            Thread.currentThread().interrupt();
        }
    }
}
