import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class MessageClient {
    public static void main(String[] args) throws InterruptedException {
        LinkedBlockingQueue queue = new LinkedBlockingQueue<>(20);
        ThreadPoolExecutor productPool = new ThreadPoolExecutor(10, 20, 60, TimeUnit.MILLISECONDS,
                new LinkedBlockingQueue<>(5), new ThreadPoolExecutor.CallerRunsPolicy());
        ThreadPoolExecutor consumerPool = new ThreadPoolExecutor(10, 20, 60, TimeUnit.MILLISECONDS,
                new LinkedBlockingQueue<>(5), new ThreadPoolExecutor.CallerRunsPolicy());

        System.out.println("start consumer queue");
        consumerPool.execute(new MessageConsumer(queue));

        for (int i = 0; i < 100; i++) {
            productPool.execute(new MessageProducer(queue));
            Thread.sleep(2000);
        }
        productPool.shutdown();
        consumerPool.shutdown();
    }
}
