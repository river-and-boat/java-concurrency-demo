import java.util.concurrent.TimeUnit;

public class InterruptedDemo {
    public static void main(String[] args) throws InterruptedException {
        Thread sleepThread = new Thread(new SleepRunner(), "SleepThread");
        sleepThread.setDaemon(true);
        Thread busyThread = new Thread(new BusyRunner(), "BusyThread");
        busyThread.setDaemon(true);

        sleepThread.start();
        busyThread.start();

        TimeUnit.SECONDS.sleep(5);
        sleepThread.interrupt();
        busyThread.interrupt();
        System.out.println("Sleep Thread interrupted: " + sleepThread.isInterrupted());
        System.out.println("Busy Thread interrupted: " + busyThread.isInterrupted());

        TimeUnit.SECONDS.sleep(5);
    }

    static class SleepRunner implements Runnable {
        @Override
        public void run() {
            try {
                while (true) {
                    Thread.sleep(10000);
                }
            } catch (InterruptedException e) {
                System.out.println("sleep thread has been interrupted");
            }
        }
    }

    static class BusyRunner implements Runnable {
        @Override
        public void run() {
            while (true) {
                
            }
        }
    }

}
