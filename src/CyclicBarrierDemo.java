import java.util.concurrent.CyclicBarrier;

/**
 * CyclicBarrierDemo
 */
public class CyclicBarrierDemo {
    static CyclicBarrier barrier = new CyclicBarrier(2, new InitRunnable());

    public static void main(String[] args) {
        new Thread(
            new Runnable(){
                @Override
                public void run() {
                    try {
                        barrier.await();
                    } catch (Exception e) {

                    }
                    System.out.println("step 1");
                }
            }
        ).start();
        try {
            barrier.await();
        } catch (Exception e) {

        }
        System.out.println("step 2");
    }
    
    static class InitRunnable implements Runnable {
        @Override
        public void run() {
            System.out.println("init fragment");
        }
    }
}