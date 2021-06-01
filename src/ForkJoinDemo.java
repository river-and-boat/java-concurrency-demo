import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.Future;

public class ForkJoinDemo {
    
    public static void main(String[] args) {
        ForkJoinPool pool = new ForkJoinPool();
        CountTask task = new CountTask(1, 5);
        Future<Integer> result = pool.submit(task);
        try {
            System.out.println(result.get());
        } catch (Exception e) {

        }
    }
}
