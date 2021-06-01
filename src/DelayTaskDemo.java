import java.util.concurrent.DelayQueue;
import java.util.concurrent.Delayed;

public class DelayTaskDemo {
    public static DelayQueue queue = new DelayQueue<>();

    public static void main(String[] args) throws InterruptedException {
        new Thread(new Runnable() {
            @Override
            public void run() {
                queue.offer(new MyDelayedTask("task1", 10000));
                queue.offer(new MyDelayedTask("task2", 3900));
                queue.offer(new MyDelayedTask("task3", 1900));
                queue.offer(new MyDelayedTask("task4", 5900));
                queue.offer(new MyDelayedTask("task5", 6900));
                queue.offer(new MyDelayedTask("task6", 7900));
                queue.offer(new MyDelayedTask("task7", 4900));
            }
        }).start();
        while (true) {
            Delayed take = queue.take();
            System.out.println(take);
        }
    }
}
