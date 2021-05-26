import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

public class App {
    private AtomicInteger atomicInteger = new AtomicInteger(0);
    private int i;

    public static void main(String[] args) throws Exception {
        final App app = new App();
        List<Thread> ts = new ArrayList<>(600);
        long start = System.currentTimeMillis();
        for (int j = 0; j < 100; j++) {
            Thread t = new Thread(new Runnable() {
                @Override
                public void run() {
                    for (int i = 0; i < 1000; i++) {
                        app.count();
                        app.safeCount();
                    }
                }
            });
            ts.add(t);
        }
        for (Thread t : ts) {
            t.start();
        }
        // wait for all thread finish
        for (Thread t : ts) {
            try {
                t.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        System.out.println("unsafe thread count: " + app.i);
        System.out.println("safe thread count: " + app.atomicInteger.get());
        System.out.println("exec time: " + (System.currentTimeMillis() - start));
    }

    private void safeCount() {
        while (!atomicCompareAndSet()) {

        }
    }

    private boolean atomicCompareAndSet() {
        int i = atomicInteger.get();
        return atomicInteger.compareAndSet(i, ++i);
    }

    private void count() {
        i++;
    }
}
