import java.util.concurrent.Exchanger;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ExchangerDemo {
    private static final Exchanger<String> exgr = new Exchanger<>();

    private static ExecutorService threadPool = Executors.newFixedThreadPool(2);

    public static void main(String[] args) {
        threadPool.execute(new Runnable() {
            @Override
            public void run() {
                try {
                    String Atitle = "Bank A";
                    exgr.exchange(Atitle);
                } catch (InterruptedException e) {

                }
            }
        });
        threadPool.execute(new Runnable() {
            @Override
            public void run() {
                try {
                    String Btitle = "Bank B";
                    String Atitle = exgr.exchange(Btitle);
                    System.out.println(
                            "A和B数据是否一致: " + Atitle.equals(Btitle) + ", A录入数据: " + Atitle + ", B录入数据: " + Btitle);
                } catch (InterruptedException e) {

                }
            }
        });
        threadPool.shutdown();
    }

}
