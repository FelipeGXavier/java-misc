package misc.thread;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class Application {

    public static void main(String[] args) throws ExecutionException, InterruptedException {
        var executor = Executors.newFixedThreadPool(10);
        var oneThread = Executors.newSingleThreadExecutor();
        Future<Integer> square = oneThread.submit(Application::run);
        if (!square.isDone()) {
            executor.submit(() -> new MyTask().run());
            executor.submit(() -> new MyTask().run());
            executor.submit(() -> new MyTask().run());
        }
        System.out.println(square.get());
        executor.shutdown();
        oneThread.shutdown();
    }

    private static Integer run() throws InterruptedException {
        Thread.sleep(1000);
        return 100 * 100;
    }
}
