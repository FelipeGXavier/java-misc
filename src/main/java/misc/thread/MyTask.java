package misc.thread;

public class MyTask implements Runnable{

    @Override
    public void run() {
        try {
            Thread.sleep(1000);
            System.out.println("Execute");
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
