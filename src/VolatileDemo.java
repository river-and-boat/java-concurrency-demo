public class VolatileDemo {
    private boolean flag = false;

    public static void main(String[] args) throws InterruptedException {
        VolatileDemo vDemo = new VolatileDemo();
        Thread threadA = new Thread(new Runnable(){
            @Override
            public void run() {
                vDemo.executeThreadA();
            }
        });
        Thread threadB = new Thread(new Runnable(){
            @Override
            public void run() {
                vDemo.executeThreadB();
            }
        });
        threadB.start();
        Thread.sleep(2000);
        threadA.start();
    }

    private void executeThreadA() {
        System.out.println("Start A");
        flag = true;
        System.out.println("End A");
    }

    private void executeThreadB() {
        System.out.println("Start B");
        while (!flag) {

        }
        System.out.println("End B");
    }
}
