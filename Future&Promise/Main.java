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
//    Received news, 0 thread number: pool-1-thread-1 Time 2023-01-09T22:02:21.061919Z
//    What is it?,   0 thread number: pool-2-thread-1 Time 2023-01-09T22:02:23.092356Z
//    Received news, 1 thread number: pool-1-thread-1 Time 2023-01-09T22:02:26.091542Z
//    What is it?,   1 thread number: pool-2-thread-1 Time 2023-01-09T22:02:28.092507Z
//    Received news, 2 thread number: pool-1-thread-1 Time 2023-01-09T22:02:31.092437Z
//    What is it?,   2 thread number: pool-2-thread-1 Time 2023-01-09T22:02:33.092837Z
//    Received news, 3 thread number: pool-1-thread-1 Time 2023-01-09T22:02:36.092832Z
//    What is it?,   3 thread number: pool-2-thread-1 Time 2023-01-09T22:02:38.093388Z
//    Received news, 4 thread number: pool-1-thread-1 Time 2023-01-09T22:02:41.093231Z
//    What is it?,   4 thread number: pool-2-thread-1 Time 2023-01-09T22:02:43.093603Z
//    Received news, 5 thread number: pool-1-thread-1 Time 2023-01-09T22:02:46.093563Z
//    What is it?,   5 thread number: pool-2-thread-1 Time 2023-01-09T22:02:48.094199Z
//    Received news, 6 thread number: pool-1-thread-1 Time 2023-01-09T22:02:51.094036Z
//    What is it?,   6 thread number: pool-2-thread-1 Time 2023-01-09T22:02:53.094642Z
//    Received news, 7 thread number: pool-1-thread-1 Time 2023-01-09T22:02:56.094596Z
//    What is it?,   7 thread number: pool-2-thread-1 Time 2023-01-09T22:02:58.094984Z
//    Received news, 8 thread number: pool-1-thread-1 Time 2023-01-09T22:03:01.095Z
//    What is it?,   8 thread number: pool-2-thread-1 Time 2023-01-09T22:03:03.095437Z
//    Received news, 9 thread number: pool-1-thread-1 Time 2023-01-09T22:03:06.095402Z
//    What is it?,   9 thread number: pool-2-thread-1 Time 2023-01-09T22:03:08.096544Z

}
