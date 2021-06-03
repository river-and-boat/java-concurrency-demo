import java.util.concurrent.LinkedBlockingQueue;

/**
 * MessageProducer
 */
public class MessageProducer implements Runnable {
    private LinkedBlockingQueue queue;

    public MessageProducer(LinkedBlockingQueue queue) {
        this.queue = queue;
    }

    @Override
    public void run() {
        queue.add("Hello World Message: " + System.currentTimeMillis());
        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}