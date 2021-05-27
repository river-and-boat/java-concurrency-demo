import java.util.ArrayList;
import java.util.List;

/**
 * ThreadPoolClient
 */
public class ThreadPoolClient {
    private ThreadPool pool;

    public static void main(String[] args) {
        ThreadPoolClient client = new ThreadPoolClient();
        client.pool = new DefaultThreadPool();
        List<Job> jobs = new ArrayList<>();
        for (int i = 0; i < 10000; i++) {
            jobs.add(new DemoJob(i));
        }
        client.pool.execute(jobs);
    }

    static class DemoJob implements Job {
        private final int number;

        public DemoJob(int number) {
            this.number = number;
        }

        @Override
        public void run() throws InterruptedException {
            System.out.println("hello world, flag = " + number);
        }
    }
}