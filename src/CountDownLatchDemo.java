import java.util.concurrent.CountDownLatch;

public class CountDownLatchDemo {
    static CountDownLatch count = new CountDownLatch(2);

    public static void main(String[] args) throws InterruptedException {
        new Thread(
            new Runnable(){
                @Override
                public void run() {
                    System.out.println("step 1");
                    count.countDown();
                    System.out.println("step 2");
                    count.countDown();
                }
            }
        ).start();
        count.await();
        System.out.println("step 3");
    }
}
