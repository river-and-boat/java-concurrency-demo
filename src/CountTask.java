import java.util.concurrent.ExecutionException;
import java.util.concurrent.RecursiveTask;

public class CountTask extends RecursiveTask<Integer> {
    private static final int THRESHOLD = 2;

    private int start;
    private int end;

    public CountTask(int start, int end) {
        this.start = start;
        this.end = end;
    }

    @Override
    protected Integer compute() {
        int sum = 0;
        boolean canComput = (end - start) <= THRESHOLD;
        if (canComput) {
            for (int i = start; i <= end; i++) {
                sum += i;
                System.out.println("sum = " + sum);
            }
        } else {
            int middle = (start + end) / 2;
            CountTask leftTask = new CountTask(start, middle);
            CountTask rightTask = new CountTask(middle + 1, end);

            leftTask.fork();
            rightTask.fork();

            int leftResult = 0;
            int rightResult = 0;
            try {
                leftResult = leftTask.get();
                rightResult = rightTask.get();
                System.out.println("sum left total: " + leftResult);
                System.out.println("sum right total: " + rightResult);
            } catch (InterruptedException | ExecutionException e) {
                e.printStackTrace();
            }

            sum = leftResult + rightResult;
            
        }
        System.out.println("sum total: " + sum);
        return sum;
    }

}
