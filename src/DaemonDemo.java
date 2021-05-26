public class DaemonDemo {
    public static void main(String[] args) {
        Thread thread = new Thread(new Runnable(){
            @Override
            public void run() {
                try {
                    Thread.sleep(10000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } finally {
                    System.out.println("DaemonThread finally run.");
                }
            }
        });
        thread.setDaemon(true);
        thread.start();
    }
}
