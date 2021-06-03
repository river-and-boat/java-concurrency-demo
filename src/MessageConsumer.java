import java.util.concurrent.LinkedBlockingQueue;

public class MessageConsumer implements Runnable {
    private LinkedBlockingQueue queue;

    public MessageConsumer(LinkedBlockingQueue queue) {
        this.queue = queue;
    }

    @Override
    public void run() {
        while (true) {
            try {
                String message = (String) queue.take();
                System.out.println("消费数据：" + message);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }        
    }
    
}
