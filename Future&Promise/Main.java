import java.time.Clock;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class Main {
    public static void main(String[] args) throws ExecutionException, InterruptedException {
        ExecutorService threadpool1 = Executors.newFixedThreadPool(1);
        ExecutorService threadpool2 = Executors.newFixedThreadPool(1);
        ArrayList<Future<Boolean>> vector = new ArrayList<Future<Boolean>> (10);
        final Clock clock = Clock.systemDefaultZone();;
        vector.add(0, threadpool1.submit(() ->
            {
                System.out.println("Received news, 0 thread number: " + Thread.currentThread().getName() + " Time " + clock.instant());
                return true;
            }));

        final List<Integer> range = IntStream.range(0,9).boxed().collect(Collectors.toList());

        for (final int i : range) {
            vector.get(i).get();
            vector.add(i + 1, threadpool1.submit(() ->
            {
                Thread.sleep(5000);
                System.out.print("Received news, " + (i + 1) + " thread number: " + Thread.currentThread().getName()+ " Time " + clock.instant()+ "\n");
                return true;
            }));

            threadpool2.submit(() -> {
                try {
                    Thread.sleep(2000);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
                System.out.print("What is it?,   " + i + " thread number: " + Thread.currentThread().getName()+ " Time " + clock.instant()+ "\n");
            });
        }
        vector.get(range.size()).get();
        threadpool2.submit(() -> {
            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            System.out.print("What is it?,   " + range.size() + " thread number: " + Thread.currentThread().getName()+ " Time " + clock.instant()+ "\n");
        });

        threadpool1.shutdown();
        threadpool2.shutdown();
    }

}
