import java.util.ArrayList;
import java.util.List;
public class AtomicDemo {
    private volatile int count = 0;

    public static void main(String[] args) throws InterruptedException {
        AtomicDemo aDemo = new AtomicDemo();
        List<Thread> threads = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            threads.add(new Thread(new Runnable() {
                @Override
                public void run() {
                    for (int i = 0; i < 1000; i++) {
                        aDemo.increate();                    
                    }
                }
            }));
        }
        
        // start
        for (Thread thread : threads) {
            thread.start();
        }

        // wait
        for (Thread thread : threads) {
            thread.join();
        }

        System.out.println("sum = " + aDemo.count);
    }
    
    private synchronized void increate() {
        count++;
    }
}
