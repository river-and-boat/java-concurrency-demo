/*
 * RenderExample
 */
public class RenderExample {
    private int a = 0;
    private boolean flag = false;

    public static void main(String[] args) {
        RenderExample renderExample = new RenderExample();
        Thread t1 = new Thread(new Runnable(){
            @Override
            public void run() {
                renderExample.write();
            }
        });
        Thread t2 = new Thread(new Runnable(){
            @Override
            public void run() {
                renderExample.render();
            }
        });
        t1.start();
        t2.start();
        try {
            t1.join();
            t2.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    private void write() {
        a = 1;
        flag = true;
    }

    private void render() {
        if (flag) {
            System.out.println("new value: " + (a * a));
        }
    }
}