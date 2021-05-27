import java.util.LinkedList;

public class Worker implements Runnable {
    private final LinkedList<Job> jobs;
    private volatile boolean running = true;

    public Worker(LinkedList<Job> jobs) {
        this.jobs = jobs;
    }

    @Override
    public void run() {
        while (running) {
            Job job = null;
            synchronized (jobs) {
                while (jobs.isEmpty()) {
                    try {
                        jobs.wait();
                    } catch (InterruptedException e) {
                        Thread.currentThread().interrupt();
                        return;
                    }
                }
                job = jobs.removeFirst();
            }

            if (job != null) {
                try {
                    job.run();
                } catch (InterruptedException e) {
                    System.out.println("job has been interrupt");
                }
            }
        }
    }

    public void shutdown() {
        running = false;
    }
}
