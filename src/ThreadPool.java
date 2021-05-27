import java.util.List;

public interface ThreadPool {
    void execute(Job job);

    void execute(List<Job> jobs);

    void shutdown();

    void addWorkers(int num);

    void removeWorker(int num);

    int getJobSize();
}
