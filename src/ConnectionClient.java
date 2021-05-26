

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.sql.Connection;
import java.util.LinkedList;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

public class ConnectionClient {
    private static final int THREAD_COUNT = 10;
    private static ConnectionPool pool = new ConnectionPool(THREAD_COUNT);
    private static CountDownLatch start = new CountDownLatch(1);
    private static CountDownLatch end = new CountDownLatch(THREAD_COUNT);

    public static void main(String[] args) throws InterruptedException {
        int count = 200;
        AtomicInteger got = new AtomicInteger();
        AtomicInteger notGot = new AtomicInteger();
        for (int i = 0; i < THREAD_COUNT; i++) {
            Thread thread = new Thread(new ConnectionRunner(count, got, notGot), "ConnectionRunnerThread");
            thread.start();
        }
        start.countDown();

        end.await();
        System.out.println("total invoke: " + (THREAD_COUNT * count));
        System.out.println("got connection: " + got);
        System.out.println("not got connection: " + notGot);
    }

    static class ConnectionRunner implements Runnable {
        int count;
        AtomicInteger got;
        AtomicInteger notGot;

        public ConnectionRunner(int count, AtomicInteger got, AtomicInteger notGot) {
            this.count = count;
            this.got = got;
            this.notGot = notGot;
        }

        @Override
        public void run() {
            try {
                start.await();
            } catch (InterruptedException e) {
                System.out.println("thread has been interrupt");
            }
            while (count > 0) {
                try {
                    Connection connection = pool.fetchConnection(1000);
                    if (connection != null) {
                        try {
                            connection.createStatement();
                            connection.commit();
                        } finally {
                            pool.releaseConnection(connection);
                            got.incrementAndGet();
                        }
                    } else {
                        notGot.incrementAndGet();
                    }
                } catch (Exception exception) {

                } finally {
                    count--;
                }
            }
            end.countDown();
        }
    }

    static class ConnectionPool {
        private LinkedList<Connection> pool = new LinkedList<>();

        public ConnectionPool(int initialSize) {
            if (initialSize > 0) {
                for (int i = 0; i < initialSize; i++) {
                    pool.add(ConnectionController.createConnection());
                }
            }
        }

        public void releaseConnection(Connection connection) {
            if (connection != null) {
                synchronized (pool) {
                    pool.add(connection);
                    pool.notifyAll();
                }
            }
        }

        public Connection fetchConnection(long mills) throws InterruptedException {
            synchronized (pool) {
                if (mills <= 0) {
                    System.out.println("complete out of time");
                    while (pool.isEmpty()) {
                        pool.wait();
                    }
                    return pool.removeFirst();
                } else {
                    long future = System.currentTimeMillis() + mills;
                    long remaining = mills;

                    while (pool.isEmpty() && remaining > 0) {
                        pool.wait();
                        remaining = future - System.currentTimeMillis();
                    }
                    Connection result = null;
                    if (!pool.isEmpty()) {
                        result = pool.removeFirst();
                    }
                    return result;
                }
            }
        }
    }
    
    static class ConnectionController {
        public static final Connection createConnection() {
            return (Connection) Proxy.newProxyInstance(
                ConnectionController.class.getClassLoader(),
                new Class<?>[] { Connection.class }, 
                new ConnectionHandler()
            );
        }
    
        static class ConnectionHandler implements InvocationHandler {
            @Override
            public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
                if (method.getName().equals("commit")) {
                    TimeUnit.MILLISECONDS.sleep(100);
                }
                return null;
            }
        }
    
    }
}
